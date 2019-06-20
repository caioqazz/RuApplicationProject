package br.ufop.ruapplicationmvvm.viewmodel;

import android.content.SharedPreferences;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import br.ufop.ruapplicationmvvm.model.result.FeedbackResult;
import br.ufop.ruapplicationmvvm.service.FeedbackService;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.listener.FeedbackListener;
import br.ufop.ruapplicationmvvm.util.Status;

public class FormFeedbackViewModel extends ViewModel implements FeedbackListener.OnFinished {
    private MediatorLiveData<FeedbackResult> result;
    private FeedbackService service;

    public FormFeedbackViewModel(SharedPreferences prefs) {
        result = new MediatorLiveData<>();
        service = new FeedbackService(TokenManager.getInstance(prefs));
    }

    public MediatorLiveData<FeedbackResult> getResult() {
        return result;
    }

    public void insert(String subject, String content) {
        result.setValue(new FeedbackResult(Status.LOADING, null, null, null));

        service.insert(this, subject, content);
    }


    @Override
    public void onRequestFinished(FeedbackResult result) {
        this.result.setValue(result);
    }
}
