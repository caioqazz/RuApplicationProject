package br.ufop.ruapplicationmvp.presenter;

import android.content.SharedPreferences;

import java.util.List;

import br.ufop.ruapplicationmvp.contract.DayMealContract;
import br.ufop.ruapplicationmvp.model.entity.Day;
import br.ufop.ruapplicationmvp.model.entity.Week;
import br.ufop.ruapplicationmvp.service.DayMealService;
import br.ufop.ruapplicationmvp.service.api.TokenManager;

public class DayMealPresenter implements DayMealContract.Model.OnFinishedListener, DayMealContract.Presenter {
    private DayMealContract.View mView;
    private DayMealService mService;
    private Week week;

    public DayMealPresenter(DayMealContract.View mView, SharedPreferences prefs, Week week) {
        this.mView = mView;
        mService = new DayMealService(TokenManager.getInstance(prefs));
        this.week = week;
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
            mService.index(this, week.getWeekOfMonth(), week.getMonth(), week.getYear());
        }
    }
}
