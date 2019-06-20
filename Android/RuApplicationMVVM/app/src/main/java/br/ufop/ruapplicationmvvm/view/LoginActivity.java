package br.ufop.ruapplicationmvvm.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;
import java.util.Map;

import br.ufop.ruapplicationmvvm.R;
import br.ufop.ruapplicationmvvm.databinding.ActivityLoginBinding;
import br.ufop.ruapplicationmvvm.model.info.LoginForm;
import br.ufop.ruapplicationmvvm.model.response.AccessToken;
import br.ufop.ruapplicationmvvm.model.response.ApiError;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.util.ConvertError;
import br.ufop.ruapplicationmvvm.util.DialogManager;
import br.ufop.ruapplicationmvvm.util.Status;
import br.ufop.ruapplicationmvvm.viewmodel.LoginViewModel;
import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel mViewModel;
    private AlertDialog mDialog;
    private ActivityLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setLoginform(new LoginForm());
        if (TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).getToken().getAccessToken() != null)
            onLogged();

        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding.setViewmodel(mViewModel);
        mDialog = DialogManager.loadingDialog(this);
        binding.setLifecycleOwner(this);


        mViewModel.getResult().observe(this, result -> {
            onLoading(result.getStatus() == Status.LOADING);

            if (result.getStatus() == Status.SUCCESS)
                onSuccess(result.getData().body());
            else if (result.getStatus() == Status.ERROR)
                onError(result.getData());
            else if (result.getStatus() == Status.FAILURE)
                onFailure(result.getFailure().getMessage());
        });

    }

    private void onLoading(boolean isLoading) {
        if (isLoading)
            mDialog.show();
        else
            mDialog.hide();

    }

    private void onError(Response<AccessToken> data) {
        if (data.code() == 401) {
            ApiError apiError = ConvertError.converErrors(data.errorBody());
            Toasty.error(this, apiError.getMessage(), Toasty.LENGTH_LONG).show();
        } else if (data.code() == 422) {
            handleErrors(data.errorBody());
        }

    }

    private void onFailure(String message) {
        Toasty.warning(this, message, Toasty.LENGTH_LONG).show();

    }

    private void onLogged() {
        startActivity(new Intent(this, PosLoginActivity.class));
        finish();
    }

    private void onSuccess(AccessToken success) {
        TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).saveToken(success);
        onLogged();

    }


    private void handleErrors(ResponseBody response) {

        ApiError apiError = ConvertError.converErrors(response);

        for (Map.Entry<String, List<String>> error : apiError.getErrors().entrySet()) {
            if (error.getKey().equals("username")) {
                binding.loginEmail.getEditText().setError(error.getValue().get(0));
            }
            if (error.getKey().equals("password")) {
                binding.loginPassword.getEditText().setError(error.getValue().get(0));

            }
        }
    }


    public void onRegisterButtonClick() {
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }
}
