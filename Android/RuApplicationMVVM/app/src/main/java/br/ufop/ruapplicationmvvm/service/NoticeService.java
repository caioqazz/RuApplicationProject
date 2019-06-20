package br.ufop.ruapplicationmvvm.service;

import java.util.List;

import br.ufop.ruapplicationmvvm.model.entity.Notice;
import br.ufop.ruapplicationmvvm.model.result.NoticeResult;
import br.ufop.ruapplicationmvvm.model.result.NoticesResult;
import br.ufop.ruapplicationmvvm.service.api.ApiService;
import br.ufop.ruapplicationmvvm.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.listener.NoticeListener;
import br.ufop.ruapplicationmvvm.util.Status;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeService {

    private ApiService service;

    public NoticeService(TokenManager tokenManager) {
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    public void index(NoticeListener.OnListFinished listener) {
        Call<List<Notice>> callIndex = service.notices();
        callIndex.enqueue(new Callback<List<Notice>>() {
            @Override
            public void onResponse(Call<List<Notice>> call, Response<List<Notice>> response) {
                if (response.isSuccessful()) {
                    listener.onRequestFinished(new NoticesResult(Status.SUCCESS, response.body(), null, null));
                } else {
                    listener.onRequestFinished(new NoticesResult(Status.ERROR, null, response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<List<Notice>> call, Throwable t) {
                listener.onRequestFinished(new NoticesResult(Status.FAILURE, null, null, t.getMessage()));

            }
        });

    }

    public void delete(int id, NoticeListener.OnFinished listener) {
        Call<Notice> call = service.deleteAviso(id);
        call.enqueue(new Callback<Notice>() {
            @Override
            public void onResponse(Call<Notice> call, Response<Notice> response) {
                if (response.isSuccessful()) {
                    listener.onRequestFinished(new NoticeResult(Status.SUCCESS, response.body(), null, null));
                } else {
                    listener.onRequestFinished(new NoticeResult(Status.ERROR, null, response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<Notice> call, Throwable t) {
                listener.onRequestFinished(new NoticeResult(Status.FAILURE, null, null, t.getMessage()));

            }
        });
    }

    public void setViewed(int id) {

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

    public void insert(NoticeListener.OnFinished listener, String subject, String content, int type) {
        Call<Notice> call = service.newAviso(subject, content, type);
        call.enqueue(new Callback<Notice>() {
            @Override
            public void onResponse(Call<Notice> call, Response<Notice> response) {
                if (response.isSuccessful()) {
                    listener.onRequestFinished(new NoticeResult(Status.SUCCESS, response.body(), null, null));
                } else {
                    listener.onRequestFinished(new NoticeResult(Status.ERROR, null, response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<Notice> call, Throwable t) {
                listener.onRequestFinished(new NoticeResult(Status.FAILURE, null, null, t.getMessage()));
            }
        });
    }

}
