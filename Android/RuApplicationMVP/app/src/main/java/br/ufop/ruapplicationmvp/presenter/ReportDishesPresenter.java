package br.ufop.ruapplicationmvp.presenter;

import android.content.SharedPreferences;

import java.util.List;

import br.ufop.ruapplicationmvp.contract.ReportDishesContract;
import br.ufop.ruapplicationmvp.model.entity.Dish;
import br.ufop.ruapplicationmvp.model.entity.ReportDish;
import br.ufop.ruapplicationmvp.service.ReportDishesService;
import br.ufop.ruapplicationmvp.service.api.TokenManager;

public class ReportDishesPresenter implements ReportDishesContract.Presenter, ReportDishesContract.Model.OnFinishedListener {
    private ReportDishesContract.View mView;
    private ReportDishesService mService;

    public ReportDishesPresenter(ReportDishesContract.View mView, SharedPreferences prefs) {
        this.mView = mView;
        mService = new ReportDishesService(TokenManager.getInstance(prefs));

    }


    @Override
    public void onFinished(ReportDish response) {
        if (mView != null) {
            mView.onFinished(response);
            mView.hideProgress();
        }
    }

    @Override
    public void onFinishedIndex(List<Dish> dishes) {
        if (mView != null) {
            mView.onFinishedIndex(dishes);
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
    public void getDishes() {
        mService.index(this);
    }

    @Override
    public void onCommitButtonClick(int id) {
        if (mView != null) {
            mView.showProgress();
            mService.reportDish(this, id);
        }
    }
}
