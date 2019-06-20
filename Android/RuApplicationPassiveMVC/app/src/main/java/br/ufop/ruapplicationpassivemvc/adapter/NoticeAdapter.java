package br.ufop.ruapplicationpassivemvc.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.model.entity.Notice;
import br.ufop.ruapplicationpassivemvc.model.entity.User;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import br.ufop.ruapplicationpassivemvc.util.FormatDate;

import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {
    private List<Notice> notices;
    private Context context;
    private User user;

    public NoticeAdapter(List<Notice> notices, Context context) {
        this.notices = notices;
        this.context = context;
        TokenManager tokenManager = TokenManager.getInstance(context.getSharedPreferences("prefs", Context.MODE_PRIVATE));
        user = tokenManager.getUser();
    }

    public List<Notice> getNotices() {
        return notices;
    }


    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_notice,
                parent, false);
        return new NoticeViewHolder(view);
    }

    public Notice getItem(int position) {
        return notices.get(position);
    }

    public void refresh(List<Notice> data) {
        notices.clear();
        notices.addAll(data);
        notifyDataSetChanged();

    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.subject.setText(notices.get(position).getSubject());
        holder.content.setText(notices.get(position).getContent());
        holder.views.setText(notices.get(position).getViews() + "");
        holder.date.setText(FormatDate.simpleFormat(notices.get(position).getUpdated_at()));

        switch (notices.get(position).getType()) {
            case 0:
                holder.icon.setImageResource(R.drawable.info);
                break;
            case 1:
                holder.icon.setImageResource(R.drawable.alert);
                break;
            case 2:
                holder.icon.setImageResource(R.drawable.warning);
                break;

            default:
                holder.icon.setImageResource(R.drawable.info);
        }


        if (user.getType() == Constants.CLIENT) {
            holder.viewsContainer.setVisibility(View.GONE);
            holder.content.setMaxLines(2);
            if (notices.get(position).getVisualized() == Constants.NOT_VISUALIZED) {
                holder.content.setTextColor(context.getResources().getColor(R.color.colorAccent));
                holder.subject.setTextColor(context.getResources().getColor(R.color.colorAccent));
                holder.date.setTextColor(context.getResources().getColor(R.color.colorAccent));
            }
        }

    }

    @Override
    public int getItemCount() {
        return notices.size();
    }


    class NoticeViewHolder extends RecyclerView.ViewHolder {

        TextView subject;
        TextView content;
        TextView date;
        TextView views;
        RelativeLayout mLayout;
        ImageView icon;

        LinearLayout viewsContainer;

        NoticeViewHolder(View itemView) {
            super(itemView);

            subject = itemView.findViewById(R.id.layout_item_notice_subject);
            content = itemView.findViewById(R.id.layout_item_notice_content);
            date = itemView.findViewById(R.id.layout_item_notice_date);
            views = itemView.findViewById(R.id.layout_item_notice_views_text);
            icon = itemView.findViewById(R.id.layout_item_notice_img_icon);
            mLayout = itemView.findViewById(R.id.layout_item_notice_container);
            viewsContainer = itemView.findViewById(R.id.layout_item_notice_views_container);


        }

    }
}
