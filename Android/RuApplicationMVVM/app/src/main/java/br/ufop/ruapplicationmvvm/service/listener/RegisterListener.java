package br.ufop.ruapplicationmvvm.service.listener;

import br.ufop.ruapplicationmvvm.model.result.RegisterResult;

public interface RegisterListener {
    interface OnFinished {
        void onRequestFinished(RegisterResult result);
    }
}
