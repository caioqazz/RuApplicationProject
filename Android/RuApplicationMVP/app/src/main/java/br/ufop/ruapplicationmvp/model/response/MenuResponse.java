package br.ufop.ruapplicationmvp.model.response;

import java.util.List;

import br.ufop.ruapplicationmvp.model.entity.Dish;
import br.ufop.ruapplicationmvp.model.entity.Meal;

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
