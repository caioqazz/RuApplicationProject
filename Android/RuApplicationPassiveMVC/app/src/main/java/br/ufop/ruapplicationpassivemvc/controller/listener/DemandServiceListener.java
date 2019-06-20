package br.ufop.ruapplicationpassivemvc.controller.listener;

import br.ufop.ruapplicationpassivemvc.model.entity.Demand;

import retrofit2.Response;

public interface DemandServiceListener {

    void onActionSuccessDemand(Response<Demand> response, int option);

    void onActionErrorDemand(Response<Demand> response, int option);

    void onActionFailureDemand(Throwable t, int option);

}
