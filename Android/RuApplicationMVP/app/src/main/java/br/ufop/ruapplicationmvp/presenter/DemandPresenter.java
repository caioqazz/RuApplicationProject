package br.ufop.ruapplicationmvp.presenter;

import android.content.SharedPreferences;

import br.ufop.ruapplicationmvp.contract.DemandContract;
import br.ufop.ruapplicationmvp.model.entity.Demand;
import br.ufop.ruapplicationmvp.service.DemandService;
import br.ufop.ruapplicationmvp.service.api.TokenManager;
import retrofit2.Response;

public class DemandPresenter implements DemandContract.Presenter, DemandContract.Model.OnFinishedListener {

    private DemandContract.View mView;
    private DemandService mService;
    private String date;
    private int type;

    public DemandPresenter(DemandContract.View mView, SharedPreferences prefs, String date, int type) {
        this.mView = mView;
        this.date = date;
        this.type = type;
        mService = new DemandService(TokenManager.getInstance(prefs));

    }

    @Override
    public void onFinished(Demand demand) {
        if (mView != null) {
            mView.setValues(demand.getSumPrincipal(), demand.getSumVegetariano(), demand.getTotal());
            mView.hideProgress();
        }
    }

    @Override
    public void onError(Response<Demand> response) {
        if (mView != null) {
            mView.onError(response.message());
            mView.hideProgress();
            errorNotFound(response.code());
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if (mView != null) {
            mView.onFailure(t);
            mView.hideProgress();
        }
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public void getDemand() {
        if (mView != null) {
            mView.showProgress();
            mService.getDemand(this, type, date);
        }
    }

    private void errorNotFound(int code) {
        if (code == 404)
            mView.setValues(0, 0, 0);
    }
}
