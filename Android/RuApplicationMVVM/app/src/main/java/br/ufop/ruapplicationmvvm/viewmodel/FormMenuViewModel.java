package br.ufop.ruapplicationmvvm.viewmodel;

import android.content.SharedPreferences;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import br.ufop.ruapplicationmvvm.model.result.DishResult;
import br.ufop.ruapplicationmvvm.model.result.DishesResult;
import br.ufop.ruapplicationmvvm.service.DishService;
import br.ufop.ruapplicationmvvm.service.HasDishService;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.listener.DishListener;
import br.ufop.ruapplicationmvvm.util.Status;

public class FormMenuViewModel extends ViewModel implements DishListener.OnListFinished, DishListener.OnFinished {
    private MediatorLiveData<DishesResult> listResult;
    private MediatorLiveData<DishResult> result;
    private DishService dishService;
    private HasDishService hasDishService;

    public FormMenuViewModel(SharedPreferences prefs) {
        listResult = new MediatorLiveData<>();
        result = new MediatorLiveData<>();
        dishService = new DishService(TokenManager.getInstance(prefs));
        hasDishService = new HasDishService(TokenManager.getInstance(prefs));
    }

    public MediatorLiveData<DishesResult> getListResult() {
        return listResult;
    }

    public MediatorLiveData<DishResult> getResult() {
        return result;
    }

    public void getDishes(int type) {
        listResult.setValue(new DishesResult(Status.LOADING, null, null, null));

        dishService.index(type, this);
    }

    public void insert(String date, int type, int dishId, int dishType) {
        result.setValue(new DishResult(Status.LOADING, null, null, null));

        hasDishService.insert(this, date, type, dishId, dishType);
    }

    public void update(String date, int type, int dishId, int dishType) {
        result.setValue(new DishResult(Status.LOADING, null, null, null));

        hasDishService.update(this, date, type, dishId, dishType);
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
