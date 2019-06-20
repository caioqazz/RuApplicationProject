package br.ufop.ruapplicationmvp.presenter;

import android.content.SharedPreferences;

import br.ufop.ruapplicationmvp.contract.MenuContract;
import br.ufop.ruapplicationmvp.model.entity.Demand;
import br.ufop.ruapplicationmvp.model.response.MenuResponse;
import br.ufop.ruapplicationmvp.service.MenuService;
import br.ufop.ruapplicationmvp.service.api.TokenManager;

public class MenuPresenter implements MenuContract.Presenter, MenuContract.Model.OnFinishedListener {
    private MenuContract.View mView;
    private MenuService mService;

    public MenuPresenter(MenuContract.View mView, SharedPreferences prefs) {
        this.mView = mView;
        mService = new MenuService(TokenManager.getInstance(prefs));

    }


    @Override
    public void onFinished(Demand demand, int option) {
        if (mView != null) {
            mView.hideButtonProgress();
            mView.onCommitParticipation(demand, option);
        }
    }

    @Override
    public void onFinished(MenuResponse response) {
        if (mView != null) {
            mView.onRefreshFinished(response);
            mView.hideProgress();
        }
    }

    @Override
    public void onError(String message, int code) {
        if (mView != null) {
            mView.onError(message, code);
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
    public void insertParticipation(int type, String date, int option) {
        if (mView != null) {
            mView.showButtonProgress();
            mService.insertParticipation(this, type, date, option);
        }
    }

    @Override
    public void deleteParticipation(int type, String date) {
        if (mView != null) {
            mView.showButtonProgress();
            mService.deleteParticipation(this, type, date);
        }
    }

    @Override
    public void getParticipation(int type, String date) {
        if (mView != null)
            mService.getParticipation(this, type, date);
    }

    @Override
    public void onRefresh(int type, String date) {
        if (mView != null) {
            mView.showProgress();
            mService.getMenu(this, type, date);
        }
    }
}
