package br.ufop.ruapplicationmvvm.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import java.util.Calendar;

import br.ufop.ruapplicationmvvm.R;
import br.ufop.ruapplicationmvvm.ViewModelFactory;
import br.ufop.ruapplicationmvvm.databinding.ActivityFormCalendarBinding;
import br.ufop.ruapplicationmvvm.model.entity.Day;
import br.ufop.ruapplicationmvvm.model.entity.Meal;
import br.ufop.ruapplicationmvvm.util.Constants;
import br.ufop.ruapplicationmvvm.util.DialogManager;
import br.ufop.ruapplicationmvvm.util.Status;
import br.ufop.ruapplicationmvvm.viewmodel.FormCalendarViewModel;
import es.dmoral.toasty.Toasty;

public class FormCalendarActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private FormCalendarViewModel mViewModel;
    private AlertDialog mDialog;
    private ActivityFormCalendarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_form_calendar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("");
        initiate();


        mDialog = DialogManager.loadingDialog(this);
        mViewModel = ViewModelProviders
                .of(this, new ViewModelFactory(getSharedPreferences("prefs", MODE_PRIVATE)))
                .get(FormCalendarViewModel.class);

        mViewModel.getResult().observe(this, result -> {
            loading(result.getStatus() == Status.LOADING);

            if (result.getStatus() == Status.SUCCESS)
                onCommitFinished(result.getData());
            else if (result.getStatus() == Status.ERROR)
                onError(result.getError());
            else if (result.getStatus() == Status.FAILURE)
                onFailure(result.getFailure());
        });
        binding.formCalendarBtnDate.setOnClickListener(this);
    }


    private void loading(boolean isLoading) {
        if (isLoading) {
            mDialog.show();
        } else {
            mDialog.hide();
        }
    }

    private void initiate() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null && mBundle.get("day") != null) {
            Day day = (Day) mBundle.get("day");
            binding.formCalendarDate.setText(day.getDate());
            mBundle.clear();
        }
        if (mBundle != null && mBundle.get("meal") != null) {
            Meal meal = (Meal) mBundle.get("meal");
            binding.formCalendarDate.setText(meal.getDate());
            setType(meal.getType());
            binding.formCalendarReason.setText(meal.getReason());
            setStatus(meal.getStatus());
            mBundle.clear();
        }


    }

    public void setType(int type) {
        if (type == Constants.JANTAR)
            binding.formCalendarTypeDinner.setChecked(true);
    }

    public void setStatus(int option) {
        if (option == 1) {
            binding.formCalendarSwitchStatus.setChecked(true);
        } else {
            binding.formCalendarSwitchStatus.setChecked(false);
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
                //   mViewModel.update();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public void onCommitFinished(Meal meal) {
        Toasty.success(this, "Success", Toasty.LENGTH_LONG).show();
    }

    public void onFailure(String message) {
        Toasty.warning(this, message, Toasty.LENGTH_LONG).show();
    }

    public void onError(String message) {
        Toasty.error(this, message, Toasty.LENGTH_LONG).show();
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        month += 1;
        binding.formCalendarDate.setText(year + "-" + month + "-" + dayOfMonth);
    }

    @Override
    public void onClick(View view) {
        Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog pickerDialog = new DatePickerDialog(this, this, day, month, year);
        pickerDialog.updateDate(year, month, day);
        pickerDialog.show();
    }
}
