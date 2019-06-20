package br.ufop.ruapplicationmvvm.viewmodel;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import br.ufop.ruapplicationmvvm.model.info.RegisterForm;
import br.ufop.ruapplicationmvvm.model.result.RegisterResult;
import br.ufop.ruapplicationmvvm.service.UserService;
import br.ufop.ruapplicationmvvm.service.listener.RegisterListener;
import br.ufop.ruapplicationmvvm.util.Status;


public class RegisterViewModel extends ViewModel implements RegisterListener.OnFinished {
    private MediatorLiveData<RegisterResult> result;
    private UserService service;

    public RegisterViewModel() {
        result = new MediatorLiveData<>();
        service = new UserService();
    }

    public MediatorLiveData<RegisterResult> getResult() {
        return result;
    }

    public void register(RegisterForm form) {
        result.setValue(new RegisterResult(Status.LOADING, null,
                null));
        if (form.isValid())
            service.register(form.getName(), form.getEmail(), form.getCpf(), form.getCpf(), this);
        else
            result.setValue(new RegisterResult(Status.FAILURE, null,
                    "Preencha todos os campos corretamente!"));


    }

    @Override
    public void onRequestFinished(RegisterResult result) {
        this.result.setValue(result);
    }
}
