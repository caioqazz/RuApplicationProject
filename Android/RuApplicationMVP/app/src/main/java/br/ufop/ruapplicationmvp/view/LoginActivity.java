package br.ufop.ruapplicationmvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.custom.SimpleCustomValidation;
import com.google.android.material.textfield.TextInputLayout;

import br.ufop.ruapplicationmvp.R;
import br.ufop.ruapplicationmvp.contract.LoginContract;
import br.ufop.ruapplicationmvp.presenter.LoginPresenter;
import br.ufop.ruapplicationmvp.util.DialogManager;
import br.ufop.ruapplicationmvp.util.Networking;
import br.ufop.ruapplicationmvp.util.Validate;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    @BindView(R.id.login_email)
    TextInputLayout mEmail;
    @BindView(R.id.login_password)
    TextInputLayout mPassword;
    private LoginPresenter mPresenter;
    private AlertDialog mDialog;
    private AwesomeValidation validator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPresenter = new LoginPresenter(this, getSharedPreferences("prefs", MODE_PRIVATE));
        ButterKnife.bind(this);
        validator = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        setupRules();
        mDialog = DialogManager.loadingDialog(this);
        mEmail.getEditText().setText("manager@gmail.com");
        mPassword.getEditText().setText("12345678");

    }

    private void setupRules() {

        validator.addValidation(mEmail, Patterns.EMAIL_ADDRESS, getResources().getString(R.string.err_email));
        validator.addValidation(mPassword, "[a-zA-Z0-9]{6,}", getResources().getString(R.string.err_password));
        validator.addValidation(this, R.id.login_password, new SimpleCustomValidation() {
            @Override
            public boolean compare(String password) {
                return Validate.lenghtMin(password, 8);
            }
        }, R.string.err_password);
    }


    @OnClick(R.id.login_btn_login)
    public void onLoginButtonClick() {
        validator.clear();
        if (Networking.isNetworkConnected(this) && validator.validate())
            mPresenter.onLoginButtonClick(mEmail.getEditText().getText().toString(), mPassword.getEditText().getText().toString());
    }

    @OnClick(R.id.login_btn_register)
    public void onRegisterButtonClick() {
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }

    @Override
    public void showProgress() {
        mDialog.show();
    }


    @Override
    public void hideProgress() {
        mDialog.hide();
    }

    @Override
    public void setErrorOnEmail(String errorMsg) {
        mEmail.setError(errorMsg);
    }

    @Override
    public void setErrorOnPassword(String errorMsg) {
        mPassword.setError(errorMsg);
    }

    @Override
    public void onLoginError(String message) {
        Toasty.error(this, message, Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(Throwable t) {
        Toasty.warning(this, t.getMessage(), Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onSuccessLogin() {
        startActivity(new Intent(this, PosLoginActivity.class));
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();

    }

}
