package br.ufop.ruapplicationmvp.presenter;

import android.content.SharedPreferences;

import br.ufop.ruapplicationmvp.contract.DetailNoticeContract;
import br.ufop.ruapplicationmvp.service.DetailNoticeService;
import br.ufop.ruapplicationmvp.service.api.TokenManager;

public class DetailNoticePresenter implements DetailNoticeContract.Presenter, DetailNoticeContract.Model.OnFinishedListener {
    private DetailNoticeContract.View mView;
    private DetailNoticeService mService;

    public DetailNoticePresenter(DetailNoticeContract.View mView, SharedPreferences prefs) {
        this.mView = mView;
        mService = new DetailNoticeService(TokenManager.getInstance(prefs));

    }

    @Override
    public void onFinished() {
        if (mView != null) {
            mView.onCommitFinished();
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
    public void onCommitButton(int id) {
        mView.showProgress();
        mService.delete(this, id);

    }

    @Override
    public void setViewed(int id) {
        mService.setViewed(id);
    }
}
