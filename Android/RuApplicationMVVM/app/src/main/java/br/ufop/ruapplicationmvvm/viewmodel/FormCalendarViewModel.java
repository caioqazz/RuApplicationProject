package br.ufop.ruapplicationmvvm.viewmodel;

import android.content.SharedPreferences;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import br.ufop.ruapplicationmvvm.model.info.CalendarForm;
import br.ufop.ruapplicationmvvm.model.result.MealResult;
import br.ufop.ruapplicationmvvm.service.MealService;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.listener.MealListener;
import br.ufop.ruapplicationmvvm.util.Status;


public class FormCalendarViewModel extends ViewModel implements MealListener.OnFinished {
    private MediatorLiveData<CalendarForm> form = new MediatorLiveData<>();
    private MediatorLiveData<MealResult> result;

    private MealService service;

    public FormCalendarViewModel(SharedPreferences prefs) {
        result = new MediatorLiveData<>();
        service = new MealService(TokenManager.getInstance(prefs));

    }


    public MutableLiveData<MealResult> getResult() {
        return result;
    }


    public void update(String date, int type, int status, String reason) {
            result.setValue(new MealResult(Status.LOADING, null, null, null));
             service.update(date, type, status, reason, this);
    }

    @Override
    public void onRequestFinished(MealResult result) {
        this.result.setValue(result);
    }
}
