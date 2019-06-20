package br.ufop.ruapplicationpassivemvc.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.activity.implementation.FormCalendarActivity;
import br.ufop.ruapplicationpassivemvc.activity.listener.CalendarControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.implementation.CalendarController;
import br.ufop.ruapplicationpassivemvc.model.entity.Meal;
import br.ufop.ruapplicationpassivemvc.model.entity.User;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import br.ufop.ruapplicationpassivemvc.util.Networking;
import br.ufop.ruapplicationpassivemvc.view.CalendarView;

public class CalendarFragment extends Fragment implements CalendarControllerListener, View.OnClickListener {
    private CalendarController controller;
    private CalendarView calendarView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Calendário de Funcionamento");
        calendarView = getActivity().findViewById(R.id.calendar_container);
        controller = new CalendarController(calendarView, this);
        calendarView.setListeners(controller, controller, controller);
        getActivity().findViewById(R.id.calendar_btn_add).setOnClickListener(this);

        TokenManager tokenManager = TokenManager.getInstance(getActivity().getSharedPreferences("prefs", getActivity().MODE_PRIVATE));
        User user = tokenManager.getUser();
        if (user.getType() == Constants.CLIENT) {
            calendarView.clientView();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Networking.isNetworkConnected(calendarView))
            controller.onRefresh();
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(getContext(), FormCalendarActivity.class));
    }

    @Override
    public void form(Meal meal) {
        Intent intent = new Intent(getContext(), FormCalendarActivity.class);
        intent.putExtra("meal", meal);
        startActivity(intent);
    }


}
