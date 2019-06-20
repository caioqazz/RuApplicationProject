package br.ufop.ruapplicationmvvm.viewmodel;

import android.content.SharedPreferences;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import br.ufop.ruapplicationmvvm.model.result.NoticeResult;
import br.ufop.ruapplicationmvvm.service.NoticeService;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.listener.NoticeListener;
import br.ufop.ruapplicationmvvm.util.Status;

public class FormNoticeViewModel extends ViewModel implements NoticeListener.OnFinished {
    private MediatorLiveData<NoticeResult> result;
    private NoticeService service;

    public FormNoticeViewModel(SharedPreferences prefs) {
        result = new MediatorLiveData<>();
        service = new NoticeService(TokenManager.getInstance(prefs));
    }

    public MediatorLiveData<NoticeResult> getResult() {
        return result;
    }

    public void insert(String subject, String content, int type) {
        result.setValue(new NoticeResult(Status.LOADING, null, null, null));
        service.insert(this, subject, content, type);
    }

    @Override
    public void onRequestFinished(NoticeResult result) {
        this.result.setValue(result);
    }
}
