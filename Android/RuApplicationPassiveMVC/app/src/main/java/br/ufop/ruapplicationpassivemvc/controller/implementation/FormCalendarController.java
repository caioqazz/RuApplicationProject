package br.ufop.ruapplicationpassivemvc.controller.implementation;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import br.ufop.ruapplicationpassivemvc.activity.listener.FormCalendarControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.listener.MealServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.Meal;
import br.ufop.ruapplicationpassivemvc.service.MealService;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import br.ufop.ruapplicationpassivemvc.util.ReplyAction;
import br.ufop.ruapplicationpassivemvc.view.FormCalendarView;

import java.util.Calendar;
import java.util.List;

import retrofit2.Response;

public class FormCalendarController implements MealServiceListener, DatePickerDialog.OnDateSetListener, View.OnClickListener {


    private TokenManager tokenManager;
    private FormCalendarView view;
    private FormCalendarControllerListener listener;

    public FormCalendarController(FormCalendarView view, FormCalendarControllerListener listener) {
        this.view = view;
        this.listener = listener;
        tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE));

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month += 1;
        this.view.setDate(year + "-" + month + "-" + dayOfMonth);
    }

    public void update() {
        if (validate(view.getDate(), view.getType(), view.getStatus(), view.getReason())) {
            view.showLoading();
            MealService service = new MealService(this, tokenManager);
            service.update(view.getDate(), view.getType(), view.getStatus(), view.getReason());
        }

    }

    @Override
    public void onClick(View v) {

        Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog pickerDialog = new DatePickerDialog(view.getContext(), this, day, month, year);
        pickerDialog.updateDate(year, month, day);
        pickerDialog.show();


    }


    @Override
    public void onActionFailure(Throwable t, int option) {
        view.showForm();
    }

    @Override
    public void onActionSuccess(Response<Meal> response, int option) {
        view.showForm();
        listener.onActionExecuted(ReplyAction.replyActionSucess(option, ""), Constants.SUCCESS);

    }

    @Override
    public void onActionError(Response<Meal> response, int option) {
        view.showForm();
        listener.onActionExecuted(ReplyAction.replyActionError(option, ""), Constants.ERROR);
    }

    @Override
    public void onIndexSuccess(Response<List<Meal>> response) {

    }

    @Override
    public void onIndexError(Response<List<Meal>> response) {

    }

    private boolean validate(String date, int type, int status, String reason) {

        if (date.equals("Selecione a data")) {
            Toast.makeText(view.getContext(), "Selecione uma data para proseguir", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (reason.isEmpty() && status == Constants.FECHADO) {
            Toast.makeText(view.getContext(), "Preencha a justificativa", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
