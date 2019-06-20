package br.ufop.ruapplicationmvvm.viewmodel;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import br.ufop.ruapplicationmvvm.model.info.LoginForm;
import br.ufop.ruapplicationmvvm.model.result.LoginResult;
import br.ufop.ruapplicationmvvm.service.UserService;
import br.ufop.ruapplicationmvvm.service.listener.LoginListener;
import br.ufop.ruapplicationmvvm.util.Status;

public class LoginViewModel extends ViewModel implements LoginListener.OnFinished {
    private MediatorLiveData<LoginResult> result;
    private UserService service;

    public LoginViewModel() {
        result = new MediatorLiveData<>();
        service = new UserService();
    }


    public MediatorLiveData<LoginResult> getResult() {
        return result;
    }


    public void login(LoginForm form) {
        result.setValue(new LoginResult(Status.LOADING, null, null));
        if (form.isValid()) {
            service.authentication(form.getEmail(), form.getPassword(), this);
        }
    }


    @Override
    public void onRequestFinished(LoginResult result) {
        this.result.setValue(result);
    }
}
