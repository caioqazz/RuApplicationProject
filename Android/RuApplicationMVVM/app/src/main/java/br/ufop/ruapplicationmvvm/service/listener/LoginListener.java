package br.ufop.ruapplicationmvvm.service.listener;

import br.ufop.ruapplicationmvvm.model.result.LoginResult;

public interface LoginListener {
    interface OnFinished {
        void onRequestFinished(LoginResult result);
    }
}
