package br.ufop.ruapplicationmvvm.view;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

import br.ufop.ruapplicationmvvm.R;
import br.ufop.ruapplicationmvvm.ViewModelFactory;
import br.ufop.ruapplicationmvvm.databinding.ActivityReportDemandBinding;
import br.ufop.ruapplicationmvvm.model.entity.DemandReport;
import br.ufop.ruapplicationmvvm.model.response.ReportDemandResponse;
import br.ufop.ruapplicationmvvm.util.Status;
import br.ufop.ruapplicationmvvm.viewmodel.ReportDemandViewModel;
import es.dmoral.toasty.Toasty;

public class ReportDemandAcitvity extends AppCompatActivity implements View.OnClickListener {
    private ActivityReportDemandBinding binding;
    private ReportDemandViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_demand);
        setTitle("Relatórios de Demanda");
        mViewModel = ViewModelProviders
                .of(this, new ViewModelFactory(getSharedPreferences("prefs", MODE_PRIVATE)))
                .get(ReportDemandViewModel.class);

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

        setSpinnerYear();
        setSpinnerMonth();
        binding.reportDemandButton.setOnClickListener(this);
    }

    private void onLoading(boolean b) {
        if (b) {
            binding.reportDemandButton.setVisibility(View.INVISIBLE);
            binding.reportDemandLoadingDialog.setVisibility(View.VISIBLE);
            binding.reportDemandResultContainer.setVisibility(View.INVISIBLE);
        } else {
            binding.reportDemandLoadingDialog.setVisibility(View.INVISIBLE);
            binding.reportDemandResultContainer.setVisibility(View.VISIBLE);
            binding.reportDemandButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onFinished(ReportDemandResponse response) {
        drawLineChart(response);
    }

    private void setSpinnerYear() {
        String[] years = {"2019", "2020", "2021", "2022", "2023", "2024", "2025"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, years);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.reportDemandSpinnerYear.setAdapter(spinnerArrayAdapter);
    }

    private void setSpinnerMonth() {
        String[] months = {"Janeiro", "Fevereiro", "Março",
                "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, months);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.reportDemandSpinnerMonth.setAdapter(spinnerArrayAdapter);
    }

    private void drawLineChart(ReportDemandResponse dataSet) {

        binding.reportDemandLineChart.setVisibility(View.INVISIBLE);
        binding.reportDemandLineChart.setTouchEnabled(true);
        binding.reportDemandLineChart.setPinchZoom(true);


        ArrayList<Entry> values = new ArrayList<>();

        for (DemandReport demand : dataSet.getDemands()) {
            values.add(new Entry(demand.getDay(), demand.getTotal()));
        }

        LineDataSet set1;
        if (binding.reportDemandLineChart.getData() != null &&
                binding.reportDemandLineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) binding.reportDemandLineChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            binding.reportDemandLineChart.getData().notifyDataChanged();
            binding.reportDemandLineChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "Demanda total diária");
            set1.setDrawIcons(false);
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.DKGRAY);
            set1.setCircleColor(Color.DKGRAY);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            set1.setFillColor(Color.RED);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            binding.reportDemandLineChart.setData(data);


        }

        binding.reportDemandLineChart.getData().notifyDataChanged();
        binding.reportDemandLineChart.notifyDataSetChanged();
        binding.reportDemandLineChart.setActivated(true);
        binding.reportDemandLineChart.setVisibility(View.VISIBLE);
    }


    public void onError(String message) {
        Toasty.error(this, message, Toasty.LENGTH_LONG).show();
    }

    public void onFailure(String message) {
        Toasty.warning(this, message, Toasty.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View view) {
        mViewModel.getReport(binding.reportDemandSpinnerYear.getSelectedItemPosition(), Integer.parseInt((String) binding.reportDemandSpinnerYear.getSelectedItem()));
    }
}
