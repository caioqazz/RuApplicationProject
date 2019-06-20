package br.ufop.ruapplicationpassivemvc.activity.implementation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.usuario.ruapplicationpassivemvc.R;

import br.ufop.ruapplicationpassivemvc.controller.implementation.ReportDemandController;
import br.ufop.ruapplicationpassivemvc.controller.listener.ReportDemandControllerListener;
import br.ufop.ruapplicationpassivemvc.view.ReportDemandView;

public class ReportDemandAcitvity extends AppCompatActivity implements ReportDemandControllerListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_demand_acitvity);
        setTitle("Relatórios de Demanda");
        ReportDemandView view = (ReportDemandView) findViewById(R.id.report_demand_container);
        ReportDemandController controller = new ReportDemandController(view, this);
        view.setListener(controller);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
