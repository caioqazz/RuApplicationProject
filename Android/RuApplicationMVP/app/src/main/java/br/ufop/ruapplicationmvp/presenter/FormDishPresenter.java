package br.ufop.ruapplicationmvp.presenter;

import android.content.SharedPreferences;

import br.ufop.ruapplicationmvp.contract.FormDishContract;
import br.ufop.ruapplicationmvp.model.entity.Dish;
import br.ufop.ruapplicationmvp.service.FormDishService;
import br.ufop.ruapplicationmvp.service.api.TokenManager;
import okhttp3.MultipartBody;

public class FormDishPresenter implements FormDishContract.Presenter, FormDishContract.Model.OnFinishedListener {
    private FormDishContract.View mView;
    private FormDishService mService;

    public FormDishPresenter(FormDishContract.View mView, SharedPreferences prefs) {
        this.mView = mView;
        mService = new FormDishService(TokenManager.getInstance(prefs));

    }

    @Override
    public void onFinished(Dish dish, int operation) {
        if (mView != null) {
            mView.onCommitFinished(dish, operation);
            mView.hideProgress();
        }
    }


    @Override
    public void onError(String message, int operation) {
        if (mView != null) {
            mView.onError(message, operation);
            mView.hideProgress();
        }
    }

    @Override
    public void onFailure(String message, int operation) {
        if (mView != null) {
            mView.onFailure(message, operation);
            mView.hideProgress();
        }
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public boolean validate(String name, int type, String description) {
        if (name.isEmpty() || type < 0 || description.isEmpty()) {
            mView.onError("Preencha todos os campos", 0);
            return false;
        }
        return true;
    }

    @Override
    public void insert(MultipartBody.Part photo, String name, int type, String description) {
        if (validate(name, type, description)) {
            mView.showProgress();
            mService.insert(this, photo, name, type, description);
        }
    }

    @Override
    public void update(MultipartBody.Part photo, int id, String name, int type, String description) {
        if (validate(name, type, description)) {
            mView.showProgress();
            mService.update(this, photo, id, name, type, description);
        }
    }


}
