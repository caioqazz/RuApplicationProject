package br.ufop.ruapplicationmvvm.viewmodel;

import android.content.SharedPreferences;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import br.ufop.ruapplicationmvvm.model.result.FeedbackResult;
import br.ufop.ruapplicationmvvm.service.FeedbackService;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.listener.FeedbackListener;
import br.ufop.ruapplicationmvvm.util.Status;

public class DetailFeedbackViewModel extends ViewModel implements FeedbackListener.OnFinished {
    private MediatorLiveData<FeedbackResult> result;
    private FeedbackService service;

    public DetailFeedbackViewModel(SharedPreferences prefs) {
        result = new MediatorLiveData<>();
        service = new FeedbackService(TokenManager.getInstance(prefs));
    }

    public MediatorLiveData<FeedbackResult> getResult() {
        return result;
    }

    public void reply(int id, String subject, String content, String reply) {
        result.setValue(new FeedbackResult(Status.LOADING, null, null, null));

        service.reply(this, id, subject, content, reply);
    }

    public void delete(int id) {
        result.setValue(new FeedbackResult(Status.LOADING, null, null, null));

        service.delete(id, this);
    }

    public void onFeedbackVisualized(int type, int managerView, int userView) {
        service.setFeedbackView(type, managerView, userView);
    }

    @Override
    public void onRequestFinished(FeedbackResult result) {
        this.result.setValue(result);
    }
}
