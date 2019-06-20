package br.ufop.ruapplicationmvvm.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ufop.ruapplicationmvvm.R;
import br.ufop.ruapplicationmvvm.databinding.LayoutItemCalendarBinding;
import br.ufop.ruapplicationmvvm.model.entity.Meal;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {
    private Context context;
    private List<Meal> meals;


    public CalendarAdapter(Context context, List<Meal> meals) {
        this.context = context;
        this.meals = meals;
    }


    @NonNull
    @Override
    public CalendarAdapter.CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutItemCalendarBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.layout_item_calendar, parent, false);

        CalendarViewHolder viewHolder = new CalendarViewHolder(binding);
        return viewHolder;

    }

    public Meal getItem(int position) {
        return meals.get(position);
    }

    public void refresh(List<Meal> data) {
        meals.clear();
        meals.addAll(data);
        notifyDataSetChanged();

    }

    @Override
    public void onBindViewHolder(@NonNull CalendarAdapter.CalendarViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.binding.setMeal(meals.get(position));

    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder {
        public LayoutItemCalendarBinding binding;


        public CalendarViewHolder(LayoutItemCalendarBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
