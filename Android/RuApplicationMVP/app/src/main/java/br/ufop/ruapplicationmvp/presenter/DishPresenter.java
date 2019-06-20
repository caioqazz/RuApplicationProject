package br.ufop.ruapplicationmvp.presenter;

import android.content.SharedPreferences;

import java.util.List;

import br.ufop.ruapplicationmvp.contract.DishContract;
import br.ufop.ruapplicationmvp.model.entity.Dish;
import br.ufop.ruapplicationmvp.service.DishService;
import br.ufop.ruapplicationmvp.service.api.TokenManager;

public class DishPresenter implements DishContract.Model.OnFinishedListener, DishContract.Presenter {
    private DishContract.View mView;
    private DishService mService;
    private int type;

    public DishPresenter(DishContract.View mView, SharedPreferences prefs, int type) {
        this.mView = mView;
        mService = new DishService(TokenManager.getInstance(prefs));
        this.type = type;
    }


    @Override
    public void onFinished(List<Dish> dishes) {
        if (mView != null) {
            mView.onRefreshFinished(dishes);
            mView.hideProgress();
        }
    }

    @Override
    public void onFinishedDelete() {
        if (mView != null) {
            mView.onFinishedDelete();
        }
    }

    @Override
    public void onError(String message, int option) {
        if (mView != null) {
            mView.onError(message, option);
            mView.hideProgress();
        }
    }

    @Override
    public void onFailure(String message, int option) {
        if (mView != null) {
            mView.onFailure(message, option);
            mView.hideProgress();
        }
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public void onDeleteButtonClick(int id) {
        mService.delete(this, id);
    }

    @Override
    public void onRefresh() {
        if (mView != null) {
            mView.showProgress();
            mService.index(this, type);
        }
    }
}
