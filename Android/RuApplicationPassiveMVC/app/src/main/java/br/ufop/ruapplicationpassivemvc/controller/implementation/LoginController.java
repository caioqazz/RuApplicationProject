package br.ufop.ruapplicationpassivemvc.controller.implementation;

import android.app.Activity;
import android.util.Patterns;
import android.view.View;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.custom.SimpleCustomValidation;
import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.activity.listener.LoginControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.listener.LoginServiceListener;
import br.ufop.ruapplicationpassivemvc.controller.listener.UserServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.User;
import br.ufop.ruapplicationpassivemvc.model.response.AccessToken;
import br.ufop.ruapplicationpassivemvc.model.response.ApiError;
import br.ufop.ruapplicationpassivemvc.service.LoginService;
import br.ufop.ruapplicationpassivemvc.service.UserService;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import br.ufop.ruapplicationpassivemvc.util.ConvertError;
import br.ufop.ruapplicationpassivemvc.util.Networking;
import br.ufop.ruapplicationpassivemvc.util.Validate;
import br.ufop.ruapplicationpassivemvc.view.LoginView;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class LoginController implements View.OnClickListener, LoginServiceListener, UserServiceListener {

    private TokenManager tokenManager;
    private LoginView loginView;
    private LoginControllerListener listener;
    private AwesomeValidation validator;

    public LoginController(LoginView loginView, LoginControllerListener listener) {
        this.loginView = loginView;
        this.listener = listener;

        validator = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        tokenManager = TokenManager.getInstance(loginView.getContext().getSharedPreferences("prefs", MODE_PRIVATE));
        if (tokenManager.getToken().getAccessToken() != null) {
            listener.onLoginSuccess(tokenManager.getUser());
        }
        setupRules();
    }

    private void setupRules() {

        validator.addValidation(loginView.getUsernameInput(), Patterns.EMAIL_ADDRESS, loginView.getEmailError());
        validator.addValidation(loginView.getPasswordInput(), "[a-zA-Z0-9]{6,}", loginView.getPasswordError());
        validator.addValidation((Activity) loginView.getContext(), R.id.login_password, new SimpleCustomValidation() {
            @Override
            public boolean compare(String s) {
                return Validate.lenghtMin(s, 8);
            }
        }, R.string.err_password);
    }

    @Override
    public void onClick(View v) {
        validator.clear();

        if (validator.validate() && Networking.isNetworkConnected(loginView)) {
            loginView.showLoading();
            LoginService service = new LoginService(this);
            service.autentication(loginView.getUserName(), loginView.getPassword());

        }
    }


    private void handleErrors(ResponseBody response) {

        ApiError apiError = ConvertError.converErrors(response);

        for (Map.Entry<String, List<String>> error : apiError.getErrors().entrySet()) {
            if (error.getKey().equals("username")) {
                loginView.setLoginError(error.getValue().get(0));
            }
            if (error.getKey().equals("password")) {
                loginView.setPasswordError(error.getValue().get(0));
            }
        }

    }

    @Override
    public void onLoginResponseSuccess(Response<AccessToken> response) {
        tokenManager.saveToken(response.body());
        UserService userService = new UserService(this, tokenManager);
        userService.getUser();

    }

    @Override
    public void onLoginResponseError422(Response<AccessToken> response) {
        handleErrors(response.errorBody());
        loginView.showForm();
    }

    @Override
    public void onLoginResponseError401(Response<AccessToken> response) {
        ApiError apiError = ConvertError.converErrors(response.errorBody());
        loginView.showForm();
        listener.onLoginError(apiError.getMessage());
    }

    @Override
    public void onLoginFailure(Throwable t) {
        loginView.showForm();
        listener.onLoginError(t.getMessage());

    }


    @Override
    public void onActionSuccess(Response<User> response, int option) {
        if (option == Constants.SHOW) {
            User user = response.body();
            tokenManager.saveUser(user);
            loginView.showForm();
            listener.onLoginSuccess(user);
        }
    }

    @Override
    public void onActionError(Response<User> response, int option) {
        loginView.showForm();
    }

    @Override
    public void onActionFailure(Throwable t, int option) {
        loginView.showForm();
    }
}
