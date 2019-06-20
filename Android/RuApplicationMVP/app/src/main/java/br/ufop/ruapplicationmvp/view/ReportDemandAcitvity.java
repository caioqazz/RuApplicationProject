package br.ufop.ruapplicationmvp.view;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

import br.ufop.ruapplicationmvp.R;
import br.ufop.ruapplicationmvp.contract.ReportDemandContract;
import br.ufop.ruapplicationmvp.model.entity.DemandReport;
import br.ufop.ruapplicationmvp.model.response.ReportDemandResponse;
import br.ufop.ruapplicationmvp.presenter.ReportDemandPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class ReportDemandAcitvity extends AppCompatActivity
        implements ReportDemandContract.View {
    @BindView(R.id.report_demand_spinner_year)
    Spinner mSpinnerYear;
    @BindView(R.id.report_demand_spinner_month)
    Spinner mSpinnerMonth;
    @BindView(R.id.report_demand_button)
    Button mButton;
    @BindView(R.id.report_demand_loading_dialog)
    LinearLayout mLoading;
    @BindView(R.id.report_demand_LineChart)
    LineChart mChart;
    @BindView(R.id.report_demand_result_container)
    LinearLayout mContainer;
    private ReportDemandPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_demand);
        ButterKnife.bind(this);
        setTitle("Relatórios de Demanda");
        mPresenter = new ReportDemandPresenter(this, getSharedPreferences("prefs", MODE_PRIVATE));


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setSpinnerYear();
        setSpinnerMonth();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onFinished(ReportDemandResponse response) {
        drawLineChart(response);
    }

    private void setSpinnerYear() {
        String[] years = {"2019", "2020", "2021", "2022", "2023", "2024", "2025"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, years);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerYear.setAdapter(spinnerArrayAdapter);
    }

    private void setSpinnerMonth() {
        String[] months = {"Janeiro", "Fevereiro", "Março",
                "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, months);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerMonth.setAdapter(spinnerArrayAdapter);
    }

    private void drawLineChart(ReportDemandResponse dataSet) {

        mChart.setVisibility(View.INVISIBLE);
        mChart.setTouchEnabled(true);
        mChart.setPinchZoom(true);


        ArrayList<Entry> values = new ArrayList<>();

        for (DemandReport demand : dataSet.getDemands()) {
            values.add(new Entry(demand.getDay(), demand.getTotal()));
        }

        LineDataSet set1;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
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
            mChart.setData(data);


        }

        mChart.getData().notifyDataChanged();
        mChart.notifyDataSetChanged();
        mChart.setActivated(true);
        mChart.setVisibility(View.VISIBLE);
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
    public void onError(String message) {
        Toasty.error(this, message, Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(String message) {
        Toasty.warning(this, message, Toasty.LENGTH_LONG).show();
    }

    @OnClick(R.id.report_demand_button)
    public void onViewClicked() {
        mPresenter.onCommitButtonClick(mSpinnerMonth.getSelectedItemPosition(), Integer.parseInt((String) mSpinnerYear.getSelectedItem()));
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
