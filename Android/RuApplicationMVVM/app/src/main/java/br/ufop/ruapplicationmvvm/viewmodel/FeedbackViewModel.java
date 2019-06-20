package br.ufop.ruapplicationmvvm.viewmodel;

import android.content.SharedPreferences;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import br.ufop.ruapplicationmvvm.model.result.FeedbackResult;
import br.ufop.ruapplicationmvvm.model.result.FeedbacksResult;
import br.ufop.ruapplicationmvvm.service.FeedbackService;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.listener.FeedbackListener;
import br.ufop.ruapplicationmvvm.util.Constants;
import br.ufop.ruapplicationmvvm.util.Status;

public class FeedbackViewModel extends ViewModel implements FeedbackListener.OnFinished, FeedbackListener.OnListFinished {
    private MediatorLiveData<FeedbacksResult> listResult;
    private MediatorLiveData<FeedbackResult> result;
    private FeedbackService service;

    public FeedbackViewModel(SharedPreferences prefs) {
        listResult = new MediatorLiveData<>();
        result = new MediatorLiveData<>();
        service = new FeedbackService(TokenManager.getInstance(prefs));
    }

    public MediatorLiveData<FeedbacksResult> getListResult() {
        return listResult;
    }

    public MediatorLiveData<FeedbackResult> getResult() {
        return result;
    }

    public void getFeedbacks(int type) {
        listResult.setValue(new FeedbacksResult(Status.LOADING, null, null, null));

        if (type == Constants.MANAGER)
            service.index(this);
        else
            service.indexByUser(this);
    }

    public void delete(int id) {
        result.setValue(new FeedbackResult(Status.LOADING, null, null, null));

        service.delete(id, this);
    }


    @Override
    public void onRequestFinished(FeedbackResult result) {
        this.result.setValue(result);
    }

    @Override
    public void onRequestFinished(FeedbacksResult result) {
        this.listResult.setValue(result);
    }
}
