package br.ufop.ruapplicationmvvm.viewmodel;

import android.content.SharedPreferences;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import br.ufop.ruapplicationmvvm.model.result.DishesResult;
import br.ufop.ruapplicationmvvm.model.result.ReportDishResult;
import br.ufop.ruapplicationmvvm.service.DishService;
import br.ufop.ruapplicationmvvm.service.ReportService;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.listener.DishListener;
import br.ufop.ruapplicationmvvm.service.listener.ReportListener;
import br.ufop.ruapplicationmvvm.util.Status;

public class ReportDishesViewModel extends ViewModel implements ReportListener.OnDishFinished, DishListener.OnListFinished {
    private MediatorLiveData<ReportDishResult> result;
    private MediatorLiveData<DishesResult> listResult;
    private ReportService reportService;
    private DishService dishService;

    public ReportDishesViewModel(SharedPreferences prefs) {
        result = new MediatorLiveData<>();
        listResult = new MediatorLiveData<>();
        reportService = new ReportService(TokenManager.getInstance(prefs));
        dishService = new DishService(TokenManager.getInstance(prefs));

    }

    public MediatorLiveData<DishesResult> getListResult() {
        return listResult;
    }

    public MutableLiveData<ReportDishResult> getResult() {
        return result;
    }

    public void getDishes() {
        listResult.setValue(new DishesResult(Status.LOADING, null, null, null));
        dishService.index(this);
    }

    public void getReport(int id) {
        result.setValue(new ReportDishResult(Status.LOADING, null, null, null));
        reportService.reportDish(this, id);
    }


    @Override
    public void onRequestFinished(ReportDishResult result) {
        this.result.setValue(result);
    }

    @Override
    public void onRequestFinished(DishesResult result) {
        this.listResult.setValue(result);
    }
}
