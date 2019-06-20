package br.ufop.ruapplicationpassivemvc.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.usuario.ruapplicationpassivemvc.R;

import br.ufop.ruapplicationpassivemvc.controller.implementation.ReportDemandController;

public class ReportDemandView extends RelativeLayout {
    public ReportDemandView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAdapterSpinnerYear(ArrayAdapter<String> adapter) {
        ((Spinner) this.findViewById(R.id.report_demand_spinner_year)).setAdapter(adapter);
    }
    public void setAdapterSpinnerMonth(ArrayAdapter<String> adapter) {
        ((Spinner) this.findViewById(R.id.report_demand_spinner_month)).setAdapter(adapter);
    }

    public int getSelectedYearSpinner() {
        Spinner spinner = this.findViewById(R.id.report_demand_spinner_year);
        return spinner.getSelectedItemPosition() ;
    }

    public int getSelectedMonthSpinner() {
        Spinner spinner = this.findViewById(R.id.report_demand_spinner_month);
        return spinner.getSelectedItemPosition()+1 ;
    }

    public void setListener(OnClickListener onClickListener) {
        ((Button) this.findViewById(R.id.report_demand_button)).setOnClickListener(onClickListener);
    }
}
