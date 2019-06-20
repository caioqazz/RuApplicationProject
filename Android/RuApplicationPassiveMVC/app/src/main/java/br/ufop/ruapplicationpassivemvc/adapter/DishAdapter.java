package br.ufop.ruapplicationpassivemvc.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.model.entity.Dish;
import br.ufop.ruapplicationpassivemvc.model.entity.User;
import br.ufop.ruapplicationpassivemvc.service.api.RetrofitBuilder;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {
    private Context context;
    private List<Dish> dishes;


    public DishAdapter(Context context, List<Dish> dishes) {
        this.context = context;
        this.dishes = dishes;
    }


    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_dish,
                parent, false);
        return new DishAdapter.DishViewHolder(view);
    }

    public Dish getItem(int position) {
        return dishes.get(position);
    }


    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        holder.name.setText(dishes.get(position).getName());
        holder.photo.setImageResource(R.drawable.image);
        holder.description.setText(dishes.get(position).getDescription());

        TokenManager tokenManager = TokenManager.getInstance(context.getSharedPreferences("prefs", Context.MODE_PRIVATE));
        User user = tokenManager.getUser();
        if (user.getType() == Constants.CLIENT) {
            holder.ratingBar.setVisibility(View.GONE);
        }

        if (dishes.get(position).getAverage() != null) {
            holder.ratingBar.setRating(dishes.get(position).getAverage());
        } else {
            holder.ratingBar.setRating(5);
        }

        if (dishes.get(position).getPhoto() != null && !dishes.get(position).getPhoto().isEmpty()) {
            Picasso.get().load(RetrofitBuilder.BASE_PARSE_URL + dishes.get(position).getPhoto()).error(R.drawable.image).into(holder.photo);
        }

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

    public class DishViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView description;
        ImageView photo;
        RatingBar ratingBar;
        RelativeLayout mLayout;

        private DishViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.layout_item_dish_name);
            description = itemView.findViewById(R.id.layout_item_dish_description);
            photo = itemView.findViewById(R.id.layout_item_dish_photo);
            ratingBar = itemView.findViewById(R.id.layout_item_dish_rating);
            mLayout = itemView.findViewById(R.id.layout_item_dish_container);
        }

    }
}
