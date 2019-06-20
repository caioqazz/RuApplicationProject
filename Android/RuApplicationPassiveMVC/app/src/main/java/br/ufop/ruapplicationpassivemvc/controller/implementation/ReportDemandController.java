package br.ufop.ruapplicationpassivemvc.controller.implementation;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.usuario.ruapplicationpassivemvc.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

import br.ufop.ruapplicationpassivemvc.controller.listener.ReportDemandControllerListener;
import br.ufop.ruapplicationpassivemvc.model.entity.DemandReport;
import br.ufop.ruapplicationpassivemvc.model.response.ReportDemandResponse;
import br.ufop.ruapplicationpassivemvc.service.ReportDemandService;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Networking;
import br.ufop.ruapplicationpassivemvc.view.ReportDemandView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportDemandController implements View.OnClickListener {
    String[] years = {"2019", "2020", "2021", "2022", "2023", "2024", "2025"};
    private ReportDemandView view;
    private ReportDemandControllerListener listener;
    private TokenManager tokenManager;

    public ReportDemandController(ReportDemandView view, ReportDemandControllerListener listener) {
        this.view = view;
        this.listener = listener;
        tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE));
        setSpinnerYear();
        setSpinnerMonth();


    }

    private void setSpinnerYear() {

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, years);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        view.setAdapterSpinnerYear(spinnerArrayAdapter);// The drop down view
    }

    private void setSpinnerMonth() {
        String[] months = {"Janeiro", "Fevereiro", "Março",
                "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, months);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        view.setAdapterSpinnerMonth(spinnerArrayAdapter);// The drop down view
    }

    private void drawLineChart(ReportDemandResponse dataSet) {
        LineChart mChart = view.findViewById(R.id.report_demand_LineChart);
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


//    private void drawChart() {
//        BarChart barChart = view.findViewById(R.id.report_demand_BarChart);
//        barChart.setDrawBarShadow(false);
//        barChart.setDrawValueAboveBar(true);
//        Description description = new Description();
//        description.setText("");
//        barChart.setDescription(description);
//        barChart.setMaxVisibleValueCount(50);
//        barChart.setPinchZoom(false);
//        barChart.setDrawGridBackground(false);
//
//        XAxis xl = barChart.getXAxis();
//        xl.setGranularity(1f);
//        xl.setCenterAxisLabels(true);
//
//        YAxis leftAxis = barChart.getAxisLeft();
//        leftAxis.setDrawGridLines(false);
//        leftAxis.setSpaceTop(30f);
//        barChart.getAxisRight().setEnabled(false);
//
//        //data
//        float groupSpace = 0.04f;
//        float barSpace = 0.02f;
//        float barWidth = 0.46f;
//
//        int startYear = 1;
//        int endYear = 5;
//
//        List<BarEntry> yVals1 = new ArrayList<BarEntry>();
//        List<BarEntry> yVals2 = new ArrayList<BarEntry>();
//        List<BarEntry> yVals3 = new ArrayList<BarEntry>();
//
//        for (int i = startYear; i < endYear; i++) {
//            yVals1.add(new BarEntry(i, 0.4f));
//        }
//
//        for (int i = startYear; i < endYear; i++) {
//            yVals2.add(new BarEntry(i, 0.7f));
//        }
//
//        for (int i = startYear; i < endYear; i++) {
//            yVals3.add(new BarEntry(i, 0.7f));
//        }
//
//        BarDataSet set1, set2, set3;
//
//        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
//            set1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
//            set2 = (BarDataSet) barChart.getData().getDataSetByIndex(1);
//            set3 = (BarDataSet) barChart.getData().getDataSetByIndex(2);
//
//            set1.setValues(yVals1);
//            set2.setValues(yVals2);
//            set3.setValues(yVals3);
//
//            barChart.getData().notifyDataChanged();
//            barChart.notifyDataSetChanged();
//        } else {
//            set1 = new BarDataSet(yVals1, "Vegetariano");
//            set1.setColor(Color.rgb(104, 241, 175));
//
//            set2 = new BarDataSet(yVals2, "Normal");
//            set2.setColor(Color.rgb(224, 128, 151));
//
//            set3 = new BarDataSet(yVals3, "Total");
//            set3.setColor(Color.rgb(164, 228, 251));
//
//            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
//            dataSets.add(set1);
//            dataSets.add(set2);
//            dataSets.add(set3);
//
//            BarData data = new BarData(dataSets);
//            barChart.setData(data);
//        }
//
//        barChart.getBarData().setBarWidth(barWidth);
//        barChart.groupBars(startYear, groupSpace, barSpace);
//        barChart.invalidate();
//
//    }
//
//    private void set() {
//        BarChart barChart = view.findViewById(R.id.report_demand_BarChart);
//        barChart.setDrawBarShadow(false);
//        barChart.setDrawValueAboveBar(false);
//        barChart.setPinchZoom(false);
//        barChart.setMaxVisibleValueCount(50);
//        barChart.setDrawGridBackground(true);
//
//        ArrayList<BarEntry> BarEntry = new ArrayList<>();
//        BarEntry.add(new BarEntry(2f, 0));
//        BarEntry.add(new BarEntry(4f, 1));
//        BarEntry.add(new BarEntry(6f, 2));
//        BarEntry.add(new BarEntry(8f, 3));
//        BarEntry.add(new BarEntry(7f, 4));
//        BarEntry.add(new BarEntry(3f, 5));
//        BarDataSet dataSet = new BarDataSet(BarEntry, "Projects");
//
//
//        ArrayList<String> labels = new ArrayList<>();
//        labels.add("January");
//        labels.add("February");
//        labels.add("March");
//        labels.add("April");
//        labels.add("May");
//        labels.add("June");
//        BarData data = new BarData(dataSet);
//
//        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//        barChart.setData(data);
//
//
//    }

    @Override
    public void onClick(View v) {
        if (Networking.isNetworkConnected(view)) {
            ReportDemandService service = new ReportDemandService(tokenManager);
            service.reportDemand(view.getSelectedMonthSpinner(), Integer.parseInt(years[view.getSelectedYearSpinner()]), new Callback<ReportDemandResponse>() {
                @Override
                public void onResponse(Call<ReportDemandResponse> call, Response<ReportDemandResponse> response) {
                    if (response.body() != null) {
                        drawLineChart(response.body());
                    }

                }

                @Override
                public void onFailure(Call<ReportDemandResponse> call, Throwable t) {
                    Toast.makeText(view.getContext(), "FAIL", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
