package br.ufop.ruapplicationpassivemvc.activity.implementation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.activity.listener.DetailDishControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.implementation.DetailDishController;
import br.ufop.ruapplicationpassivemvc.model.entity.Dish;
import br.ufop.ruapplicationpassivemvc.model.entity.User;
import br.ufop.ruapplicationpassivemvc.service.api.RetrofitBuilder;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import br.ufop.ruapplicationpassivemvc.util.Dialog;
import br.ufop.ruapplicationpassivemvc.util.Networking;
import br.ufop.ruapplicationpassivemvc.view.DetailDishView;
import com.squareup.picasso.Picasso;

public class DetailDishActivity extends AppCompatActivity implements DetailDishControllerListener {
    Dish dish;
    DetailDishView view;
    DetailDishController controller;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dish);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        view = findViewById(R.id.detail_dish_container);

        initiate();

        controller = new DetailDishController(view, dish, this);
        TokenManager tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        user = tokenManager.getUser();
        if (user.getType() == Constants.CLIENT) {
            view.clientView();
            controller.showClassification();
        }

    }

    private void initiate() {
        Bundle mBundle = getIntent().getExtras();

        if (mBundle != null && mBundle.get("dish") != null) {
            dish = (Dish) mBundle.get("dish");

            view.setName(dish.getName());
            view.setDescription(dish.getDescription());

            if (dish.getAverage() != null) {
                view.setRating(dish.getAverage());
            } else {
                view.setRating(5);
            }


            if (dish.getPhoto() != null && !dish.getPhoto().isEmpty()) {
                Picasso.get().load(RetrofitBuilder.BASE_PARSE_URL + dish.getPhoto()).error(R.drawable.image).into(view.getPhotoField());
            }
            mBundle.clear();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (user.getType() == Constants.MANAGER) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_detail, menu);
        } else {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.send_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_edit:
                Intent intent = new Intent(this, FormDishActivity.class);
                intent.putExtra("dish", dish);
                startActivity(intent);
                finish();
                return true;
            case R.id.menu_delete:
                Dialog.confimationDelete(view.getContext(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Networking.isNetworkConnected(view))
                            controller.delete();
                    }
                }, dish.getName());

                return true;
            case R.id.send:
                controller.updateClassification();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onActionExecuted(String s, int result) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

        if (result == Constants.SUCCESS)
            finish();
    }
}
