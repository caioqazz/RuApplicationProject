package br.ufop.ruapplicationmvvm.service.listener;

import br.ufop.ruapplicationmvvm.model.result.DemandResult;

public interface DemandListener {
    interface OnFinished {
        void onRequestFinished(DemandResult result);
    }

    interface OnParticipationFinished {
        void onRequestFinished(DemandResult result, int option);
    }
}
