package br.ufop.ruapplicationmvvm.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.ufop.ruapplicationmvvm.R;
import br.ufop.ruapplicationmvvm.model.entity.Feedback;
import br.ufop.ruapplicationmvvm.model.entity.User;
import br.ufop.ruapplicationmvvm.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvvm.service.api.UserManager;
import br.ufop.ruapplicationmvvm.util.Constants;
import br.ufop.ruapplicationmvvm.util.FormatDate;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {
    private Context context;
    private List<Feedback> feedbacks;

    public FeedbackAdapter(Context context, List<Feedback> feedbacks) {
        this.context = context;
        this.feedbacks = feedbacks;

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
        UserManager userManager = UserManager.getInstance(context.getSharedPreferences("prefs", Context.MODE_PRIVATE));
        User user = userManager.getUser();
        if (user.getType() == Constants.CLIENT && feedbacks.get(position).getUserView() == Constants.NOT_VISUALIZED) {
            holder.name.setTextColor(context.getResources().getColor(R.color.colorAccent));
            holder.subject.setTextColor(context.getResources().getColor(R.color.colorAccent));
        } else if (user.getType() == Constants.MANAGER && feedbacks.get(position).getManagerViewed() == Constants.NOT_VISUALIZED) {
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

