package br.ufop.ruapplicationpassivemvc.view;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.usuario.ruapplicationpassivemvc.R;

public class LoginView extends RelativeLayout {

    AlertDialog dialog;

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setListeners(OnClickListener loginListener, OnClickListener registerListener) {
        findViewById(R.id.login_btn_login).setOnClickListener(loginListener);
        findViewById(R.id.login_btn_register).setOnClickListener(registerListener);

        ((TextInputLayout) this.findViewById(R.id.login_email)).getEditText().setText("manager@gmail.com");
        ((TextInputLayout) this.findViewById(R.id.login_password)).getEditText().setText("12345678");

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

    public TextInputLayout getUsernameInput() {
        return ((TextInputLayout) this.findViewById(R.id.login_email));
    }

    public TextInputLayout getPasswordInput() {
        return ((TextInputLayout) this.findViewById(R.id.login_password));
    }

    public String getUserName() {
        return ((TextInputLayout) this.findViewById(R.id.login_email)).getEditText().getText().toString();
    }

    public String getPassword() {
        return ((TextInputLayout) this.findViewById(R.id.login_password)).getEditText().getText().toString();
    }

    public String getEmailError() {
        return getContext().getString(R.string.err_email);
    }

    public String getPasswordError() {
        return getContext().getString(R.string.err_password);
    }

    public void setPasswordError(String error) {
        ((TextInputLayout) this.findViewById(R.id.login_password)).setError(error);
    }

    public void setLoginError(String error) {
        ((TextInputLayout) this.findViewById(R.id.login_email)).setError(error);
    }

}
