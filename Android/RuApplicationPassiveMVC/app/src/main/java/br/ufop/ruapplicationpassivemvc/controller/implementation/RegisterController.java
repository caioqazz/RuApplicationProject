package br.ufop.ruapplicationpassivemvc.controller.implementation;

import android.app.Activity;
import android.content.Context;
import android.util.Patterns;
import android.view.View;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.custom.SimpleCustomValidation;
import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.activity.listener.RegisterControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.listener.RegisterServiceListener;
import br.ufop.ruapplicationpassivemvc.model.response.AccessToken;
import br.ufop.ruapplicationpassivemvc.model.response.ApiError;
import br.ufop.ruapplicationpassivemvc.service.RegisterService;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.ConvertError;
import br.ufop.ruapplicationpassivemvc.util.Networking;
import br.ufop.ruapplicationpassivemvc.util.Validate;
import br.ufop.ruapplicationpassivemvc.view.RegisterView;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class RegisterController implements View.OnClickListener, RegisterServiceListener {

    private RegisterView registerView;
    private RegisterControllerListener listener;
    private AwesomeValidation validator;
    private TokenManager tokenManager;


    public RegisterController(RegisterView registerView, RegisterControllerListener listener) {
        this.registerView = registerView;
        this.listener = listener;

        validator = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        tokenManager = TokenManager.getInstance(registerView.getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE));
        setupRules();
    }

    private void setupRules() {

        validator.addValidation((Activity) registerView.getContext(), R.id.register_name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.err_name);
        validator.addValidation(registerView.getEmailInput(), Patterns.EMAIL_ADDRESS, registerView.getEmailError());
        validator.addValidation((Activity) registerView.getContext(), R.id.register_cpf, new SimpleCustomValidation() {
            @Override
            public boolean compare(String cpf) {
                return Validate.isCPF(cpf);

            }
        }, R.string.err_cpf);
        validator.addValidation(registerView.getPasswordInput(), "[a-zA-Z0-9]{6,}", registerView.getPasswordError());
        validator.addValidation((Activity) registerView.getContext(), R.id.register_password_verify, R.id.register_password, R.string.err_password_verify);
    }

    @Override
    public void onClick(View v) {
        validator.clear();

        if (validator.validate() && Networking.isNetworkConnected(registerView)) {
            registerView.showLoading();
            RegisterService service = new RegisterService(this);
            service.register(registerView.getName(), registerView.getEmail(), registerView.getCpf(), registerView.getPassword());
        }
    }

    private void handleErrors(ResponseBody response) {

        ApiError apiError = ConvertError.converErrors(response);

        for (Map.Entry<String, List<String>> error : apiError.getErrors().entrySet()) {
            if (error.getKey().equals("name")) {
                registerView.setNameError(error.getValue().get(0));
            }
            if (error.getKey().equals("email")) {
                registerView.setEmailError(error.getValue().get(0));
            }
            if (error.getKey().equals("cpf")) {
                registerView.setCpfError(error.getValue().get(0));
            }
            if (error.getKey().equals("password")) {
                registerView.setPasswordError(error.getValue().get(0));
            }
        }

    }

    @Override
    public void onRegisterResponseSuccess(Response<AccessToken> response) {
        tokenManager.saveToken(response.body());
        listener.onRegisterSuccess();
        registerView.showForm();
    }

    @Override
    public void onRegisterResponseSuccessError(Response<AccessToken> response) {
        handleErrors(response.errorBody());
        registerView.showForm();
    }

    @Override
    public void onRegisterFailure(Throwable t) {
        registerView.showForm();
        listener.onRegisterError(t.getMessage());
    }
}
