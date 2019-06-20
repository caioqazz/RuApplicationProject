package br.ufop.ruapplicationmvp.presenter;

import android.content.SharedPreferences;

import java.util.List;

import br.ufop.ruapplicationmvp.contract.NoticeContract;
import br.ufop.ruapplicationmvp.model.entity.Notice;
import br.ufop.ruapplicationmvp.service.NoticeService;
import br.ufop.ruapplicationmvp.service.api.TokenManager;

public class NoticePresenter implements NoticeContract.Presenter, NoticeContract.Model.OnFinishedListener {
    private NoticeContract.View mView;
    private NoticeService mService;

    public NoticePresenter(NoticeContract.View mView, SharedPreferences prefs) {
        this.mView = mView;
        mService = new NoticeService(TokenManager.getInstance(prefs));

    }


    @Override
    public void onFinished(List<Notice> notices) {
        if (mView != null) {
            mView.onRefreshFinished(notices);
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
    public void onError(String message, int operation) {
        if (mView != null) {
            mView.onError(message, operation);
            mView.hideProgress();
        }
    }

    @Override
    public void onFailure(String message, int operation) {
        if (mView != null) {
            mView.onFailure(message, operation);
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
            mService.index(this);
        }
    }
}
