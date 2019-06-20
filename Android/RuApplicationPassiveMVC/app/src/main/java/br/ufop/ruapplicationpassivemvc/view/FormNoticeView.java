package br.ufop.ruapplicationpassivemvc.view;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.usuario.ruapplicationpassivemvc.R;

public class FormNoticeView extends RelativeLayout {
    AlertDialog dialog;

    public FormNoticeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getType() {
        Spinner spinner = this.findViewById(R.id.form_notice_spinner);
        return spinner.getSelectedItemPosition() - 1;
    }

    public void setType(int type) {
        ((Spinner) this.findViewById(R.id.form_notice_spinner)).setSelection(type + 1);
    }

    public String getSubject() {
        return ((EditText) this.findViewById(R.id.form_notice_subject)).getText().toString();
    }

    public void setSubject(String subject) {
        ((EditText) this.findViewById(R.id.form_notice_subject)).setText(subject);
    }

    public String getContent() {
        return ((EditText) this.findViewById(R.id.form_notice_content)).getText().toString();
    }

    public void setContent(String content) {
        ((EditText) this.findViewById(R.id.form_notice_content)).setText(content);
    }

    public void setAdapterSpinner(ArrayAdapter<String> adapter) {
        ((Spinner) this.findViewById(R.id.form_notice_spinner)).setAdapter(adapter);
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
