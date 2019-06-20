package br.ufop.ruapplicationmvp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.custom.SimpleCustomValidation;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Map;

import br.ufop.ruapplicationmvp.R;
import br.ufop.ruapplicationmvp.contract.RegisterContract;
import br.ufop.ruapplicationmvp.model.response.ApiError;
import br.ufop.ruapplicationmvp.presenter.RegisterPresenter;
import br.ufop.ruapplicationmvp.util.DialogManager;
import br.ufop.ruapplicationmvp.util.Networking;
import br.ufop.ruapplicationmvp.util.Validate;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {
    @BindView(R.id.register_name)
    TextInputLayout mName;
    @BindView(R.id.register_email)
    TextInputLayout mEmail;
    @BindView(R.id.register_password)
    TextInputLayout mPassword;
    @BindView(R.id.register_cpf)
    TextInputLayout mCpf;


    private RegisterPresenter mPresenter;
    private AlertDialog mDialog;
    private AwesomeValidation validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mPresenter = new RegisterPresenter(this, getSharedPreferences("prefs", MODE_PRIVATE));
        ButterKnife.bind(this);
        validator = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        setupRules();

    }

    private void setupRules() {


        validator.addValidation(this, R.id.register_name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.err_name);
        validator.addValidation(mEmail, Patterns.EMAIL_ADDRESS, getResources().getString(R.string.err_email));
        validator.addValidation(this, R.id.register_cpf, new SimpleCustomValidation() {
            @Override
            public boolean compare(String cpf) {
                return Validate.isCPF(cpf);

            }
        }, R.string.err_cpf);
        validator.addValidation(mPassword, "[a-zA-Z0-9]{6,}", getResources().getString(R.string.err_password));
        validator.addValidation(this, R.id.register_password_verify, R.id.register_password, R.string.err_password_verify);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
        super.onBackPressed();
    }

    @OnClick(R.id.register_btn_register)
    public void onCommitButtonClick() {
        validator.clear();
        if (Networking.isNetworkConnected(this) && validator.validate())
            mPresenter.onCommitButtonClick(mName.getEditText().getText().toString(),
                    mEmail.getEditText().getText().toString(),
                    mCpf.getEditText().getText().toString(),
                    mPassword.getEditText().getText().toString()
            );
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();

    }


    @Override
    public void onRegisterSuccess() {
        startActivity(new Intent(this, PosLoginActivity.class));
        finish();
    }

    @Override
    public void handleFieldError(ApiError apiError) {
        for (Map.Entry<String, List<String>> error : apiError.getErrors().entrySet()) {
            if (error.getKey().equals("name")) {
                mName.setError(error.getValue().get(0));
            }
            if (error.getKey().equals("email")) {
                mEmail.setError(error.getValue().get(0));
            }
            if (error.getKey().equals("cpf")) {
                mCpf.setError(error.getValue().get(0));
            }
            if (error.getKey().equals("password")) {
                mPassword.setError(error.getValue().get(0));
            }
        }
    }

    @Override
    public void showProgress() {
        mDialog = DialogManager.loadingDialog(this);
        mDialog.show();
    }

    @Override
    public void hideProgress() {
        mDialog.hide();
    }


    @Override
    public void onRegisterError(String erroMsg) {
        Toasty.error(this, erroMsg, Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(Throwable t) {
        Toasty.warning(this, t.getMessage(), Toasty.LENGTH_LONG).show();
    }
}
