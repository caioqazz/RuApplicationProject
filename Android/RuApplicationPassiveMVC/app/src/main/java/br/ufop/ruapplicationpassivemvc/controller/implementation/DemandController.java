package br.ufop.ruapplicationpassivemvc.controller.implementation;

import android.content.Context;
import android.view.View;

import br.ufop.ruapplicationpassivemvc.activity.listener.DemandControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.listener.DemandServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.Day;
import br.ufop.ruapplicationpassivemvc.model.entity.Demand;
import br.ufop.ruapplicationpassivemvc.service.DemandService;
import br.ufop.ruapplicationpassivemvc.service.api.ApiService;
import br.ufop.ruapplicationpassivemvc.service.api.RetrofitBuilder;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.view.DemandView;

import retrofit2.Response;

public class DemandController implements DemandServiceListener {
    private ApiService service;
    private TokenManager tokenManager;
    private DemandView view;
    private DemandControllerListener listener;
    private Day day;

    public DemandController(DemandView view, DemandControllerListener listener, Day day) {
        this.day = day;
        this.view = view;
        this.listener = listener;
        tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE));

        if (tokenManager.getToken() == null) {
            //unauthenticate
        }
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    public void show(int type) {
        view.setVisibilityDialog(View.VISIBLE);
        DemandService demandService = new DemandService(this, tokenManager);
        demandService.show(type, day.getDate());
    }

    @Override
    public void onActionSuccessDemand(Response<Demand> response, int option) {
        Demand demand = response.body();
        view.setPrincipal(demand.getSumPrincipal());
        view.setVegetariano(demand.getSumVegetariano());
        view.setTotal(demand.getTotal());
        view.setVisibilityDialog(View.INVISIBLE);

    }

    @Override
    public void onActionErrorDemand(Response<Demand> response, int option) {
        if(response.code() == 404){
            view.setPrincipal(0);
            view.setVegetariano(0);
            view.setTotal(0);
            view.setVisibilityDialog(View.INVISIBLE);
        }
    }

    @Override
    public void onActionFailureDemand(Throwable t, int option) {
    }
}
