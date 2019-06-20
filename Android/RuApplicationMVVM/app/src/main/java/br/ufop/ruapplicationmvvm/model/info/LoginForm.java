package br.ufop.ruapplicationmvvm.model.info;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import br.ufop.ruapplicationmvvm.BR;
import br.ufop.ruapplicationmvvm.util.Validate;

public class LoginForm extends BaseObservable {
    private String email;
    private String password;
    private boolean isValid = false;


    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.loginform);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.loginform);
    }

    public boolean isValid() {
        isValid = Validate.isPassword(password) && Validate.isEmail(email);
        return isValid;
    }
}
