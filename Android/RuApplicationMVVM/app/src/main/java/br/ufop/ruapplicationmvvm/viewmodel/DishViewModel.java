package br.ufop.ruapplicationmvvm.viewmodel;

import android.content.SharedPreferences;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import br.ufop.ruapplicationmvvm.model.result.DishResult;
import br.ufop.ruapplicationmvvm.model.result.DishesResult;
import br.ufop.ruapplicationmvvm.service.DishService;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.listener.DishListener;
import br.ufop.ruapplicationmvvm.util.Status;

public class DishViewModel extends ViewModel implements DishListener.OnFinished, DishListener.OnListFinished {
    private MediatorLiveData<DishesResult> listResult;
    private MediatorLiveData<DishResult> result;
    private DishService service;

    public DishViewModel(SharedPreferences prefs) {
        listResult = new MediatorLiveData<>();
        result = new MediatorLiveData<>();
        service = new DishService(TokenManager.getInstance(prefs));
    }

    public MediatorLiveData<DishesResult> getListResult() {
        return listResult;
    }

    public MediatorLiveData<DishResult> getResult() {
        return result;
    }

    public void getDishes(int type) {
        listResult.setValue(new DishesResult(Status.LOADING, null, null, null));

        service.index(type, this);
    }

    public void delete(int id) {
        result.setValue(new DishResult(Status.LOADING, null, null, null));

        service.delete(id, this);
    }

    @Override
    public void onRequestFinished(DishResult result) {
        this.result.setValue(result);
    }

    @Override
    public void onRequestFinished(DishesResult result) {
        this.listResult.setValue(result);
    }
}
