package br.ufop.ruapplicationpassivemvc.view;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.util.Constants;

public class FormCalendarView extends RelativeLayout {
    AlertDialog dialog;

    public FormCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setListener(View.OnClickListener onClickListener) {
        findViewById(R.id.form_calendar_btn_date).setOnClickListener(onClickListener);
    }

    public String getDate() {
        return ((TextView) findViewById(R.id.form_calendar_date)).getText().toString();
    }

    public void setDate(String date) {
        ((TextView) findViewById(R.id.form_calendar_date)).setText(date);
    }

    public String getReason() {
        return ((EditText) findViewById(R.id.form_calendar_reason)).getText().toString();
    }

    public void setReason(String reason) {
        ((EditText) findViewById(R.id.form_calendar_reason)).setText(reason);
    }

    public int getType() {
        if (((RadioButton) findViewById(R.id.form_calendar_type_lunch)).isChecked()) {
            return Constants.ALMOCO;
        }
        return Constants.JANTAR;
    }

    public void setType(int type) {
        if (type == Constants.ALMOCO) {

        } else {
            ((RadioButton) findViewById(R.id.form_calendar_type_dinner)).setChecked(true);
        }
    }

    public int getStatus() {
        if (((Switch) findViewById(R.id.form_calendar_switch_status)).isChecked()) {
            return Constants.ABERTO;
        }
        return Constants.FECHADO;
    }

    public void setStatus(int option) {
        if (option == 1) {
            ((Switch) findViewById(R.id.form_calendar_switch_status)).setChecked(true);
        } else {
            ((Switch) findViewById(R.id.form_calendar_switch_status)).setChecked(false);
        }

    }

    public void showLoading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_layout_message, null);
        builder.setView(view);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void showForm() {
        dialog.dismiss();
    }


}
