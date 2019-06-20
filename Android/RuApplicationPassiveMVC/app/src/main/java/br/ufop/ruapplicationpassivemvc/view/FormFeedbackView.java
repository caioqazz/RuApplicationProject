package br.ufop.ruapplicationpassivemvc.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.usuario.ruapplicationpassivemvc.R;

public class FormFeedbackView extends LinearLayout {
    AlertDialog dialog;

    public FormFeedbackView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public String getContent() {
        return ((EditText) this.findViewById(R.id.form_feedback_content)).getText().toString();
    }

    public void setContent(String content) {
        ((EditText) this.findViewById(R.id.form_feedback_content)).setText(content);
    }

    public String getSubject() {
        return ((EditText) this.findViewById(R.id.form_feedback_subject)).getText().toString();
    }

    public void setSubject(String subject) {
        ((EditText) this.findViewById(R.id.form_feedback_subject)).setText(subject);
    }


    public void setContentError(String errorMessage) {
        ((EditText) this.findViewById(R.id.form_feedback_content)).setError(errorMessage);
    }

    public void setSubjectError(String errorMessage) {
        ((EditText) this.findViewById(R.id.form_feedback_subject)).setError(errorMessage);
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
