package br.ufop.ruapplicationmvp.presenter;

import android.content.SharedPreferences;

import br.ufop.ruapplicationmvp.contract.PosLoginContract;
import br.ufop.ruapplicationmvp.model.entity.User;
import br.ufop.ruapplicationmvp.service.PosLoginService;
import br.ufop.ruapplicationmvp.service.api.TokenManager;
import br.ufop.ruapplicationmvp.service.api.UserManager;

public class PosLoginPresenter implements PosLoginContract.Presenter, PosLoginContract.Model.OnFinishedListener {
    private PosLoginContract.View mView;
    private PosLoginService service;
    private SharedPreferences prefs;
    private int count = 0;

    public PosLoginPresenter(PosLoginContract.View mView, SharedPreferences prefs) {
        this.mView = mView;
        this.prefs = prefs;
        service = new PosLoginService(TokenManager.getInstance(this.prefs));
        if (TokenManager.getInstance(prefs).getToken().getAccessToken() != null && UserManager.getInstance(prefs).getUser().getEmail() != null) {
            mView.onSuccess(UserManager.getInstance(prefs).getUser());
        } else {
            getUser();
        }
    }


    @Override
    public void getUser() {
        service.getUser(this);
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public void onFinished(User user) {
        UserManager.getInstance(prefs).saveUser(user);
        mView.onSuccess(user);
    }

    @Override
    public void onError() {
        if (mView != null)
            getUser();
    }

    @Override
    public void onFailure() {
        if (mView != null)
            getUser();
    }
}
