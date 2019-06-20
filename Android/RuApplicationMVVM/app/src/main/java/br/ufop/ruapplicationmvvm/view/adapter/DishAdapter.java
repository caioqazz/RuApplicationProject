package br.ufop.ruapplicationmvvm.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
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

        UserManager userManager = UserManager.getInstance(context.getSharedPreferences("prefs", Context.MODE_PRIVATE));
        User user = userManager.getUser();
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
