package br.ufop.ruapplicationpassivemvc.activity.implementation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.activity.listener.FormMenuControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.implementation.FormMenuController;
import br.ufop.ruapplicationpassivemvc.model.entity.Day;
import br.ufop.ruapplicationpassivemvc.model.entity.Dish;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import br.ufop.ruapplicationpassivemvc.view.FormMenuView;

public class MenuFormActivity extends AppCompatActivity implements FormMenuControllerListener {
    private Dish dish;
    private Day day;
    private int dish_type;
    private int type;
    private FormMenuController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_form_edit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Selecionar prato");
        FormMenuView view = findViewById(R.id.menu_form_container);
        initiate();

        controller = new FormMenuController(view, this, dish);
        view.setListener(controller);
        controller.getDishes(dish_type);
    }

    private void initiate() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            if (mBundle.get("dish") != null) {
                dish = (Dish) mBundle.get("dish");
                setTitle("Editar prato");
            }
            dish_type = mBundle.getInt("dish_type");
            day = (Day) mBundle.get("day");
            type = mBundle.getInt("type");
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save:
                if (dish == null) {
                    controller.insert(day.getDate(), type);
                } else {
                    controller.update(day.getDate(), type);
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onActionExecuted(String message, int result) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        if (result == Constants.SUCCESS)
            finish();
    }
}
