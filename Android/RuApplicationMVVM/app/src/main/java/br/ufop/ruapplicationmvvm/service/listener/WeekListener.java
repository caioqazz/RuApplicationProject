package br.ufop.ruapplicationmvvm.service.listener;

import br.ufop.ruapplicationmvvm.model.result.WeekResult;

public interface WeekListener {
    interface OnFinished {
        void onRequestFinished(WeekResult result);
    }
}
