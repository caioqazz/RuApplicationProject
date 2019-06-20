package br.ufop.ruapplicationmvp.presenter;

import android.content.SharedPreferences;

import br.ufop.ruapplicationmvp.contract.FormFeedbackContract;
import br.ufop.ruapplicationmvp.model.entity.Feedback;
import br.ufop.ruapplicationmvp.service.FormFeedbackService;
import br.ufop.ruapplicationmvp.service.api.TokenManager;

public class FormFeedbackPresenter implements FormFeedbackContract.Presenter, FormFeedbackContract.Model.OnFinishedListener {
    private FormFeedbackContract.View mView;
    private FormFeedbackService mService;

    public FormFeedbackPresenter(FormFeedbackContract.View mView, SharedPreferences prefs) {
        this.mView = mView;
        mService = new FormFeedbackService(TokenManager.getInstance(prefs));

    }

    @Override
    public void onFinished(Feedback notice) {
        if (mView != null) {
            mView.onCommitFinished(notice);
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
    public boolean validate(String subject, String content) {
        if (subject.isEmpty() || content.isEmpty()) {
            mView.onError("Preencha todos os campos");
            return false;
        }
        return true;
    }

    @Override
    public void onCommitButton(String subject, String content) {
        if (validate(subject, content)) {
            mView.showProgress();
            mService.insert(this, subject, content);
        }
    }
}
