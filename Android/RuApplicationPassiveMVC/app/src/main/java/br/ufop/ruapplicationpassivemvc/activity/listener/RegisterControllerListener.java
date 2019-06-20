package br.ufop.ruapplicationpassivemvc.activity.listener;

public interface RegisterControllerListener {

    void onRegisterSuccess();

    void onRegisterError(String error);
}
