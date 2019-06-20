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
import br.ufop.ruapplicationmvp.model.entity.Day;
import br.ufop.ruapplicationmvp.util.NameDate;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayMenuViewHolder> {
    private Context context;
    private List<Day> days;


    public DayAdapter(Context context, List<Day> days) {
        this.context = context;
        this.days = days;
    }


    @NonNull
    @Override
    public DayAdapter.DayMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_days_menu,
                parent, false);
        return new DayAdapter.DayMenuViewHolder(view);
    }

    public Day getItem(int position) {
        return days.get(position);
    }


    @Override
    public void onBindViewHolder(@NonNull DayAdapter.DayMenuViewHolder holder, int position) {
        holder.name.setText(NameDate.dayName(days.get(position).getNumDay()));
        holder.day.setText(days.get(position).getDay());
        holder.month.setText(NameDate.monthName(days.get(position).getMonth()));

    }

    public void refresh(List<Day> data) {
        days.clear();
        days.addAll(data);
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public class DayMenuViewHolder extends RecyclerView.ViewHolder {

        TextView day;
        TextView name;
        TextView month;


        private DayMenuViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.layout_item_days_menu_name);
            day = itemView.findViewById(R.id.layout_item_days_menu_day);
            month = itemView.findViewById(R.id.layout_item_days_menu_month);
        }

    }
}
