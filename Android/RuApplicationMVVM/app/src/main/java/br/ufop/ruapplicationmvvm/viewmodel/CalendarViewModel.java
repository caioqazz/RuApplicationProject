package br.ufop.ruapplicationmvvm.viewmodel;

import android.content.SharedPreferences;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import br.ufop.ruapplicationmvvm.model.result.MealsResult;
import br.ufop.ruapplicationmvvm.service.MealService;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.listener.MealListener;
import br.ufop.ruapplicationmvvm.util.Status;

public class CalendarViewModel extends ViewModel implements MealListener.OnListFinished {
    private MediatorLiveData<MealsResult> result;
    private MealService service;

    public CalendarViewModel(SharedPreferences prefs) {
        result = new MediatorLiveData<>();
        service = new MealService(TokenManager.getInstance(prefs));
    }

    public MediatorLiveData<MealsResult> getResult() {
        return result;
    }


    public void getCalendar() {
        result.setValue(new MealsResult(Status.LOADING, null, null, null));
        service.indexDaysClosed(this);
    }

    @Override
    public void onRequestFinished(MealsResult result) {
        this.result.setValue(result);
    }
}
