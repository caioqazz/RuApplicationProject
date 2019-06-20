package br.ufop.ruapplicationmvp.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.ufop.ruapplicationmvp.R;
import br.ufop.ruapplicationmvp.contract.FormMenuContract;
import br.ufop.ruapplicationmvp.model.entity.Day;
import br.ufop.ruapplicationmvp.model.entity.Dish;
import br.ufop.ruapplicationmvp.presenter.FormMenuPresenter;
import br.ufop.ruapplicationmvp.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvp.util.DialogManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class FormMenuActivity extends AppCompatActivity
        implements FormMenuContract.View, AdapterView.OnItemSelectedListener {
    @BindView(R.id.menu_form_spinner)
    Spinner mSpinner;
    @BindView(R.id.menu_form_imageview)
    ImageView mImageview;
    @BindView(R.id.menu_form_name)
    TextView mName;
    @BindView(R.id.menu_form_description)
    TextView mDescription;
    private Dish dish;
    private Day day;
    private int dish_type;
    private int type;
    private FormMenuPresenter mPresenter;
    private AlertDialog mDialog;
    private ArrayList<Dish> dishes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_form_edit);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Selecionar prato");
        initiate();
        mDialog = DialogManager.loadingDialog(this);
        mPresenter = new FormMenuPresenter(this, getSharedPreferences("prefs", MODE_PRIVATE));
        mPresenter.getDishes(dish_type);
        mSpinner.setOnItemSelectedListener(this);
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
        mSpinner.setAdapter(spinnerArrayAdapter);// The drop down view
        if (dish != null) {
            mSpinner.setSelection(positionDish());
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
                Dish select = dishes.get(mSpinner.getSelectedItemPosition() - 1);
                if (dish == null) {
                    mPresenter.insert(day.getDate(), type, select.getId(), select.getType());
                } else {
                    mPresenter.update(day.getDate(), type, select.getId(), select.getType());
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void showProgress() {
        mDialog.show();
    }


    @Override
    public void hideProgress() {
        mDialog.hide();
    }


    @Override
    public void onFailure(String message) {
        Toasty.warning(this, message, Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onError(String message) {
        Toasty.error(this, message, Toasty.LENGTH_LONG).show();
    }


    @Override
    public void onFinished(List<Dish> dishes) {
        this.dishes = (ArrayList<Dish>) dishes;
        setSpinner(getNames(this.dishes));
    }

    @Override
    public void onFinished(Dish dish) {
        Toasty.success(this, "Success", Toasty.LENGTH_LONG).show();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i > 0) {
            Dish dish = dishes.get(i - 1);
            mName.setText(dish.getName());
            mDescription.setText(dish.getDescription());
            if (dish.getPhoto() != null && !dish.getPhoto().isEmpty()) {

                Picasso.get().load(RetrofitBuilder.BASE_PARSE_URL + dish.getPhoto()).into(mImageview);
            } else {
                mImageview.setImageResource(R.drawable.image);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
