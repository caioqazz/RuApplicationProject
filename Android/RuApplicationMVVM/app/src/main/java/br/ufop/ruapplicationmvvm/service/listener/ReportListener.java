package br.ufop.ruapplicationmvvm.service.listener;

import br.ufop.ruapplicationmvvm.model.entity.ReportDish;
import br.ufop.ruapplicationmvvm.model.response.ReportDemandResponse;
import br.ufop.ruapplicationmvvm.model.result.ReportDemandtResult;
import br.ufop.ruapplicationmvvm.model.result.ReportDishResult;

public interface ReportListener {
    interface OnDemandFinished {
        void onRequestFinished(ReportDemandtResult result);
    }

    interface OnDishFinished {
        void onRequestFinished(ReportDishResult result);
    }
}
