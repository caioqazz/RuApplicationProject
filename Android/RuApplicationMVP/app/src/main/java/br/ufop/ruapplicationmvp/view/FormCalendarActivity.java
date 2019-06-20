package br.ufop.ruapplicationmvp.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import br.ufop.ruapplicationmvp.R;
import br.ufop.ruapplicationmvp.contract.FormCalendarContract;
import br.ufop.ruapplicationmvp.model.entity.Day;
import br.ufop.ruapplicationmvp.model.entity.Meal;
import br.ufop.ruapplicationmvp.presenter.FormCalendarPresenter;
import br.ufop.ruapplicationmvp.util.Constants;
import br.ufop.ruapplicationmvp.util.DialogManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class FormCalendarActivity extends AppCompatActivity
        implements FormCalendarContract.View, DatePickerDialog.OnDateSetListener {

    @BindView(R.id.form_calendar_date)
    TextView formCalendarDate;
    @BindView(R.id.form_calendar_switch_status)
    Switch formCalendarSwitchStatus;
    @BindView(R.id.form_calendar_reason)
    EditText formCalendarReason;
    @BindView(R.id.form_calendar_type_dinner)
    RadioButton formCalendarTypeDinner;
    @BindView(R.id.form_calendar_reason_container)
    LinearLayout formCalendarReasonContainer;
    private FormCalendarPresenter mPresenter;
    private AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_calendar);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("");
        initiate();


        mDialog = DialogManager.loadingDialog(this);
        mPresenter = new FormCalendarPresenter(this, getSharedPreferences("prefs", MODE_PRIVATE));


    }

    private void initiate() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null && mBundle.get("day") != null) {
            Day day = (Day) mBundle.get("day");
            formCalendarDate.setText(day.getDate());
            mBundle.clear();
        }
        if (mBundle != null && mBundle.get("meal") != null) {
            Meal meal = (Meal) mBundle.get("meal");
            formCalendarDate.setText(meal.getDate());
            setType(meal.getType());
            formCalendarReason.setText(meal.getReason());
            setStatus(meal.getStatus());
            mBundle.clear();
        }


    }

    public int getStatus() {
        if (formCalendarSwitchStatus.isChecked()) {
            return Constants.ABERTO;
        }
        return Constants.FECHADO;
    }

    public void setStatus(int option) {
        if (option == 1) {
            formCalendarSwitchStatus.setChecked(true);
        } else {
            formCalendarSwitchStatus.setChecked(false);
        }

    }

    public int getType() {
        if (formCalendarTypeDinner.isChecked()) {
            return Constants.JANTAR;
        }
        return Constants.ALMOCO;

    }

    public void setType(int type) {
        if (type == Constants.JANTAR)
            formCalendarTypeDinner.setChecked(true);
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
                mPresenter.onCommitButton(formCalendarDate.getText().toString(), getType(), getStatus(), formCalendarReason.getText().toString());
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
    public void onCommitFinished(Meal meal) {
        Toasty.success(this, "Success", Toasty.LENGTH_LONG).show();
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
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @OnClick(R.id.form_calendar_switch_status)
    public void onSwitchClicked() {
        if (formCalendarSwitchStatus.isChecked()) {
            formCalendarReasonContainer.setVisibility(View.INVISIBLE);
        } else {
            formCalendarReasonContainer.setVisibility(View.VISIBLE);

        }
    }

    @OnClick(R.id.form_calendar_btn_date)
    public void onButtonClicked() {
        Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog pickerDialog = new DatePickerDialog(this, this, day, month, year);
        pickerDialog.updateDate(year, month, day);
        pickerDialog.show();

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        month += 1;
        formCalendarDate.setText(year + "-" + month + "-" + dayOfMonth);
    }
}
