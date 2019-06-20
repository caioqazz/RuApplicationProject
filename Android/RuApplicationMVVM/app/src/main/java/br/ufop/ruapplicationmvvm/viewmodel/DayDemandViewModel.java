package br.ufop.ruapplicationmvvm.viewmodel;

import android.content.SharedPreferences;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import br.ufop.ruapplicationmvvm.model.result.DayResult;
import br.ufop.ruapplicationmvvm.service.DemandService;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.listener.DayListener;
import br.ufop.ruapplicationmvvm.util.Status;

public class DayDemandViewModel extends ViewModel implements DayListener.OnFinished {
    private MediatorLiveData<DayResult> result;
    private DemandService service;

    public DayDemandViewModel(SharedPreferences prefs) {
        result = new MediatorLiveData<>();
        service = new DemandService(TokenManager.getInstance(prefs));
    }

    public MediatorLiveData<DayResult> getResult() {
        return result;
    }


    public void getDays() {
        result.setValue(new DayResult(Status.LOADING, null, null, null));
        service.indexDays(this);

    }

    @Override
    public void onRequestFinished(DayResult result) {
        this.result.setValue(result);
    }
}