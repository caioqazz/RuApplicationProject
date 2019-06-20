package br.ufop.ruapplicationmvp.presenter;

import android.content.SharedPreferences;

import br.ufop.ruapplicationmvp.contract.FormNoticeContract;
import br.ufop.ruapplicationmvp.model.entity.Notice;
import br.ufop.ruapplicationmvp.service.FormNoticeService;
import br.ufop.ruapplicationmvp.service.api.TokenManager;

public class FormNoticePresenter implements FormNoticeContract.Presenter, FormNoticeContract.Model.OnFinishedListener {
    private FormNoticeContract.View mView;
    private FormNoticeService mService;

    public FormNoticePresenter(FormNoticeContract.View mView, SharedPreferences prefs) {
        this.mView = mView;
        mService = new FormNoticeService(TokenManager.getInstance(prefs));

    }

    @Override
    public void onFinished(Notice notice) {
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
    public boolean validate(String subject, String content, int type) {
        if (subject.isEmpty() || type < 0 || content.isEmpty()) {
            mView.onError("Preencha todos os campos");
            return false;
        }
        return true;
    }

    @Override
    public void onCommitButton(String subject, String content, int type) {
        if (validate(subject, content, type)) {
            mView.showProgress();
            mService.insert(this, subject, content, type);
        }
    }
}
