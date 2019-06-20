package br.ufop.ruapplicationpassivemvc.activity.implementation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.usuario.ruapplicationpassivemvc.R;

import br.ufop.ruapplicationpassivemvc.activity.listener.ReportDishesControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.implementation.ReportDishesController;
import br.ufop.ruapplicationpassivemvc.view.ReportDishesView;

public class ReportDishesActivity extends AppCompatActivity implements ReportDishesControllerListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_dishes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Relatórios de Pratos");
        ReportDishesView view = findViewById(R.id.report_dishes_container);
        ReportDishesController controller = new ReportDishesController(view,this);
        view.setListeners(controller);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
