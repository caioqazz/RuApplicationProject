package br.ufop.ruapplicationmvvm.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import br.ufop.ruapplicationmvvm.R;
import br.ufop.ruapplicationmvvm.ViewModelFactory;
import br.ufop.ruapplicationmvvm.databinding.ActivityMenuFormEditBinding;
import br.ufop.ruapplicationmvvm.model.entity.Day;
import br.ufop.ruapplicationmvvm.model.entity.Dish;
import br.ufop.ruapplicationmvvm.util.DialogManager;
import br.ufop.ruapplicationmvvm.util.Status;
import br.ufop.ruapplicationmvvm.viewmodel.FormMenuViewModel;
import es.dmoral.toasty.Toasty;

public class FormMenuActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    private Dish dish;
    private Day day;
    private int dish_type;
    private int type;
    private AlertDialog mDialog;
    private ArrayList<Dish> dishes;
    private ActivityMenuFormEditBinding binding;
    private FormMenuViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu_form_edit);
        mViewModel = ViewModelProviders
                .of(this, new ViewModelFactory(getSharedPreferences("prefs", MODE_PRIVATE)))
                .get(FormMenuViewModel.class);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Selecionar prato");
        initiate();
        mDialog = DialogManager.loadingDialog(this);
        mViewModel.getResult().observe(this, result -> {
            onLoading(result.getStatus() == Status.LOADING);

            if (result.getStatus() == Status.SUCCESS)
                onFinished(result.getData());
            else if (result.getStatus() == Status.ERROR)
                onError(result.getError());
            else if (result.getStatus() == Status.FAILURE)
                onFailure(result.getFailure());
        });

        mViewModel.getListResult().observe(this, result -> {
            if (result.getStatus() == Status.SUCCESS)
                onFinished(result.getData());
            else if (result.getStatus() == Status.ERROR)
                onError(result.getError());
            else if (result.getStatus() == Status.FAILURE)
                onFailure(result.getFailure());
        });
        mViewModel.getDishes(dish_type);
        binding.menuFormSpinner.setOnItemSelectedListener(this);
    }

    private void onLoading(boolean b) {
        if (b)
            mDialog.show();
        else
            mDialog.hide();
    }


    private int positionDish() {
        for (int i = 0; i < dishes.size(); i++) {
            if (dish.getId() == dishes.get(i).getId()) {
                return i + 1;
            }
        }
        return 0;
    }

    private String[] getNames(ArrayList<Dish> dishes) {
        String[] data = new String[dishes.size() + 1];
        data[0] = "Selecione";
        for (int i = 1; i < dishes.size() + 1; i++) {
            data[i] = dishes.get(i - 1).getName();
        }
        return data;
    }

    private void setSpinner(String[] data) {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.menuFormSpinner.setAdapter(spinnerArrayAdapter);// The drop down view
        if (dish != null) {
            binding.menuFormSpinner.setSelection(positionDish());
        }
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
                Dish select = dishes.get(binding.menuFormSpinner.getSelectedItemPosition() - 1);
                if (dish == null) {
                    mViewModel.insert(day.getDate(), type, select.getId(), select.getType());
                } else {
                    mViewModel.update(day.getDate(), type, select.getId(), select.getType());
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public void onFailure(String message) {
        Toasty.warning(this, message, Toasty.LENGTH_LONG).show();
    }

    public void onError(String message) {
        Toasty.error(this, message, Toasty.LENGTH_LONG).show();
    }


    public void onFinished(List<Dish> dishes) {
        this.dishes = (ArrayList<Dish>) dishes;
        setSpinner(getNames(this.dishes));
    }

    public void onFinished(Dish dish) {
        Toasty.success(this, "Success", Toasty.LENGTH_LONG).show();
        setResult(RESULT_OK);
        finish();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i > 0) {
            Dish dish = dishes.get(i - 1);

            binding.setDish(dish);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
