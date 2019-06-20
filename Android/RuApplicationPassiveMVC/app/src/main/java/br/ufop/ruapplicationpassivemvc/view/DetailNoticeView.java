package br.ufop.ruapplicationpassivemvc.view;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.usuario.ruapplicationpassivemvc.R;

public class DetailNoticeView extends RelativeLayout {

    AlertDialog dialog;

    public DetailNoticeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public String getContent() {
        return ((TextView) findViewById(R.id.detail_notice_content)).getText().toString();
    }

    public void setContent(String content) {
        ((TextView) findViewById(R.id.detail_notice_content)).setText(content);
    }

    public String getSubject() {
        return ((TextView) findViewById(R.id.detail_notice_subject)).getText().toString();
    }


    public void setSubject(String subject) {
        ((TextView) findViewById(R.id.detail_notice_subject)).setText(subject);
    }

    public String getDate() {
        return null;
    }

    public void setDate(String date) {
        ((TextView) findViewById(R.id.detail_notice_date)).setText(date);
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

    public void setColor(int type) {
        switch (type) {
            case 0:
                (findViewById(R.id.detail_notice_header)).setBackgroundResource(R.color.lightBlue);
                break;
            case 1:
                (findViewById(R.id.detail_notice_header)).setBackgroundResource(R.color.darkYellow);
                break;
            case 2:
                (findViewById(R.id.detail_notice_header)).setBackgroundResource(R.color.lightRed);
                break;
        }
    }


}
