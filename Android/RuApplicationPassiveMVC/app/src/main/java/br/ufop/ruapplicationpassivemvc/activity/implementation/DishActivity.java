package br.ufop.ruapplicationpassivemvc.activity.implementation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.activity.listener.DishControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.implementation.DishController;
import br.ufop.ruapplicationpassivemvc.model.entity.Dish;
import br.ufop.ruapplicationpassivemvc.view.DishView;

public class DishActivity extends AppCompatActivity implements DishControllerListener {
    private DishController controller;
    private int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);
        setTitle("Pratos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initiate();


        DishView view = findViewById(R.id.dish_container);
        controller = new DishController(view, this, type);
        view.setListeners(controller, controller);
    }

    private void initiate() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            type = mBundle.getInt("category_dish_type");
            mBundle.clear();
        }
    }


    @Override
    public void detailDish(Dish dish) {
        Intent intent = new Intent(this, DetailDishActivity.class);
        intent.putExtra("dish", dish);
        startActivity(intent);
    }

    @Override
    public void editDish(Dish dish) {
        Intent intent = new Intent(this, FormDishActivity.class);
        intent.putExtra("dish", dish);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        controller.onRefresh();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
