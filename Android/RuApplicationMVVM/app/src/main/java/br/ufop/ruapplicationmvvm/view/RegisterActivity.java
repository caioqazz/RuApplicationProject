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
import br.ufop.ruapplicationmvvm.databinding.ActivityRegisterBinding;
import br.ufop.ruapplicationmvvm.model.info.RegisterForm;
import br.ufop.ruapplicationmvvm.model.response.AccessToken;
import br.ufop.ruapplicationmvvm.model.response.ApiError;
import br.ufop.ruapplicationmvvm.util.ConvertError;
import br.ufop.ruapplicationmvvm.util.DialogManager;
import br.ufop.ruapplicationmvvm.util.Status;
import br.ufop.ruapplicationmvvm.viewmodel.RegisterViewModel;
import es.dmoral.toasty.Toasty;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private RegisterViewModel mViewModel;
    private AlertDialog mDialog;
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mDialog = DialogManager.loadingDialog(this);
        mViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        binding.setViewmodel(mViewModel);
        binding.setForm(new RegisterForm());
        binding.setLifecycleOwner(this);

        mViewModel.getResult().observe(this, result -> {
            onLoading(result.getStatus() == Status.LOADING);

            if (result.getStatus() == Status.SUCCESS)
                onSuccess(result.getData().body());
            else if (result.getStatus() == Status.ERROR)
                onError(result.getData());
            else if (result.getStatus() == Status.FAILURE)
                onFailure(result.getFailure());
        });
    }

    private void onLoading(boolean isLoading) {

        if (isLoading) {
            mDialog.show();
        } else {
            mDialog.hide();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
        super.onBackPressed();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void onSuccess(AccessToken success) {
//        TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).saveToken(success);
//        startActivity(new Intent(this, PosLoginActivity.class));
//        finish();
    }

    private void handleFieldError(ApiError apiError) {
        for (Map.Entry<String, List<String>> error : apiError.getErrors().entrySet()) {
            if (error.getKey().equals("name")) {
                binding.registerName.setError(error.getValue().get(0));
            }
            if (error.getKey().equals("email")) {
                binding.registerEmail.setError(error.getValue().get(0));
            }
            if (error.getKey().equals("cpf")) {
                binding.registerCpf.setError(error.getValue().get(0));
            }
            if (error.getKey().equals("password")) {
                binding.registerPassword.setError(error.getValue().get(0));
            }
        }
    }

    private void onError(Response<AccessToken> response) {
        ApiError apiError = ConvertError.converErrors(response.errorBody());
        handleFieldError(apiError);
        Toasty.error(this, response.message(), Toasty.LENGTH_LONG).show();
    }


    private void onFailure(String message) {
        Toasty.warning(this, message, Toasty.LENGTH_LONG).show();
    }


}
