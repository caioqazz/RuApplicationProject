package br.ufop.ruapplicationmvp.presenter;

import android.content.SharedPreferences;

import br.ufop.ruapplicationmvp.contract.RegisterContract;
import br.ufop.ruapplicationmvp.model.response.AccessToken;
import br.ufop.ruapplicationmvp.model.response.ApiError;
import br.ufop.ruapplicationmvp.service.RegisterService;
import br.ufop.ruapplicationmvp.service.api.TokenManager;
import br.ufop.ruapplicationmvp.util.ConvertError;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class RegisterPresenter implements RegisterContract.Presenter, RegisterContract.Model.OnFinishedListener {
    RegisterContract.View mView;
    RegisterService mService;
    private SharedPreferences mPrefs;

    public RegisterPresenter(RegisterContract.View mView, SharedPreferences prefs) {
        this.mView = mView;
        mPrefs = prefs;
        mService = new RegisterService();
    }

    @Override
    public void onCommitButtonClick(String name, String email, String cpf, String password) {
        if (mView != null) {
            mService.register(this, name, email, cpf, password);
            mView.showProgress();
        }

    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public void onFinished(AccessToken accessToken) {
        if (mView != null) {
            mView.hideProgress();
            TokenManager.getInstance(mPrefs).saveToken(accessToken);
            mView.onRegisterSuccess();
        }
    }

    @Override
    public void onError(Response<AccessToken> response) {
        if (mView != null) {
            handleErrors(response.errorBody());
            mView.hideProgress();
            mView.onRegisterError(response.message());
        }
    }

    private void handleErrors(ResponseBody response) {
        ApiError apiError = ConvertError.converErrors(response);
        mView.handleFieldError(apiError);


    }

    @Override
    public void onFailure(Throwable t) {
        if (mView != null) {
            mView.onFailure(t);
            mView.hideProgress();
        }
    }
}
