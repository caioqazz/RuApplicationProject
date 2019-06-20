package br.ufop.ruapplicationmvp.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import br.ufop.ruapplicationmvp.R;
import br.ufop.ruapplicationmvp.contract.PosLoginContract;
import br.ufop.ruapplicationmvp.model.entity.User;
import br.ufop.ruapplicationmvp.presenter.PosLoginPresenter;
import br.ufop.ruapplicationmvp.service.api.UserManager;
import br.ufop.ruapplicationmvp.util.Constants;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PosLoginActivity extends AppCompatActivity implements PosLoginContract.View {

    private PosLoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pos_login);
        getSupportActionBar().hide();
        mPresenter = new PosLoginPresenter(this, getSharedPreferences("prefs", MODE_PRIVATE));



    }


    @Override
    public void onSuccess(User user) {
        Intent intent;
        if (user.getType() == Constants.MANAGER) {
            intent = new Intent(this, MainManagerActivity.class);
        } else {
            intent = new Intent(this, MainClientActivity.class);
        }

        this.startActivity(intent);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
