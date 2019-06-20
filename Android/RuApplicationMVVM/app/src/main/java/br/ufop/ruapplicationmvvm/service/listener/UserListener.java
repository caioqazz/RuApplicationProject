package br.ufop.ruapplicationmvvm.service.listener;

import br.ufop.ruapplicationmvvm.model.result.UserResult;

public interface UserListener {
    interface OnFinished {
        void onRequestFinished(UserResult result);
    }
}
