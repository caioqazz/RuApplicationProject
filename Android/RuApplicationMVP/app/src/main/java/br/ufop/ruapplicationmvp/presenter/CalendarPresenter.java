package br.ufop.ruapplicationmvp.presenter;

import android.content.SharedPreferences;

import java.util.List;

import br.ufop.ruapplicationmvp.contract.CalendarContract;
import br.ufop.ruapplicationmvp.model.entity.Meal;
import br.ufop.ruapplicationmvp.service.CalendarService;
import br.ufop.ruapplicationmvp.service.api.TokenManager;

public class CalendarPresenter  implements CalendarContract.Model.OnFinishedListener, CalendarContract.Presenter {
    private CalendarContract.View mView;
    private CalendarService mService;

    public CalendarPresenter(CalendarContract.View mView, SharedPreferences prefs) {
        this.mView = mView;
        mService = new CalendarService(TokenManager.getInstance(prefs));

    }


    @Override
    public void onFinished(List<Meal> meals) {
        if (mView != null) {
            mView.onRefreshFinished(meals);
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
