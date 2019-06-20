package br.ufop.ruapplicationmvp.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import br.ufop.ruapplicationmvp.R;
import br.ufop.ruapplicationmvp.contract.ReportDishesContract;
import br.ufop.ruapplicationmvp.model.entity.Dish;
import br.ufop.ruapplicationmvp.model.entity.ReportDish;
import br.ufop.ruapplicationmvp.presenter.ReportDishesPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class ReportDishesActivity extends AppCompatActivity
        implements ReportDishesContract.View {
    @BindView(R.id.report_dishes_spinner)
    Spinner mSpinner;
    @BindView(R.id.report_dishes_button)
    Button mButton;
    @BindView(R.id.report_dishes_loading_dialog)
    LinearLayout mLoading;
    @BindView(R.id.report_dishes_piechat)
    PieChart pieChart;
    @BindView(R.id.demand_textview_total)
    TextView mTotal;
    @BindView(R.id.report_dishes_result_container)
    LinearLayout mContainer;
    private ReportDishesPresenter mPresenter;
    private ArrayList<Dish> dishes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_dishes);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Relatórios de Pratos");
        mPresenter = new ReportDishesPresenter(this, getSharedPreferences("prefs", MODE_PRIVATE));
        mPresenter.getDishes();
    }

    private void addDataSet(ReportDish reportDish) {
        float[] yData = {reportDish.getRuim(),
                reportDish.getBaixo(),
                reportDish.getMedio(),
                reportDish.getAlto(),
                reportDish.getExcelente()
        };
        String[] xData = {"Ruim", "Baixo", "Médio", "Alto", "Excelente"};
        if (pieChart.getData() != null) {
            pieChart.clearValues();
            pieChart.clear();
        }
        //    pieChart.setContentDescription("gdfgdfg");
        pieChart.setRotationEnabled(true);
        pieChart.setUsePercentValues(true);
        //pieChart.setUsePercentValues(true);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChart.getDescription().setEnabled(false);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Avaliações");
        pieChart.setCenterTextSize(10);


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
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);


        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        pieChart.invalidate();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void showProgress() {
        mButton.setVisibility(View.INVISIBLE);
        mLoading.setVisibility(View.VISIBLE);
        mContainer.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        mLoading.setVisibility(View.INVISIBLE);
        mContainer.setVisibility(View.VISIBLE);
        mButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFinished(ReportDish response) {
        addDataSet(response);
        mTotal.setText(response.getRejeicao() + " Pessoas");
    }

    @Override
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
        mSpinner.setAdapter(spinnerArrayAdapter);// The drop down view
        mButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String message) {
        Toasty.error(this, message, Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(String message) {
        Toasty.warning(this, message, Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @OnClick(R.id.report_dishes_button)
    public void onViewClicked() {
        mPresenter.onCommitButtonClick(dishes.get(mSpinner.getSelectedItemPosition()).getId());
    }
}
