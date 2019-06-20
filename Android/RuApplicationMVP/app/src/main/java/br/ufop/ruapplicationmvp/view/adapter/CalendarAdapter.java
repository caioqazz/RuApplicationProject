package br.ufop.ruapplicationmvp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ufop.ruapplicationmvp.R;
import br.ufop.ruapplicationmvp.model.entity.Meal;
import br.ufop.ruapplicationmvp.util.Constants;
import br.ufop.ruapplicationmvp.util.NameDate;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_calendar,
                parent, false);
        return new CalendarAdapter.CalendarViewHolder(view);
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
        holder.name.setText(NameDate.dayName(meals.get(position).getNumDay()));
        holder.day.setText(meals.get(position).getDay());
        holder.month.setText(NameDate.monthName(meals.get(position).getMonth()));
        holder.reason.setText(meals.get(position).getReason());
        holder.year.setText(meals.get(position).getDate().substring(0, 4));
        if (meals.get(position).getType() == Constants.ALMOCO) {
            holder.type.setText(R.string.almoco);
        } else {
            holder.type.setText(R.string.jantar);
        }
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder {

        TextView day;
        TextView name;
        TextView month;
        TextView type;
        TextView reason;
        TextView year;


        private CalendarViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.layout_item_calendar_name);
            day = itemView.findViewById(R.id.layout_item_calendar_day);
            month = itemView.findViewById(R.id.layout_item_calendar_month);
            type = itemView.findViewById(R.id.layout_item_calendar_type);
            reason = itemView.findViewById(R.id.layout_item_calendar_reason);
            year = itemView.findViewById(R.id.layout_item_calendar_year);
        }

    }
}
