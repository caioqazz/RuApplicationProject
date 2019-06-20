package br.ufop.ruapplicationmvvm.model.info;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import br.ufop.ruapplicationmvvm.BR;
import br.ufop.ruapplicationmvvm.util.Validate;

public class RegisterForm extends BaseObservable {
    private String name;
    private String email;
    private String cpf;
    private String password;
    private String confirmationPassword;
    private boolean isValid = false;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.form);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.form);
    }

    @Bindable
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
        notifyPropertyChanged(BR.form);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.form);
    }

    @Bindable
    public String getConfirmationPassword() {
        return confirmationPassword;
    }

    public void setConfirmationPassword(String confirmationPassword) {
        this.confirmationPassword = confirmationPassword;
        notifyPropertyChanged(BR.form);
    }

    public boolean isValid() {
        isValid = Validate.isPassword(password) && Validate.isEmail(email) &&
                Validate.isCPF(cpf) && Validate.isPassword(confirmationPassword) && !Validate.isEmpty(name);
        return isValid;
    }

}
