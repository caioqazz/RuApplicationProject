package br.ufop.ruapplicationpassivemvc.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.usuario.ruapplicationpassivemvc.R;

public class ReportDishesView extends RelativeLayout {

    public ReportDishesView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setListeners(OnClickListener onClickListener){
        ((Button) this.findViewById(R.id.report_dishes_button)).setOnClickListener(onClickListener);
    }

    public void setAdapterSpinner(ArrayAdapter<String> adapter) {
        ((Spinner) this.findViewById(R.id.report_dishes_spinner)).setAdapter(adapter);
    }

    public void showButton() {
        ((Button) this.findViewById(R.id.report_dishes_button)).setVisibility(View.VISIBLE);
    }

    public void showResult(){
        ((LinearLayout) this.findViewById(R.id.report_dishes_result_container)).setVisibility(View.VISIBLE);
    }

    public void setRejeicao(int num){
        ((TextView) this.findViewById(R.id.demand_textview_total)).setText(num+ " Pessoas");
    }
    public int getSelectedSpinner() {
            Spinner spinner = this.findViewById(R.id.report_dishes_spinner);
            return spinner.getSelectedItemPosition() ;
    }

}
