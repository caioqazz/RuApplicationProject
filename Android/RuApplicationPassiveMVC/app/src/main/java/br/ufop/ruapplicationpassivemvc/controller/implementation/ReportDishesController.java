package br.ufop.ruapplicationpassivemvc.controller.implementation;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.usuario.ruapplicationpassivemvc.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import br.ufop.ruapplicationpassivemvc.activity.listener.ReportDishesControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.listener.DishServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.Dish;
import br.ufop.ruapplicationpassivemvc.model.entity.ReportDish;
import br.ufop.ruapplicationpassivemvc.service.DishService;
import br.ufop.ruapplicationpassivemvc.service.ReportDishesService;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Networking;
import br.ufop.ruapplicationpassivemvc.view.ReportDishesView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportDishesController implements View.OnClickListener, DishServiceListener {

    private ReportDishesView view;
    private ReportDishesControllerListener listener;
    private TokenManager tokenManager;
    private ArrayList<Dish> dishes;

    public ReportDishesController(ReportDishesView view, ReportDishesControllerListener listener) {
        this.view = view;
        this.listener = listener;
        tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE));
        getDishes();

    }

    private void getDishes() {
        DishService service = new DishService(this, tokenManager);
        service.index();
    }


    public void setResult(ReportDish reportDish) {
        if (reportDish.getTotal() != 0) {
            addDataSet(reportDish);
        }

        //set Rejeicao

        //show form
    }


    private void addDataSet(ReportDish reportDish) {
        float[] yData = {reportDish.getRuim(),
                reportDish.getBaixo(),
                reportDish.getMedio(),
                reportDish.getAlto(),
                reportDish.getExcelente()
        };
        String[] xData = {"Ruim", "Baixo", "Médio", "Alto", "Excelente"};
        PieChart pieChart = (PieChart) view.findViewById(R.id.report_dishes_piechat);
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
    public void onClick(View v) {
        if (Networking.isNetworkConnected(view)) {
            ReportDishesService service = new ReportDishesService(tokenManager);
            service.reportDish(
                    dishes.get(view.getSelectedSpinner()).getId(), new Callback<ReportDish>() {
                        @Override
                        public void onResponse(Call<ReportDish> call, Response<ReportDish> response) {
                            if (response.isSuccessful()) {
                                setResult(response.body());
                                view.setRejeicao(response.body().getRejeicao());
                                view.showResult();
                            }
                        }

                        @Override
                        public void onFailure(Call<ReportDish> call, Throwable t) {
                            Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void setSpinner(ArrayList<Dish> dishes) {
        String[] dishesName = new String[dishes.size()];

        for (int i = 0; i < dishes.size(); i++) {
            dishesName[i] = dishes.get(i).getName();
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, dishesName);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        view.setAdapterSpinner(spinnerArrayAdapter);// The drop down view
        view.showButton();
    }

    @Override
    public void onIndexSuccess(Response<List<Dish>> response) {
        dishes = (ArrayList) response.body();
        if (dishes != null)
            setSpinner(dishes);
    }

    @Override
    public void onIndexError(Response<List<Dish>> response) {
        // getDishes();
    }

    @Override
    public void onActionSuccess(Response<Dish> response, int option) {

    }

    @Override
    public void onActionError(Response<Dish> response, int option) {

    }

    @Override
    public void onActionFailure(Throwable t, int option) {

    }


}
