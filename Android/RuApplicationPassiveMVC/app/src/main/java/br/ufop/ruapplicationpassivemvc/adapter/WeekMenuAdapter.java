package br.ufop.ruapplicationpassivemvc.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.model.entity.Week;
import br.ufop.ruapplicationpassivemvc.util.NameDate;

import java.util.List;

public class WeekMenuAdapter extends RecyclerView.Adapter<WeekMenuAdapter.WeekMenuViewHolder> {
    private Context context;
    private List<Week> weeks;


    public WeekMenuAdapter(Context context, List<Week> weeks) {
        this.context = context;
        this.weeks = weeks;
    }


    @NonNull
    @Override
    public WeekMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_weekmenu,
                parent, false);
        return new WeekMenuAdapter.WeekMenuViewHolder(view);
    }

    public Week getItem(int position) {
        return weeks.get(position);
    }

    public void refresh(List<Week> data) {

        weeks.clear();
        weeks.addAll(data);
        notifyDataSetChanged();

    }

    @Override
    public void onBindViewHolder(@NonNull WeekMenuViewHolder holder, int position) {

        String name = "Semana de " + NameDate.monthName(weeks.get(position).getMonth());
        String weekNum = weeks.get(position).getWeekOfMonth() + "ª";
        String days = "Dia(s):" + weeks.get(position).getDays() + ".";
        String year = "" + weeks.get(position).getYear();

        holder.name.setText(name);
        holder.weekNum.setText(weekNum);
        holder.days.setText(days);
        holder.year.setText(year);

    }

    @Override
    public int getItemCount() {
        return weeks.size();
    }

    public class WeekMenuViewHolder extends RecyclerView.ViewHolder {
        TextView year;
        TextView weekNum;
        TextView days;
        TextView name;


        private WeekMenuViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.layout_item_week_menu_name);
            weekNum = itemView.findViewById(R.id.layout_item_week_menu_weeknum);
            year = itemView.findViewById(R.id.layout_item_week_menu_year);
            days = itemView.findViewById(R.id.layout_item_week_menu_days);

        }

    }
}
