package br.ufop.ruapplicationmvvm.service.listener;

import br.ufop.ruapplicationmvvm.model.result.DayResult;

public interface DayListener {
    interface OnFinished {
        void onRequestFinished(DayResult result);
    }
}
