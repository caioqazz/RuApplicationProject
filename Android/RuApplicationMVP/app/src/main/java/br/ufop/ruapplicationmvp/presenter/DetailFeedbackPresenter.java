package br.ufop.ruapplicationmvp.presenter;

import android.content.SharedPreferences;

import br.ufop.ruapplicationmvp.contract.DetailFeedbackContract;
import br.ufop.ruapplicationmvp.model.entity.Feedback;
import br.ufop.ruapplicationmvp.service.DetailFeedbackService;
import br.ufop.ruapplicationmvp.service.api.TokenManager;

public class DetailFeedbackPresenter implements DetailFeedbackContract.Presenter, DetailFeedbackContract.Model.OnFinishedListener {
    private DetailFeedbackContract.View mView;
    private DetailFeedbackService mService;

    public DetailFeedbackPresenter(DetailFeedbackContract.View mView, SharedPreferences prefs) {
        this.mView = mView;
        mService = new DetailFeedbackService(TokenManager.getInstance(prefs));

    }

    @Override
    public void onFinished(Feedback feedback, int option) {
        if (mView != null) {
            mView.onFinished(feedback, option);
            mView.hideProgress();
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
    public boolean validate(String reply) {
        if (reply.isEmpty()) {
            mView.onError("Preencha todos os campos!", 0);
            return false;
        }
        return true;
    }


    @Override
    public void reply(int id, String subject, String content, String reply) {
        if (validate(reply)) {
            mView.showProgress();
            mService.reply(this, id, subject, content, reply);
        }
    }

    @Override
    public void delete(int id) {
        mView.showProgress();
        mService.delete(this, id);
    }

    @Override
    public void onFeedbackVisualized(int type, int managerView, int userView) {
        mService.setFeedbackView(type, managerView, userView);
    }


}
