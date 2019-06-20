package br.ufop.ruapplicationpassivemvc.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.model.entity.Dish;
import br.ufop.ruapplicationpassivemvc.service.api.RetrofitBuilder;
import com.squareup.picasso.Picasso;

public class FormMenuView extends LinearLayout {
    AlertDialog dialog;

    public FormMenuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setListener(AdapterView.OnItemSelectedListener onItemSelectedListener) {
        ((Spinner) this.findViewById(R.id.menu_form_spinner)).setOnItemSelectedListener(onItemSelectedListener);
    }

    public void setPreView(Dish dish) {
        ((TextView) findViewById(R.id.menu_form_name)).setText(dish.getName());
        ((TextView) findViewById(R.id.menu_form_description)).setText(dish.getDescription());
        ImageView imageView = findViewById(R.id.menu_form_imageview);
        if (dish.getPhoto() != null && !dish.getPhoto().isEmpty()) {

            Picasso.get().load(RetrofitBuilder.BASE_PARSE_URL + dish.getPhoto()).into(imageView);
        } else {
            imageView.setImageResource(R.drawable.image);
        }
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

    public void setAdapterSpinner(ArrayAdapter<String> adapter) {
        ((Spinner) this.findViewById(R.id.menu_form_spinner)).setAdapter(adapter);
    }

    public void setPositionSpinner(int position) {
        ((Spinner) this.findViewById(R.id.menu_form_spinner)).setSelection(position);
    }
}
