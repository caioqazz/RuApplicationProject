package br.ufop.ruapplicationpassivemvc.controller.implementation;

import android.content.Context;
import android.widget.Toast;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.activity.listener.ProfileControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.listener.UserServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.User;
import br.ufop.ruapplicationpassivemvc.service.UserService;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Validate;
import br.ufop.ruapplicationpassivemvc.view.ProfileView;

import okhttp3.MultipartBody;
import retrofit2.Response;

public class ProfileController implements UserServiceListener {
    ProfileView view;
    ProfileControllerListener listener;
    TokenManager tokenManager;


    public ProfileController(ProfileView view, ProfileControllerListener listener) {
        this.view = view;
        this.listener = listener;
        tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE));

    }

    public void update(String name, MultipartBody.Part photo) {
        UserService userService = new UserService(this, tokenManager);

        if (!view.getName().isEmpty() && (Validate.wasFieldUpdate(name, view.getName()) || photo != null)) {
            view.showLoading();
            userService.updateUser(view.getName(), photo);
        } else {
            Toast.makeText(view.getContext(), R.string.error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActionSuccess(Response<User> response, int option) {
        listener.onActionExecuted("Perfil Atualizado com Sucesso!");
        tokenManager.saveUser(response.body());
        view.showForm();
    }

    @Override
    public void onActionError(Response<User> response, int option) {
        view.showForm();
        listener.onActionExecuted("Erro ao Atualizar Perfil!");
    }

    @Override
    public void onActionFailure(Throwable t, int option) {
        view.showForm();
        listener.onActionExecuted("Erro ao Atualizar Perfil!");

    }
}
