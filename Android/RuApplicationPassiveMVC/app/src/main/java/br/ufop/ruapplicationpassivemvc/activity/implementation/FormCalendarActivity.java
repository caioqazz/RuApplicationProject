package br.ufop.ruapplicationpassivemvc.activity.implementation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.activity.listener.FormCalendarControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.implementation.FormCalendarController;
import br.ufop.ruapplicationpassivemvc.model.entity.Day;
import br.ufop.ruapplicationpassivemvc.model.entity.Meal;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import br.ufop.ruapplicationpassivemvc.view.FormCalendarView;

public class FormCalendarActivity extends AppCompatActivity implements FormCalendarControllerListener {

    private FormCalendarController controller;
    private FormCalendarView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_calendar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        view = findViewById(R.id.form_calendar_container);
        setTitle("");
        initiate();

        controller = new FormCalendarController(view, this);
        view.setListener(controller);


    }

    private void initiate() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null && mBundle.get("day") != null) {
            Day day = (Day) mBundle.get("day");
            view.setDate(day.getDate());
            mBundle.clear();
        }
        if (mBundle != null && mBundle.get("meal") != null) {
            Meal meal = (Meal) mBundle.get("meal");
            view.setDate(meal.getDate());
            view.setType(meal.getType());
            view.setReason(meal.getReason());
            view.setStatus(meal.getStatus());
            mBundle.clear();
        }


        final Switch aSwitch = findViewById(R.id.form_calendar_switch_status);
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aSwitch.isChecked()) {
                    findViewById(R.id.form_calendar_reason_container).setVisibility(View.INVISIBLE);
                } else {
                    findViewById(R.id.form_calendar_reason_container).setVisibility(View.VISIBLE);

                }
            }
        });
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
                controller.update();
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
