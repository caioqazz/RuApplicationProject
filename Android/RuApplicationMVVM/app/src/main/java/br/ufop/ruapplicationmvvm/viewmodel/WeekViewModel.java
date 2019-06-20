package br.ufop.ruapplicationmvvm.viewmodel;

import android.content.SharedPreferences;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import br.ufop.ruapplicationmvvm.model.result.WeekResult;
import br.ufop.ruapplicationmvvm.service.MealService;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.listener.WeekListener;
import br.ufop.ruapplicationmvvm.util.Status;

public class WeekViewModel extends ViewModel implements WeekListener.OnFinished {
    private MediatorLiveData<WeekResult> result;
    private MealService service;

    public WeekViewModel(SharedPreferences prefs) {
        result = new MediatorLiveData<>();
        service = new MealService(TokenManager.getInstance(prefs));
    }

    public MediatorLiveData<WeekResult> getResult() {
        return result;
    }


    public void getWeeks() {
        result.setValue(new WeekResult(Status.LOADING, null, null, null));
        service.indexWeeks(this);
    }

    @Override
    public void onRequestFinished(WeekResult result) {
        this.result.setValue(result);
    }
}
