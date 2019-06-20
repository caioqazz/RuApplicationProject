package br.ufop.ruapplicationpassivemvc.view;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.usuario.ruapplicationpassivemvc.R;

public class DetailFeedbackView extends ScrollView {

    AlertDialog dialog;

    public DetailFeedbackView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public String getSubject() {
        return ((TextView) findViewById(R.id.detail_feedback_subject)).getText().toString();
    }


    public void setSubject(String subject) {
        ((TextView) findViewById(R.id.detail_feedback_subject)).setText(subject);
    }


    public String getName() {
        return ((TextView) findViewById(R.id.detail_feedback_name)).getText().toString();
    }


    public void setName(String name) {
        ((TextView) findViewById(R.id.detail_feedback_name)).setText(name);
    }


    public void setDate(String date) {
        ((TextView) findViewById(R.id.detail_feedback_date)).setText(date);
    }


    public String getContent() {
        return ((TextView) findViewById(R.id.detail_feedback_content)).getText().toString();
    }


    public void setContent(String content) {
        ((TextView) findViewById(R.id.detail_feedback_content)).setText(content);
    }


    public String getReply() {
        return ((EditText) findViewById(R.id.detail_feedback_reply)).getText().toString();
    }


    public void setReply(String reply) {
        ((EditText) findViewById(R.id.detail_feedback_reply)).setText(reply);
    }

    public ImageView getPhoto() {
        return ((ImageView) findViewById(R.id.detail_feedback_imageview));
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

    public void clientView() {
        findViewById(R.id.detail_feedback_reply).setEnabled(false);
        ((EditText) findViewById(R.id.detail_feedback_reply)).setHint("");
    }
}
