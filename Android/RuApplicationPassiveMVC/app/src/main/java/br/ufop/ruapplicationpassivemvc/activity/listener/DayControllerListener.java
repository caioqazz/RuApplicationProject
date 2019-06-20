package br.ufop.ruapplicationpassivemvc.activity.listener;

import br.ufop.ruapplicationpassivemvc.model.entity.Day;

public interface DayControllerListener {
    void menu(Day day);

    void delete(Day day);
}
