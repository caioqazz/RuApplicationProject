package br.ufop.ruapplicationpassivemvc.controller.listener;

import br.ufop.ruapplicationpassivemvc.model.entity.Classification;

import retrofit2.Response;


public interface ClassificationServiceListener {
    void onActionSuccessClassification(Response<Classification> response, int option);

    void onActionErrorClassification(Response<Classification> response, int option);

    void onActionFailureClassification(Throwable t, int option);

}
