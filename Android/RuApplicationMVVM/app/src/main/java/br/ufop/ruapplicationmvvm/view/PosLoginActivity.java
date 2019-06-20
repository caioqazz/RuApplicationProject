package br.ufop.ruapplicationmvvm.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import br.ufop.ruapplicationmvvm.R;
import br.ufop.ruapplicationmvvm.ViewModelFactory;
import br.ufop.ruapplicationmvvm.service.api.UserManager;
import br.ufop.ruapplicationmvvm.util.Constants;
import br.ufop.ruapplicationmvvm.viewmodel.PosLoginViewModel;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PosLoginActivity extends AppCompatActivity {
    private PosLoginViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pos_login);
        getSupportActionBar().hide();
        mViewModel = ViewModelProviders
                .of(this, new ViewModelFactory(getSharedPreferences("prefs", MODE_PRIVATE)))
                .get(PosLoginViewModel.class);


        onSuccess();

        mViewModel.getUser();
    }


    public void onSuccess() {
        mViewModel.getResult().observe(this, user -> {
            UserManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).saveUser(user);
            Intent intent = new Intent();
            if (user.getType() == Constants.MANAGER) {
                intent = new Intent(this, MainManagerActivity.class);
            } else {
                intent = new Intent(this, MainClientActivity.class);
            }

            this.startActivity(intent);
            finish();
        });
    }


}
