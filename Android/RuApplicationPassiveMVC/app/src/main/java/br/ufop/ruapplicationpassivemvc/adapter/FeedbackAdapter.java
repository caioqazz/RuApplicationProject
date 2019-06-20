package br.ufop.ruapplicationpassivemvc.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.model.entity.Feedback;
import br.ufop.ruapplicationpassivemvc.service.api.RetrofitBuilder;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import br.ufop.ruapplicationpassivemvc.util.FormatDate;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {
    private Context context;
    private List<Feedback> feedbacks;
    private TokenManager tokenManager;

    public FeedbackAdapter(Context context, List<Feedback> feedbacks) {
        this.context = context;
        this.feedbacks = feedbacks;
        tokenManager = TokenManager.getInstance(context.getSharedPreferences("prefs", Context.MODE_PRIVATE));

    }


    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_feedback,
                parent, false);
        return new FeedbackAdapter.FeedbackViewHolder(view);
    }

    public Feedback getItem(int position) {
        return feedbacks.get(position);
    }


    public void refresh(List<Feedback> data) {
        if (data != feedbacks) {
            feedbacks.clear();
            feedbacks.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.name.setText(feedbacks.get(position).getName());
        holder.photo.setImageResource(R.drawable.user);
        holder.content.setText(feedbacks.get(position).getContent());
        holder.subject.setText(feedbacks.get(position).getSubject());
        holder.date.setText(FormatDate.simpleFormat(feedbacks.get(position).getDate()));


        if (feedbacks.get(position).getPhoto() != null) {
            if (!feedbacks.get(position).getPhoto().isEmpty())
                Picasso.get().load(RetrofitBuilder.BASE_PARSE_URL + feedbacks.get(position).getPhoto()).error(R.drawable.meat).into(holder.photo);
        }
        if (tokenManager.getUser().getType() == Constants.CLIENT && feedbacks.get(position).getUserView() == Constants.NOT_VISUALIZED) {
            holder.name.setTextColor(context.getResources().getColor(R.color.colorAccent));
            holder.subject.setTextColor(context.getResources().getColor(R.color.colorAccent));
        } else if (tokenManager.getUser().getType() == Constants.MANAGER && feedbacks.get(position).getManagerViewed() == Constants.NOT_VISUALIZED) {
            holder.name.setTextColor(context.getResources().getColor(R.color.colorAccent));
            holder.subject.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }

    }

    @Override
    public int getItemCount() {
        return feedbacks.size();
    }

    public class FeedbackViewHolder extends RecyclerView.ViewHolder {
        TextView subject;
        TextView content;
        TextView date;
        ImageView photo;
        TextView name;
        RelativeLayout mLayout;

        public FeedbackViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.layout_item_feedback_name);
            subject = itemView.findViewById(R.id.layout_item_feedback_subject);
            photo = itemView.findViewById(R.id.layout_item_feedback_imageview);
            date = itemView.findViewById(R.id.layout_item_feedback_date);
            content = itemView.findViewById(R.id.layout_item_feedback_content);
            mLayout = itemView.findViewById(R.id.layout_item_feedback_container);
        }

    }
}

