package br.ufop.ruapplicationpassivemvc.activity.implementation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.activity.listener.RegisterControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.implementation.RegisterController;
import br.ufop.ruapplicationpassivemvc.view.RegisterView;

public class RegisterActivity extends AppCompatActivity implements RegisterControllerListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RegisterView registerView = findViewById(R.id.register_container);

        RegisterController controller = new RegisterController(registerView, this);
        registerView.setListeners(controller);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onRegisterSuccess() {
        this.startActivity(new Intent(this, MainManagerActivity.class));
        finish();
    }

    @Override
    public void onRegisterError(String error) {
        Toast.makeText(this, "Erro no registro " + error, Toast.LENGTH_SHORT).show();
    }




}
