package br.ufop.ruapplicationmvp.presenter;

import android.content.SharedPreferences;

import java.util.List;
import java.util.Map;

import br.ufop.ruapplicationmvp.contract.LoginContract;
import br.ufop.ruapplicationmvp.model.response.AccessToken;
import br.ufop.ruapplicationmvp.model.response.ApiError;
import br.ufop.ruapplicationmvp.service.LoginService;
import br.ufop.ruapplicationmvp.service.api.TokenManager;
import br.ufop.ruapplicationmvp.util.ConvertError;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class LoginPresenter implements LoginContract.Presenter, LoginContract.Model.OnFinishedListener {
    private SharedPreferences prefs;
    private LoginContract.View mView;
    private LoginService mService;

    public LoginPresenter(LoginContract.View mView, SharedPreferences prefs) {
        this.mView = mView;
        this.prefs = prefs;
        mService = new LoginService();

        if (TokenManager.getInstance(prefs).getToken().getAccessToken() != null) {
            mView.onSuccessLogin();
        }
    }

    @Override
    public void onFinished(AccessToken accessToken) {

        if (mView != null) {
            mView.hideProgress();
            TokenManager.getInstance(prefs).saveToken(accessToken);
            mView.onSuccessLogin();
        }

    }

    @Override
    public void onError(Response<AccessToken> response) {
        if (mView != null) {
            if (response.code() == 401) {
                ApiError apiError = ConvertError.converErrors(response.errorBody());
                mView.onLoginError(apiError.getMessage());
            } else if (response.code() == 422) {
                handleErrors(response.errorBody());
            }
            mView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if (mView != null) {
            mView.onFailure(t);
            mView.hideProgress();
        }
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    private void handleErrors(ResponseBody response) {

        ApiError apiError = ConvertError.converErrors(response);

        for (Map.Entry<String, List<String>> error : apiError.getErrors().entrySet()) {
            if (error.getKey().equals("username")) {
                mView.setErrorOnEmail(error.getValue().get(0));
            }
            if (error.getKey().equals("password")) {
                mView.setErrorOnPassword(error.getValue().get(0));

            }
        }
    }

    @Override
    public void onLoginButtonClick(String username, String password) {

        mView.showProgress();
        mService.authentication(this, username, password);
    }


}
