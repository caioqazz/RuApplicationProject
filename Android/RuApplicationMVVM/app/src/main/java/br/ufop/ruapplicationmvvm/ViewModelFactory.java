package br.ufop.ruapplicationmvvm;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import br.ufop.ruapplicationmvvm.viewmodel.CalendarViewModel;
import br.ufop.ruapplicationmvvm.viewmodel.DayDemandViewModel;
import br.ufop.ruapplicationmvvm.viewmodel.DayMealViewModel;
import br.ufop.ruapplicationmvvm.viewmodel.DemandViewModel;
import br.ufop.ruapplicationmvvm.viewmodel.DetailDishViewModel;
import br.ufop.ruapplicationmvvm.viewmodel.DetailFeedbackViewModel;
import br.ufop.ruapplicationmvvm.viewmodel.DetailNoticeViewModel;
import br.ufop.ruapplicationmvvm.viewmodel.DishViewModel;
import br.ufop.ruapplicationmvvm.viewmodel.FeedbackViewModel;
import br.ufop.ruapplicationmvvm.viewmodel.FormCalendarViewModel;
import br.ufop.ruapplicationmvvm.viewmodel.FormDishViewModel;
import br.ufop.ruapplicationmvvm.viewmodel.FormFeedbackViewModel;
import br.ufop.ruapplicationmvvm.viewmodel.FormMenuViewModel;
import br.ufop.ruapplicationmvvm.viewmodel.FormNoticeViewModel;
import br.ufop.ruapplicationmvvm.viewmodel.MenuViewModel;
import br.ufop.ruapplicationmvvm.viewmodel.NoticeViewModel;
import br.ufop.ruapplicationmvvm.viewmodel.PosLoginViewModel;
import br.ufop.ruapplicationmvvm.viewmodel.ProfileViewModel;
import br.ufop.ruapplicationmvvm.viewmodel.ReportDemandViewModel;
import br.ufop.ruapplicationmvvm.viewmodel.ReportDishesViewModel;
import br.ufop.ruapplicationmvvm.viewmodel.WeekViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private SharedPreferences prefs;

    public ViewModelFactory(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == PosLoginViewModel.class) {
            return (T) new PosLoginViewModel(prefs);
        } else if (modelClass == DemandViewModel.class) {
            return (T) new DemandViewModel(prefs);
        } else if (modelClass == WeekViewModel.class) {
            return (T) new WeekViewModel(prefs);
        } else if (modelClass == DayMealViewModel.class) {
            return (T) new DayMealViewModel(prefs);
        } else if (modelClass == DayDemandViewModel.class) {
            return (T) new DayDemandViewModel(prefs);
        } else if (modelClass == CalendarViewModel.class) {
            return (T) new CalendarViewModel(prefs);
        } else if (modelClass == NoticeViewModel.class) {
            return (T) new NoticeViewModel(prefs);
        } else if (modelClass == DishViewModel.class) {
            return (T) new DishViewModel(prefs);
        } else if (modelClass == FeedbackViewModel.class) {
            return (T) new FeedbackViewModel(prefs);
        } else if (modelClass == FormCalendarViewModel.class) {
            return (T) new FormCalendarViewModel(prefs);
        } else if (modelClass == FormDishViewModel.class) {
            return (T) new FormDishViewModel(prefs);
        } else if (modelClass == MenuViewModel.class) {
            return (T) new MenuViewModel(prefs);
        } else if (modelClass == ProfileViewModel.class) {
            return (T) new ProfileViewModel(prefs);
        } else if (modelClass == DetailDishViewModel.class) {
            return (T) new DetailDishViewModel(prefs);
        } else if (modelClass == DetailFeedbackViewModel.class) {
            return (T) new DetailFeedbackViewModel(prefs);
        } else if (modelClass == DetailNoticeViewModel.class) {
            return (T) new DetailNoticeViewModel(prefs);
        } else if (modelClass == FormMenuViewModel.class) {
            return (T) new FormMenuViewModel(prefs);
        } else if (modelClass == FormNoticeViewModel.class) {
            return (T) new FormNoticeViewModel(prefs);
        } else if (modelClass == FormFeedbackViewModel.class) {
            return (T) new FormFeedbackViewModel(prefs);
        } else if (modelClass == ReportDemandViewModel.class) {
            return (T) new ReportDemandViewModel(prefs);
        } else if (modelClass == ReportDishesViewModel.class) {
            return (T) new ReportDishesViewModel(prefs);
        } else {
            return super.create(modelClass);
        }
    }
}
