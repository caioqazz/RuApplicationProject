package br.ufop.ruapplicationmvvm.viewmodel;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import br.ufop.ruapplicationmvvm.model.result.DemandResult;
import br.ufop.ruapplicationmvvm.service.DemandService;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.listener.DemandListener;

public class DemandViewModel extends ViewModel implements DemandListener.OnFinished {
    public final ObservableField<Boolean> isLoading = new ObservableField<>();
    private MediatorLiveData<DemandResult> result;
    private DemandService service;

    public DemandViewModel(SharedPreferences prefs) {
        result = new MediatorLiveData<>();
        isLoading.set(false);
        service = new DemandService(TokenManager.getInstance(prefs));
    }

    public MediatorLiveData<DemandResult> getResult() {
        return result;
    }


    public void getDemand(String date, int type) {
        isLoading.set(true);
        service.getDemand(type, date, this);
    }

    @Override
    public void onRequestFinished(DemandResult result) {
        isLoading.set(false);
        Log.d("demand", "off"+isLoading.get());
        this.result.setValue(result);
    }
}
