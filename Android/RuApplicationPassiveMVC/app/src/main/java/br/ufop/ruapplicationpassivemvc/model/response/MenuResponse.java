package br.ufop.ruapplicationpassivemvc.model.response;

import br.ufop.ruapplicationpassivemvc.model.entity.Dish;
import br.ufop.ruapplicationpassivemvc.model.entity.Meal;

import java.util.List;

public class MenuResponse {
    Meal meal;
    List<Dish> dishes;

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}
