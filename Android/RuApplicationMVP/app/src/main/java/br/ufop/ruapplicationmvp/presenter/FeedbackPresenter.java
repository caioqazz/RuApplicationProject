package br.ufop.ruapplicationmvp.presenter;

import android.content.SharedPreferences;

import java.util.List;

import br.ufop.ruapplicationmvp.contract.FeedbackContract;
import br.ufop.ruapplicationmvp.model.entity.Feedback;
import br.ufop.ruapplicationmvp.service.FeedbackService;
import br.ufop.ruapplicationmvp.service.api.TokenManager;
import br.ufop.ruapplicationmvp.util.Constants;

public class FeedbackPresenter implements FeedbackContract.Presenter, FeedbackContract.Model.OnFinishedListener {
    private FeedbackContract.View mView;
    private FeedbackService mService;

    public FeedbackPresenter(FeedbackContract.View mView, SharedPreferences prefs) {
        this.mView = mView;
        mService = new FeedbackService(TokenManager.getInstance(prefs));

    }


    @Override
    public void onFinished(List<Feedback> feedbacks) {
        if (mView != null) {
            mView.onRefreshFinished(feedbacks);
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
    public void onDeleteButtonClick(int id) {
        if (mView != null) {
            mService.delete(this, id);
        }
    }

    @Override
    public void onRefresh(int option) {
        if (mView != null) {
            mView.showProgress();
            switch (option) {
                case Constants.MANAGER:
                    mService.index(this);
                    break;
                case Constants.CLIENT:
                    mService.indexByUser(this);
                    break;
            }

        }
    }
}
