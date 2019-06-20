package br.ufop.ruapplicationmvvm.model.info;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import br.ufop.ruapplicationmvvm.BR;
import br.ufop.ruapplicationmvvm.util.Validate;

public class DishForm
        extends BaseObservable {
    private String name;
    private String description;
    private boolean isValid = false;

    public DishForm(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public DishForm() {
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
     //   notifyPropertyChanged(BR);
    }

    @Bindable
    public String getDescription() {
        return description;

    }

    public void setDescription(String description) {
        this.description = description;
       // notifyPropertyChanged(BR.);
    }


    public boolean isValid() {
        isValid = !Validate.isEmpty(name) && Validate.isEmpty(description);
        return isValid;
    }
}
