package br.ufop.ruapplicationmvp.presenter;

import android.content.SharedPreferences;

import br.ufop.ruapplicationmvp.contract.FormCalendarContract;
import br.ufop.ruapplicationmvp.model.entity.Meal;
import br.ufop.ruapplicationmvp.service.FormCalendarService;
import br.ufop.ruapplicationmvp.service.api.TokenManager;
import br.ufop.ruapplicationmvp.util.Constants;

public class FormCalendarPresenter implements FormCalendarContract.Presenter, FormCalendarContract.Model.OnFinishedListener {
    private FormCalendarContract.View mView;
    private FormCalendarService mService;

    public FormCalendarPresenter(FormCalendarContract.View mView, SharedPreferences prefs) {
        this.mView = mView;
        mService = new FormCalendarService(TokenManager.getInstance(prefs));

    }

    @Override
    public void onFinished(Meal meal) {
        if (mView != null) {
            mView.onCommitFinished(meal);
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
    public boolean validate(String date, int type, int status, String reason) {
        if (date.equals("Selecione a data")) {
            mView.onError("Selecione uma data para proseguir");
            return false;
        } else if (reason.isEmpty() && status == Constants.FECHADO) {
            mView.onError("Preencha a justificativa");
            return false;
        }
        return true;
    }

    @Override
    public void onCommitButton(String date, int type, int status, String reason) {
        if (validate(date, type, status, reason)) {
            mView.showProgress();
            mService.update(this, date, type, status, reason);
        }
    }
}
