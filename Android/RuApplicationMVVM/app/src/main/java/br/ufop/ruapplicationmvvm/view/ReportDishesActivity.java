package br.ufop.ruapplicationmvvm.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import br.ufop.ruapplicationmvvm.R;
import br.ufop.ruapplicationmvvm.ViewModelFactory;
import br.ufop.ruapplicationmvvm.databinding.ActivityReportDishesBinding;
import br.ufop.ruapplicationmvvm.model.entity.Dish;
import br.ufop.ruapplicationmvvm.model.entity.ReportDish;
import br.ufop.ruapplicationmvvm.util.Status;
import br.ufop.ruapplicationmvvm.viewmodel.ReportDishesViewModel;
import es.dmoral.toasty.Toasty;

public class ReportDishesActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Dish> dishes;
    private ActivityReportDishesBinding binding;
    private ReportDishesViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_dishes);
        mViewModel = ViewModelProviders
                .of(this, new ViewModelFactory(getSharedPreferences("prefs", MODE_PRIVATE)))
                .get(ReportDishesViewModel.class);
        mViewModel.getListResult().observe(this, result -> {
            if (result.getStatus() == Status.SUCCESS)
                onFinishedIndex(result.getData());
            else if (result.getStatus() == Status.ERROR)
                onError(result.getError());
            else if (result.getStatus() == Status.FAILURE)
                onFailure(result.getFailure());
        });

        mViewModel.getResult().observe(this, result -> {
            onLoading(result.getStatus() == Status.LOADING);

            if (result.getStatus() == Status.SUCCESS)
                onFinished(result.getData());
            else if (result.getStatus() == Status.ERROR)
                onError(result.getError());
            else if (result.getStatus() == Status.FAILURE)
                onFailure(result.getFailure());
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Relatórios de Pratos");
        mViewModel.getDishes();
        binding.reportDishesButton.setOnClickListener(this);


    }

    private void onLoading(boolean b) {
        if (b) {
            binding.reportDishesButton.setVisibility(View.INVISIBLE);
            binding.reportDishesLoadingDialog.setVisibility(View.VISIBLE);
            binding.reportDishesResultContainer.setVisibility(View.INVISIBLE);
        } else {
            binding.reportDishesLoadingDialog.setVisibility(View.INVISIBLE);
            binding.reportDishesResultContainer.setVisibility(View.VISIBLE);
            binding.reportDishesButton.setVisibility(View.VISIBLE);
        }
    }

    private void addDataSet(ReportDish reportDish) {
        float[] yData = {reportDish.getRuim(),
                reportDish.getBaixo(),
                reportDish.getMedio(),
                reportDish.getAlto(),
                reportDish.getExcelente()
        };
        String[] xData = {"Ruim", "Baixo", "Médio", "Alto", "Excelente"};
        if (binding.reportDishesPiechat.getData() != null) {
            binding.reportDishesPiechat.clearValues();
            binding.reportDishesPiechat.clear();
        }
        binding.reportDishesPiechat.setRotationEnabled(true);
        binding.reportDishesPiechat.setUsePercentValues(true);
        binding.reportDishesPiechat.getDescription().setEnabled(false);
        binding.reportDishesPiechat.setHoleRadius(25f);
        binding.reportDishesPiechat.setTransparentCircleAlpha(0);
        binding.reportDishesPiechat.setCenterText("Avaliações");
        binding.reportDishesPiechat.setCenterTextSize(10);


        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for (int i = 0; i < yData.length; i++) {
            yEntrys.add(new PieEntry(yData[i], xData[i]));
        }

        for (int i = 1; i < xData.length; i++) {
            xEntrys.add(xData[i]);
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.MAGENTA);

        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = binding.reportDishesPiechat.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);


        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        binding.reportDishesPiechat.setData(pieData);

        binding.reportDishesPiechat.invalidate();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    public void onFinished(ReportDish response) {
        addDataSet(response);
        binding.demandTextviewTotal.setText(response.getRejeicao() + " Pessoas");
    }

    public void onFinishedIndex(List<Dish> dishes) {
        if (dishes != null)
            setSpinner((ArrayList<Dish>) dishes);
    }

    private void setSpinner(ArrayList<Dish> dishes) {
        this.dishes = dishes;
        String[] dishesName = new String[dishes.size()];

        for (int i = 0; i < dishes.size(); i++) {
            dishesName[i] = dishes.get(i).getName();
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dishesName);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.reportDishesSpinner.setAdapter(spinnerArrayAdapter);// The drop down view
        binding.reportDishesButton.setVisibility(View.VISIBLE);
    }

    public void onError(String message) {
        Toasty.error(this, message, Toasty.LENGTH_LONG).show();
    }

    public void onFailure(String message) {
        Toasty.warning(this, message, Toasty.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View view) {
        mViewModel.getReport(dishes.get(binding.reportDishesSpinner.getSelectedItemPosition()).getId());
    }
}
