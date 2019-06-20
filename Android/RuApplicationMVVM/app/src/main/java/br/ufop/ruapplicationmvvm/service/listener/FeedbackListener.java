package br.ufop.ruapplicationmvvm.service.listener;

import br.ufop.ruapplicationmvvm.model.result.FeedbackResult;
import br.ufop.ruapplicationmvvm.model.result.FeedbacksResult;

public interface FeedbackListener {
    interface OnFinished {
        void onRequestFinished(FeedbackResult result);
    }

    interface OnListFinished {
        void onRequestFinished(FeedbacksResult result);
    }
}
