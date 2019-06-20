package br.ufop.ruapplicationpassivemvc.controller.listener;

import br.ufop.ruapplicationpassivemvc.model.entity.Dish;

public interface MenuControllerListener {
    void edit(Dish dish, int type);
}
