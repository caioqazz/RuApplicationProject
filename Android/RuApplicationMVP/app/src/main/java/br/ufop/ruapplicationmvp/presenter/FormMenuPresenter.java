package br.ufop.ruapplicationmvp.presenter;

import android.content.SharedPreferences;

import java.util.List;

import br.ufop.ruapplicationmvp.contract.FormMenuContract;
import br.ufop.ruapplicationmvp.model.entity.Dish;
import br.ufop.ruapplicationmvp.service.FormMenuService;
import br.ufop.ruapplicationmvp.service.api.TokenManager;

public class FormMenuPresenter implements FormMenuContract.Presenter, FormMenuContract.Model.OnFinishedListener {
    private FormMenuContract.View mView;
    private FormMenuService mService;

    public FormMenuPresenter(FormMenuContract.View mView, SharedPreferences prefs) {
        this.mView = mView;
        mService = new FormMenuService(TokenManager.getInstance(prefs));

    }

    @Override
    public void onFinished(List<Dish> dishes) {
        if (mView != null) {
            mView.onFinished(dishes);
            mView.hideProgress();
        }
    }

    @Override
    public void onFinished(Dish dish) {
        if (mView != null) {
            mView.onFinished(dish);
            mView.hideProgress();
        }
    }


    @Override
    public void onError(String message) {
        if (mView != null) {
            mView.onError(message);
            mView.hideProgress();
        }
    }

    @Override
    public void onFailure(String message) {
        if (mView != null) {
            mView.onFailure(message);
            mView.hideProgress();
        }
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public void insert(String date, int type, int dishId, int dishType) {
        mService.insert(this, date, type, dishId, dishType);
        mView.showProgress();
    }

    @Override
    public void update(String date, int type, int dishId, int dishType) {
        mService.update(this, date, type, dishId, dishType);
        mView.showProgress();
    }

    @Override
    public void getDishes(int dishType) {
        mService.indexByType(this, dishType);
    }


}
