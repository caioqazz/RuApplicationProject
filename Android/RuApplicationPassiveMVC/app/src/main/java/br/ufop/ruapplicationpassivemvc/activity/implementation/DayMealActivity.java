package br.ufop.ruapplicationpassivemvc.activity.implementation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.activity.listener.DayControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.implementation.MealDayController;
import br.ufop.ruapplicationpassivemvc.model.entity.Day;
import br.ufop.ruapplicationpassivemvc.model.entity.Week;
import br.ufop.ruapplicationpassivemvc.util.NameDate;
import br.ufop.ruapplicationpassivemvc.view.DayView;

public class DayMealActivity extends AppCompatActivity implements DayControllerListener {
    private Week week;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        DayView view = findViewById(R.id.day_container);


        initiate();
        MealDayController controller = new MealDayController(view, week, this);
        view.setListeners(controller, controller);
        controller.onRefresh();
    }

    private void initiate() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null && mBundle.get("week") != null) {
            week = (Week) mBundle.get("week");
            String title = week.getWeekOfMonth() + "ª Semana de " + NameDate.monthName(week.getMonth());
            setTitle(title);
            mBundle.clear();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void menu(Day day) {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("day", day);
        startActivity(intent);
    }

    @Override
    public void delete(Day day) {
        Intent intent = new Intent(this, FormCalendarActivity.class);
        intent.putExtra("day", day);
        startActivity(intent);
    }


}
