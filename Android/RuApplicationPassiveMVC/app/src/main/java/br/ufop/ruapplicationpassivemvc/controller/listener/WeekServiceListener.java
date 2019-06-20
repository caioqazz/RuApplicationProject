package br.ufop.ruapplicationpassivemvc.controller.listener;

import br.ufop.ruapplicationpassivemvc.model.entity.Week;

import java.util.List;

import retrofit2.Response;

public interface WeekServiceListener {

    void onIndexSuccess(Response<List<Week>> response);

    void onIndexError(Response<List<Week>> response);

    void onActionFailure(Throwable t, int option);
}
