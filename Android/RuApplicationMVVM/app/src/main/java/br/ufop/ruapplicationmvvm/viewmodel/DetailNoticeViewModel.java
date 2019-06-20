package br.ufop.ruapplicationmvvm.viewmodel;

import android.content.SharedPreferences;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import br.ufop.ruapplicationmvvm.model.result.NoticeResult;
import br.ufop.ruapplicationmvvm.service.NoticeService;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.listener.NoticeListener;
import br.ufop.ruapplicationmvvm.util.Status;

public class DetailNoticeViewModel extends ViewModel implements NoticeListener.OnFinished {
    private MediatorLiveData<NoticeResult> result;
    private NoticeService service;

    public DetailNoticeViewModel(SharedPreferences prefs) {
        result = new MediatorLiveData<>();
        service = new NoticeService(TokenManager.getInstance(prefs));
    }

    public MediatorLiveData<NoticeResult> getResult() {
        return result;
    }

    public void delete(int id) {
        result.setValue(new NoticeResult(Status.LOADING, null, null, null));
        service.delete(id, this);
    }
    public void setViewed(int id){
        service.setViewed(id);
    }
    @Override
    public void onRequestFinished(NoticeResult result) {
        this.result.setValue(result);
    }
}
