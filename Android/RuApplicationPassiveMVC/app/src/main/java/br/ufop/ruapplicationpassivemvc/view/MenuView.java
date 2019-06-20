package br.ufop.ruapplicationpassivemvc.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.model.entity.Dish;
import br.ufop.ruapplicationpassivemvc.service.api.RetrofitBuilder;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import com.squareup.picasso.Picasso;

public class MenuView extends FrameLayout {
    AlertDialog dialog;

    public MenuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setListeners(OnClickListener onClickListener) {
        (findViewById(R.id.menu_principal_edit)).setOnClickListener(onClickListener);
        (findViewById(R.id.menu_vegetariano_edit)).setOnClickListener(onClickListener);
        (findViewById(R.id.menu_guarnicao_edit)).setOnClickListener(onClickListener);
        (findViewById(R.id.menu_arroz_edit)).setOnClickListener(onClickListener);
        (findViewById(R.id.menu_feijao_edit)).setOnClickListener(onClickListener);
        (findViewById(R.id.menu_bebida_edit)).setOnClickListener(onClickListener);
        (findViewById(R.id.menu_sobremesa_edit)).setOnClickListener(onClickListener);
        (findViewById(R.id.menu_participation_button)).setOnClickListener(onClickListener);

    }

    public void showLoading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_layout_message, null);
        builder.setView(view);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void showForm() {
        if (dialog != null)
            dialog.dismiss();
    }

    public void setButton(int participation) {
        if (participation == Constants.NOT_CONFIRMED) {
            (findViewById(R.id.menu_participation_button)).setBackgroundResource(R.drawable.rounded_button_commit);
            ((Button) findViewById(R.id.menu_participation_button)).setText("Participar");
        } else {
            (findViewById(R.id.menu_participation_button)).setBackgroundResource(R.drawable.rounded_button_return);
            ((Button) findViewById(R.id.menu_participation_button)).setText("Cancelar Participação");
        }
        (this.findViewById(R.id.menu_participation_button)).setVisibility(VISIBLE);
    }

    public void setHeader(int type) {

        switch (type) {
            case Constants.ALMOCO:
                ((ImageView) findViewById(R.id.menu_imageview)).setImageResource(R.drawable.sun);
                ((TextView) findViewById(R.id.menu_type)).setText(R.string.almoco);
                findViewById(R.id.menu_header).setBackgroundResource(R.color.darkYellow);
                break;
            case Constants.JANTAR:
                ((ImageView) findViewById(R.id.menu_imageview)).setImageResource(R.drawable.night);
                ((TextView) findViewById(R.id.menu_type)).setText(R.string.jantar);
                findViewById(R.id.menu_header).setBackgroundResource(R.color.night);
                break;
        }
    }

    public void setVisibilityDialog(int type) {
        if (type == View.VISIBLE) {
            (this.findViewById(R.id.menu_dishes_container)).setVisibility(View.INVISIBLE);
            (this.findViewById(R.id.menu_loading_dialog)).setVisibility(View.VISIBLE);
        } else {
            (this.findViewById(R.id.menu_loading_dialog)).setVisibility(View.INVISIBLE);
            (this.findViewById(R.id.menu_dishes_container)).setVisibility(View.VISIBLE);
        }
    }

    public void showErrorContainer(String reason) {
        (this.findViewById(R.id.menu_dishes_container)).setVisibility(View.INVISIBLE);
        (this.findViewById(R.id.menu_error_container)).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.menu_close_reason)).setText(reason);
    }

    public void setPrincipal(Dish dish) {
        if (dish.getPhoto() != null && !dish.getPhoto().isEmpty()) {
            ImageView imageView = findViewById(R.id.menu_imageview_principal);
            Picasso.get().load(RetrofitBuilder.BASE_PARSE_URL + dish.getPhoto()).error(R.drawable.meat).into(imageView);
        }
        ((TextView) findViewById(R.id.menu_principal_name)).setText(dish.getName());
        ((TextView) findViewById(R.id.menu_principal_description)).setText(dish.getDescription());

    }

    public void setSobremesa(Dish dish) {
        if (dish.getPhoto() != null && !dish.getPhoto().isEmpty()) {
            ImageView imageView = findViewById(R.id.menu_imageview_sobremesa);
            Picasso.get().load(RetrofitBuilder.BASE_PARSE_URL + dish.getPhoto()).error(R.drawable.diet).into(imageView);
        }
        ((TextView) findViewById(R.id.menu_sobremesa_name)).setText(dish.getName());
        ((TextView) findViewById(R.id.menu_sobremesa_description)).setText(dish.getDescription());

    }


    public void setBebida(Dish dish) {
        if (dish.getPhoto() != null && !dish.getPhoto().isEmpty()) {
            ImageView imageView = findViewById(R.id.menu_imageview_bebida);
            Picasso.get().load(RetrofitBuilder.BASE_PARSE_URL + dish.getPhoto()).error(R.drawable.juice).into(imageView);
        }
        ((TextView) findViewById(R.id.menu_bebida_name)).setText(dish.getName());
        ((TextView) findViewById(R.id.menu_bebida_description)).setText(dish.getDescription());

    }


    public void setFeijao(Dish dish) {
        if (dish.getPhoto() != null && !dish.getPhoto().isEmpty()) {
            ImageView imageView = findViewById(R.id.menu_imageview_feijao);
            Picasso.get().load(RetrofitBuilder.BASE_PARSE_URL + dish.getPhoto()).error(R.drawable.beans).into(imageView);
        }
        ((TextView) findViewById(R.id.menu_feijao_name)).setText(dish.getName());
        ((TextView) findViewById(R.id.menu_feijao_description)).setText(dish.getDescription());

    }

    public void setArroz(Dish dish) {
        if (dish.getPhoto() != null && !dish.getPhoto().isEmpty()) {
            ImageView imageView = findViewById(R.id.menu_imageview_arroz);
            Picasso.get().load(RetrofitBuilder.BASE_PARSE_URL + dish.getPhoto()).error(R.drawable.rice).into(imageView);
        }
        ((TextView) findViewById(R.id.menu_arroz_name)).setText(dish.getName());
        ((TextView) findViewById(R.id.menu_arroz_description)).setText(dish.getDescription());

    }

    public void setGuarnicao(Dish dish) {
        if (dish.getPhoto() != null && !dish.getPhoto().isEmpty()) {
            ImageView imageView = findViewById(R.id.menu_imageview_guarnicao);
            Picasso.get().load(RetrofitBuilder.BASE_PARSE_URL + dish.getPhoto()).error(R.drawable.pot).into(imageView);
        }
        ((TextView) findViewById(R.id.menu_guarnicao_name)).setText(dish.getName());
        ((TextView) findViewById(R.id.menu_guarnicao_description)).setText(dish.getDescription());

    }

    public void setVegetariano(Dish dish) {
        if (dish.getPhoto() != null && !dish.getPhoto().isEmpty()) {
            ImageView imageView = findViewById(R.id.menu_imageview_vegetariano);
            Picasso.get().load(RetrofitBuilder.BASE_PARSE_URL + dish.getPhoto()).error(R.drawable.diet).into(imageView);
        }
        ((TextView) findViewById(R.id.menu_vegetariano_name)).setText(dish.getName());
        ((TextView) findViewById(R.id.menu_vegetariano_description)).setText(dish.getDescription());

    }

    public void clientView() {
        (findViewById(R.id.menu_principal_edit)).setVisibility(View.GONE);
        (findViewById(R.id.menu_vegetariano_edit)).setVisibility(View.GONE);
        (findViewById(R.id.menu_guarnicao_edit)).setVisibility(View.GONE);
        (findViewById(R.id.menu_arroz_edit)).setVisibility(View.GONE);
        (findViewById(R.id.menu_feijao_edit)).setVisibility(View.GONE);
        (findViewById(R.id.menu_bebida_edit)).setVisibility(View.GONE);
        (findViewById(R.id.menu_sobremesa_edit)).setVisibility(View.GONE);

    }

    public void managerView() {
        (findViewById(R.id.menu_participation_button)).setVisibility(View.GONE);
    }

    public void setDate(String date) {
        ((TextView) findViewById(R.id.menu_date)).setText(date);
    }
}
