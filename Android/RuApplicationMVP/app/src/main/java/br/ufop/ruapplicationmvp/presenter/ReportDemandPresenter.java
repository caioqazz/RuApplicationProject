package br.ufop.ruapplicationmvp.presenter;

import android.content.SharedPreferences;

import br.ufop.ruapplicationmvp.contract.ReportDemandContract;
import br.ufop.ruapplicationmvp.model.response.ReportDemandResponse;
import br.ufop.ruapplicationmvp.service.ReportDemandService;
import br.ufop.ruapplicationmvp.service.api.TokenManager;

public class ReportDemandPresenter implements ReportDemandContract.Presenter, ReportDemandContract.Model.OnFinishedListener {
    private ReportDemandContract.View mView;
    private ReportDemandService mService;

    public ReportDemandPresenter(ReportDemandContract.View mView, SharedPreferences prefs) {
        this.mView = mView;
        mService = new ReportDemandService(TokenManager.getInstance(prefs));

    }


    @Override
    public void onFinished(ReportDemandResponse response) {
        if (mView != null) {
            mView.onFinished(response);
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
    public void onCommitButtonClick(int month, int year) {
        if (mView != null) {
            mView.showProgress();
            mService.reportDemand(this, month, year);
        }
    }
}
