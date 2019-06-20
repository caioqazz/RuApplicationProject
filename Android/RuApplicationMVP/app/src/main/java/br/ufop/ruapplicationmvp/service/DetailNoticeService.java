package br.ufop.ruapplicationmvp.service;

import br.ufop.ruapplicationmvp.contract.DetailNoticeContract;
import br.ufop.ruapplicationmvp.model.entity.Notice;
import br.ufop.ruapplicationmvp.service.api.ApiService;
import br.ufop.ruapplicationmvp.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvp.service.api.TokenManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailNoticeService implements DetailNoticeContract.Model {

    private ApiService service;

    public DetailNoticeService(TokenManager tokenManager) {
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    @Override
    public void delete(OnFinishedListener onFinishedListener, int id) {
        Call<Notice> call = service.deleteAviso(id);
        call.enqueue(new Callback<Notice>() {
            @Override
            public void onResponse(Call<Notice> call, Response<Notice> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished();
                } else {
                    onFinishedListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<Notice> call, Throwable t) {
                onFinishedListener.onFailure(t.getMessage());
            }
        });
    }

    @Override
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
}
