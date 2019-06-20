package br.ufop.ruapplicationpassivemvc.service;

import br.ufop.ruapplicationpassivemvc.controller.listener.NoticeServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.Notice;
import br.ufop.ruapplicationpassivemvc.service.api.ApiService;
import br.ufop.ruapplicationpassivemvc.service.api.RetrofitBuilder;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeService {
    NoticeServiceListener listener;

    ApiService service;
    Call<List<Notice>> callIndex;
    Call<Notice> call;

    public NoticeService(NoticeServiceListener listener, TokenManager tokenManager) {
        this.listener = listener;
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    public void index() {

        callIndex = service.notices();

        callIndex.enqueue(new Callback<List<Notice>>() {
            @Override
            public void onResponse(Call<List<Notice>> call, Response<List<Notice>> response) {
                if (response.isSuccessful()) {
                    listener.onIndexSuccess(response);
                } else {
                    listener.onIndexError(response);
                }
            }

            @Override
            public void onFailure(Call<List<Notice>> call, Throwable t) {
                listener.onActionFailure(t, Constants.GET);
            }
        });
    }

    public void delete(int id) {
        call = service.deleteAviso(id);
        call.enqueue(new Callback<Notice>() {
            @Override
            public void onResponse(Call<Notice> call, Response<Notice> response) {
                if (response.isSuccessful()) {
                    listener.onActionSuccess(response, Constants.DELETE);
                } else {
                    listener.onActionError(response, Constants.DELETE);
                }
            }

            @Override
            public void onFailure(Call<Notice> call, Throwable t) {
                listener.onActionFailure(t, Constants.DELETE);
            }
        });

    }

    public void insert(String subject, String content, int type) {

        call = service.newAviso(subject, content, type);
        call.enqueue(new Callback<Notice>() {
            @Override
            public void onResponse(Call<Notice> call, Response<Notice> response) {
                if (response.isSuccessful()) {
                    listener.onActionSuccess(response, Constants.INSERT);
                } else {
                    listener.onActionError(response, Constants.INSERT);
                }
            }

            @Override
            public void onFailure(Call<Notice> call, Throwable t) {
                listener.onActionFailure(t, Constants.INSERT);
            }
        });
    }

    public void update(int id, String subject, int type, String content) {
        call = service.updateAviso(id, subject, type, content);
        call.enqueue(new Callback<Notice>() {
            @Override
            public void onResponse(Call<Notice> call, Response<Notice> response) {
                if (response.isSuccessful()) {
                    listener.onActionSuccess(response, Constants.UPDATE);
                } else {
                    listener.onActionError(response, Constants.UPDATE);
                }
            }

            @Override
            public void onFailure(Call<Notice> call, Throwable t) {
                listener.onActionFailure(t, Constants.UPDATE);
            }
        });
    }

    public void setView(int id) {
        Call<ResponseBody> call = service.setViewAviso(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


}
