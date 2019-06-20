package br.ufop.ruapplicationmvvm.viewmodel;

import android.content.SharedPreferences;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import br.ufop.ruapplicationmvvm.model.result.NoticeResult;
import br.ufop.ruapplicationmvvm.model.result.NoticesResult;
import br.ufop.ruapplicationmvvm.service.NoticeService;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.listener.NoticeListener;
import br.ufop.ruapplicationmvvm.util.Status;

public class NoticeViewModel extends ViewModel implements NoticeListener.OnFinished, NoticeListener.OnListFinished {
    private MediatorLiveData<NoticesResult> listResult;
    private MediatorLiveData<NoticeResult> result;

    private NoticeService service;

    public NoticeViewModel(SharedPreferences prefs) {
        listResult = new MediatorLiveData<>();
        result = new MediatorLiveData<>();
        service = new NoticeService(TokenManager.getInstance(prefs));
    }

    public MediatorLiveData<NoticesResult> getListResult() {
        return listResult;
    }

    public MediatorLiveData<NoticeResult> getResult() {
        return result;
    }


    public void getNotices() {
        listResult.setValue(new NoticesResult(Status.LOADING, null, null, null));

        service.index(this);
    }

    public void delete(int id) {
        result.setValue(new NoticeResult(Status.LOADING, null, null, null));

        service.delete(id, this);
    }

    @Override
    public void onRequestFinished(NoticeResult result) {
        this.result.setValue(result);
    }

    @Override
    public void onRequestFinished(NoticesResult result) {
        this.listResult.setValue(result);
    }
}
