package br.ufop.ruapplicationpassivemvc.controller.listener;

import br.ufop.ruapplicationpassivemvc.model.entity.Notice;

import java.util.List;

import retrofit2.Response;

public interface NoticeServiceListener {

    void onActionSuccess(Response<Notice> response, int option);

    void onActionError(Response<Notice> response, int option);

    void onActionFailure(Throwable t, int option);

    void onIndexSuccess(Response<List<Notice>> response);

    void onIndexError(Response<List<Notice>> response);


}
