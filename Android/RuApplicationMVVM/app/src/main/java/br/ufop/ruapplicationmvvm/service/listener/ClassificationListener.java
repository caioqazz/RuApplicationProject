package br.ufop.ruapplicationmvvm.service.listener;

import br.ufop.ruapplicationmvvm.model.result.ClassificationResult;

public interface ClassificationListener {
    interface OnFinished {
        void onRequestFinished(ClassificationResult result);
    }
}
