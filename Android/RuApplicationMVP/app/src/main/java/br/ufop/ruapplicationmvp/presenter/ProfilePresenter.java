package br.ufop.ruapplicationmvp.presenter;

import android.content.SharedPreferences;

import br.ufop.ruapplicationmvp.contract.ProfileContract;
import br.ufop.ruapplicationmvp.model.entity.User;
import br.ufop.ruapplicationmvp.service.ProfileService;
import br.ufop.ruapplicationmvp.service.api.TokenManager;
import okhttp3.MultipartBody;

public class ProfilePresenter implements ProfileContract.Presenter, ProfileContract.Model.OnFinishedListener {
    ProfileService mService;
    ProfileContract.View mView;

    public ProfilePresenter(ProfileContract.View mView, SharedPreferences prefs) {
        this.mView = mView;
        mService = new ProfileService(TokenManager.getInstance(prefs));

    }


    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public void onSendButtonClick(String name, MultipartBody.Part photo) {
        if (mView != null) {
            mView.showProgress();
            mService.update(this, name, photo);
        }
    }

    @Override
    public void onFinished(User user) {
        if (mView != null) {
            mView.onSuccess(user);
            mView.hideProgress();
        }
    }

    @Override
    public void onError(String errorMsg) {
        if (mView != null) {
            mView.onError(errorMsg);
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
}
