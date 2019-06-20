package br.ufop.ruapplicationpassivemvc.activity.listener;

import br.ufop.ruapplicationpassivemvc.model.entity.Dish;

public interface DishControllerListener {

    void detailDish(Dish dish);

    void editDish(Dish dish);
}
