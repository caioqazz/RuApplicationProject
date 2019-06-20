package br.ufop.ruapplicationmvp.presenter;

import android.content.SharedPreferences;

import java.util.List;

import br.ufop.ruapplicationmvp.contract.DemandDaysContract;
import br.ufop.ruapplicationmvp.model.entity.Day;
import br.ufop.ruapplicationmvp.service.DemandDaysService;
import br.ufop.ruapplicationmvp.service.api.TokenManager;

public class DemandDaysPresenter implements DemandDaysContract.Presenter, DemandDaysContract.Model.OnFinishedListener {
    private DemandDaysContract.View mView;
    private DemandDaysService mService;

    public DemandDaysPresenter(DemandDaysContract.View mView, SharedPreferences prefs) {
        this.mView = mView;
        mService = new DemandDaysService(TokenManager.getInstance(prefs));

    }


    @Override
    public void onFinished(List<Day> days) {
        if (mView != null) {
            mView.onRefreshFinished(days);
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
    public void onRefresh() {
        if (mView != null) {
            mView.showProgress();
            mService.index(this);
        }
    }
}
