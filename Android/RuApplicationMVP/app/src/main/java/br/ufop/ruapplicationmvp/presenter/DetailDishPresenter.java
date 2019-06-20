package br.ufop.ruapplicationmvp.presenter;

import android.content.SharedPreferences;

import br.ufop.ruapplicationmvp.contract.DetailDishContract;
import br.ufop.ruapplicationmvp.model.entity.Classification;
import br.ufop.ruapplicationmvp.service.DetailDishService;
import br.ufop.ruapplicationmvp.service.api.TokenManager;

public class DetailDishPresenter implements DetailDishContract.Presenter, DetailDishContract.Model.OnFinishedListener {
    private DetailDishContract.View mView;
    private DetailDishService mService;

    public DetailDishPresenter(DetailDishContract.View mView, SharedPreferences prefs) {
        this.mView = mView;
        mService = new DetailDishService(TokenManager.getInstance(prefs));

    }

    @Override
    public void onFinished(int option) {
        if (mView != null) {
            mView.onFinished(option);
            mView.hideProgress();
        }
    }

    @Override
    public void onFinished(Classification classification) {
        if (mView != null) {
            mView.onShowFinished(classification);

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
    public void updateClassification(int dish, float rate, boolean option, String comment) {
        mView.showProgress();
        mService.updateClassification(this, dish, rate, option, comment);
    }

    @Override
    public void showClassification(int id) {
        mService.showClassification(this, id);
    }


    @Override
    public void delete(int id) {
        mView.showProgress();
        mService.delete(this, id);
    }


}
