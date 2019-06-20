package br.ufop.ruapplicationpassivemvc.controller.implementation;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import br.ufop.ruapplicationpassivemvc.activity.listener.DetailDishControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.listener.ClassificationServiceListener;
import br.ufop.ruapplicationpassivemvc.controller.listener.DishServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.Classification;
import br.ufop.ruapplicationpassivemvc.model.entity.Dish;
import br.ufop.ruapplicationpassivemvc.service.ClassificationService;
import br.ufop.ruapplicationpassivemvc.service.DishService;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import br.ufop.ruapplicationpassivemvc.util.ReplyAction;
import br.ufop.ruapplicationpassivemvc.view.DetailDishView;

import java.util.List;

import retrofit2.Response;

public class DetailDishController implements ClassificationServiceListener, DishServiceListener {

    private DetailDishView view;
    private Dish dish;
    private DetailDishControllerListener listener;
    private TokenManager tokenManager;

    public DetailDishController(DetailDishView view, Dish dish, DetailDishControllerListener listener) {
        this.view = view;
        this.dish = dish;
        this.listener = listener;
        tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE));
    }

    public void delete() {
        view.showLoading();
        DishService dishService = new DishService(this, tokenManager);
        dishService.delete(dish.getId());

    }

    public void showClassification() {
        ClassificationService service = new ClassificationService(this, tokenManager);
        service.show(dish.getId());
    }

    public void updateClassification() {
        view.showLoading();
        ClassificationService service = new ClassificationService(this, tokenManager);
        service.store(dish.getId(), view.getRating(), view.getCheckBox(), view.getComment());
    }

    @Override
    public void onActionSuccess(Response<Dish> response, int option) {
        view.showForm();
        listener.onActionExecuted(ReplyAction.replyActionSucess(option, dish.getName()), Constants.SUCCESS);

    }

    @Override
    public void onActionError(Response<Dish> response, int option) {
        view.showForm();
        listener.onActionExecuted(ReplyAction.replyActionError(option, dish.getName()), Constants.ERROR);

    }

    @Override
    public void onActionFailure(Throwable t, int option) {
        view.showForm();
    }


    @Override
    public void onActionSuccessClassification(Response<Classification> response, int option) {
        view.showForm();
        switch (option) {
            case Constants.UPDATE:
                listener.onActionExecuted(ReplyAction.replyActionSucess(option, ""), Constants.SUCCESS);
                break;

            case Constants.SHOW:
                if (response.body() != null)
                    setClassification(response.body());
                break;
        }


    }

    private void setClassification(Classification classification) {
        view.setRating(classification.getRating());
        view.setComment(classification.getComment());
        view.setCheck(classification.getOption());
    }

    @Override
    public void onActionErrorClassification(Response<Classification> response, int option) {
        view.showForm();
        if (option == Constants.UPDATE)
            listener.onActionExecuted(ReplyAction.replyActionError(option, ""), Constants.ERROR);
    }

    @Override
    public void onActionFailureClassification(Throwable t, int option) {
        view.showForm();
    }


    @Override
    public void onIndexSuccess(Response<List<Dish>> response) {

    }

    @Override
    public void onIndexError(Response<List<Dish>> response) {

    }

}
