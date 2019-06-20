package br.ufop.ruapplicationpassivemvc.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.usuario.ruapplicationpassivemvc.R;

public class RegisterView extends RelativeLayout {
    AlertDialog dialog;

    public RegisterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setListeners(OnClickListener onClickListener) {
        findViewById(R.id.register_btn_register).setOnClickListener(onClickListener);
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

    public TextInputLayout getEmailInput() {
        return ((TextInputLayout) this.findViewById(R.id.register_email));
    }

    public TextInputLayout getPasswordInput() {
        return ((TextInputLayout) this.findViewById(R.id.register_password));
    }

    public String getEmail() {
        return ((TextInputLayout) this.findViewById(R.id.register_email)).getEditText().getText().toString();
    }

    public String getPassword() {
        return ((TextInputLayout) this.findViewById(R.id.register_password)).getEditText().getText().toString();
    }

    public String getCpf() {
        return ((TextInputLayout) this.findViewById(R.id.register_cpf)).getEditText().getText().toString();
    }

    public String getName() {
        return ((TextInputLayout) this.findViewById(R.id.register_name)).getEditText().getText().toString();
    }

    public String getEmailError() {
        return getContext().getString(R.string.err_email);
    }

    public void setEmailError(String error) {
        ((TextInputLayout) this.findViewById(R.id.register_email)).setError(error);
    }

    public String getPasswordError() {
        return getContext().getString(R.string.err_password);
    }

    public void setPasswordError(String error) {
        ((TextInputLayout) this.findViewById(R.id.register_password)).setError(error);
    }

    public void setCpfError(String error) {
        ((TextInputLayout) this.findViewById(R.id.register_cpf)).setError(error);
    }

    public void setNameError(String error) {
        ((TextInputLayout) this.findViewById(R.id.register_name)).setError(error);
    }


}
