package br.ufop.ruapplicationpassivemvc.controller.listener;

import br.ufop.ruapplicationpassivemvc.model.entity.Day;

import java.util.List;

import retrofit2.Response;

public interface DayServiceListener {
    void onActionFailure(Throwable t, int option);

    void onIndexSuccess(Response<List<Day>> response);

    void onIndexError(Response<List<Day>> response);

}
