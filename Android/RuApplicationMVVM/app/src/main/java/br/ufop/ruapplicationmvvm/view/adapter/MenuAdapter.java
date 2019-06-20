package br.ufop.ruapplicationmvvm.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.ufop.ruapplicationmvvm.R;
import br.ufop.ruapplicationmvvm.model.entity.Dish;
import br.ufop.ruapplicationmvvm.model.entity.User;
import br.ufop.ruapplicationmvvm.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvvm.service.api.UserManager;
import br.ufop.ruapplicationmvvm.util.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private Context context;
    private List<Dish> dishes;


    public MenuAdapter(Context context, List<Dish> dishes) {
        this.context = context;
        this.dishes = dishes;
    }


    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_menu,
                parent, false);
        return new MenuViewHolder(view);
    }

    public Dish getItem(int position) {
        return dishes.get(position);
    }

    private String nameType(int type) {
        switch (type) {
            case Constants.PRINCIPAL:
                return "Principal";
            case Constants.VEGETARIANO:
                return "Vegetariano";
            case Constants.GUARNICAO:
                return "Guarnição";
            case Constants.ARROZ:
                return "Arroz";
            case Constants.FEIJAO:
                return "Feijao";
            case Constants.BEBIDAS:
                return "Bebidas";
            case Constants.SOBREMESA:
                return "Sobremesa";
            case Constants.SALADA:
                return "Salada";

        }
        return "";
    }

    private int photoType(int type) {
        switch (type) {
            case Constants.PRINCIPAL:
                return R.drawable.meat;
            case Constants.VEGETARIANO:
                return R.drawable.diet;
            case Constants.GUARNICAO:
                return R.drawable.pot;
            case Constants.ARROZ:
                return R.drawable.rice;
            case Constants.FEIJAO:
                return R.drawable.beans;
            case Constants.BEBIDAS:
                return R.drawable.juice;
            case Constants.SOBREMESA:
                return R.drawable.cupcake;
            case Constants.SALADA:
                return R.mipmap.salad;

        }
        return R.drawable.meat;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        holder.mName.setText(dishes.get(position).getName());
        holder.mDescription.setText(dishes.get(position).getDescription());
        holder.mType.setText(nameType(dishes.get(position).getType()));
        holder.mImageView.setImageResource(photoType(dishes.get(position).getType()));

        UserManager userManager = UserManager.getInstance(context.getSharedPreferences("prefs", Context.MODE_PRIVATE));
        User user = userManager.getUser();
        if (user.getType() == Constants.CLIENT)
            holder.mBtnEdit.setVisibility(View.GONE);


        if (dishes.get(position).getPhoto() != null && !dishes.get(position).getPhoto().isEmpty())
            Picasso.get().load(RetrofitBuilder.BASE_PARSE_URL + dishes.get(position).getPhoto()).error(R.drawable.image).into(holder.mImageView);


    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    public void refresh(List<Dish> data) {

        dishes.clear();
        dishes.addAll(data);
        notifyDataSetChanged();

    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout_item_menu_type)
        TextView mType;
        @BindView(R.id.layout_item_menu_btn_edit)
        ImageButton mBtnEdit;
        @BindView(R.id.layout_item_menu_imageView)
        ImageView mImageView;
        @BindView(R.id.layout_item_menu_name)
        TextView mName;
        @BindView(R.id.layout_item_menu_description)
        TextView mDescription;

        private MenuViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
