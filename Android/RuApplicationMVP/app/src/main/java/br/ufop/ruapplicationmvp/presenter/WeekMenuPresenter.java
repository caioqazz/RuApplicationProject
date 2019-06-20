package br.ufop.ruapplicationmvp.presenter;

import android.content.SharedPreferences;

import java.util.List;

import br.ufop.ruapplicationmvp.contract.WeekMenuContract;
import br.ufop.ruapplicationmvp.model.entity.Week;
import br.ufop.ruapplicationmvp.service.WeekMenuService;
import br.ufop.ruapplicationmvp.service.api.TokenManager;

public class WeekMenuPresenter implements WeekMenuContract.Presenter, WeekMenuContract.Model.OnFinishedListener {
    private WeekMenuContract.View mView;
    private WeekMenuService mService;

    public WeekMenuPresenter(WeekMenuContract.View mView, SharedPreferences prefs) {
        this.mView = mView;
        mService = new WeekMenuService(TokenManager.getInstance(prefs));

    }


    @Override
    public void onFinished(List<Week> weeks) {
        if (mView != null) {
            mView.onRefreshFinished(weeks);
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
