package br.ufop.ruapplicationpassivemvc.activity.implementation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.activity.listener.LoginControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.implementation.LoginController;
import br.ufop.ruapplicationpassivemvc.model.entity.User;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import br.ufop.ruapplicationpassivemvc.view.LoginView;


public class LoginActivity extends AppCompatActivity implements LoginControllerListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginView loginView = findViewById(R.id.login_container);

        LoginController controller = new LoginController(loginView, this);
        loginView.setListeners(controller, OnClickRegister());
    }


    @Override
    public void onLoginSuccess(User user) {
        if (user.getType() == Constants.MANAGER) {
            Intent intent = new Intent(this, MainManagerActivity.class);
            intent.putExtra("user", user);
            this.startActivity(intent);
        } else {
            Intent intent = new Intent(this, MainClientActivity.class);
            intent.putExtra("user", user);
            this.startActivity(intent);
        }
        finish();
    }

    @Override
    public void onLoginError(String error) {
        Toast.makeText(this, "Erro ao logar " + error, Toast.LENGTH_SHORT).show();
    }



    public View.OnClickListener OnClickRegister() {
        return new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        };
    }


}

