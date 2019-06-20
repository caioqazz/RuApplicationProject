package br.ufop.ruapplicationmvvm.viewmodel;

import android.content.SharedPreferences;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import br.ufop.ruapplicationmvvm.model.result.ReportDemandtResult;
import br.ufop.ruapplicationmvvm.service.ReportService;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.listener.ReportListener;
import br.ufop.ruapplicationmvvm.util.Status;

public class ReportDemandViewModel extends ViewModel implements ReportListener.OnDemandFinished {
    private MediatorLiveData<ReportDemandtResult> result;
    private ReportService service;

    public ReportDemandViewModel(SharedPreferences prefs) {
        result = new MediatorLiveData<>();
        service = new ReportService(TokenManager.getInstance(prefs));

    }


    public MutableLiveData<ReportDemandtResult> getResult() {
        return result;
    }

    public void getReport(int month, int year) {
        result.setValue(new ReportDemandtResult(Status.LOADING, null, null, null));
        service.reportDemand(this, month, year);
    }

    @Override
    public void onRequestFinished(ReportDemandtResult result) {
        this.result.setValue(result);
    }
}
