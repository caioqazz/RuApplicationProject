package br.ufop.ruapplicationmvvm.viewmodel;

import android.content.SharedPreferences;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import br.ufop.ruapplicationmvvm.model.result.DemandResult;
import br.ufop.ruapplicationmvvm.model.result.MenuResult;
import br.ufop.ruapplicationmvvm.service.DemandService;
import br.ufop.ruapplicationmvvm.service.MenuService;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.listener.DemandListener;
import br.ufop.ruapplicationmvvm.service.listener.MenuListener;
import br.ufop.ruapplicationmvvm.util.Status;

public class MenuViewModel extends ViewModel implements MenuListener.onMenuFinished, DemandListener.OnFinished {
    private MediatorLiveData<DemandResult> demandResult = new MediatorLiveData<>();
    private MediatorLiveData<MenuResult> menuResult = new MediatorLiveData<>();
    private DemandService demandService;
    private MenuService menuService;

    public MenuViewModel(SharedPreferences prefs) {
        demandService = new DemandService(TokenManager.getInstance(prefs));
        menuService = new MenuService(TokenManager.getInstance(prefs));
    }

    public MediatorLiveData<DemandResult> getDemandResult() {
        return demandResult;
    }

    public MediatorLiveData<MenuResult> getMenuResult() {
        return menuResult;
    }

    public void insertParticipation(int type, String date, int option) {
        demandResult.setValue(new DemandResult(Status.LOADING, null, null, null));
        demandService.insertParticipation(this, type, date, option);
    }

    public void deleteParticipation(int type, String date) {
        demandResult.setValue(new DemandResult(Status.LOADING, null, null, null));
        demandService.deleteParticipation(this, type, date);
    }

    public void getParticipation(int type, String date) {
        demandResult.setValue(new DemandResult(Status.LOADING, null, null, null));
        demandService.getParticipation(this, type, date);
    }

    public void getMenu(int type, String date) {
        menuResult.setValue(new MenuResult(Status.LOADING, null, null, null));
        menuService.getMenu(this, type, date);
    }

    @Override
    public void onRequestFinished(MenuResult result) {
        this.menuResult.setValue(result);
    }

    @Override
    public void onRequestFinished(DemandResult result) {
        this.demandResult.setValue(result);
    }
}
