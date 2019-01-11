package com.senior.project.genealogy.view.fragment.notification.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Notification;
import com.senior.project.genealogy.util.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private Context mContext;
    private List<Notification> mNotification;
    private FragmentManager mFragment;

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvContent, tvDate;
        CircleImageView imgNoti;

        MyViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvDate = view.findViewById(R.id.tvDate);
            tvContent = view.findViewById(R.id.tvContent);
            imgNoti = view.findViewById(R.id.imgNoti);
        }
    }

    public NotificationAdapter(Context context, FragmentManager fragment, List<Notification> notification) {
        mContext = context;
        mFragment = fragment;
        mNotification = notification;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Notification item = mNotification.get(position);
        DateFormat dfTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dateTime = dfTime.format(item.getDate());
        String date = df.format(item.getDate());
        switch (item.getNotificationTypeId()){
            case Constants.NOTIFICATION_TYPE.ACCEPT_JOIN:
                holder.imgNoti.setImageResource(R.drawable.noti_accept);
                holder.tvDate.setText(dateTime);
                break;
            case Constants.NOTIFICATION_TYPE.MEMBER_JOIN:
                holder.imgNoti.setImageResource(R.drawable.noti_join);
                holder.tvDate.setText(dateTime);
                break;
            case Constants.NOTIFICATION_TYPE.DEATH_ANNIVERSARY:
                holder.imgNoti.setImageResource(R.drawable.noti_death);
                holder.tvDate.setText(date);
                break;
            case Constants.NOTIFICATION_TYPE.FAMILY_ACTIVITIES:
                holder.imgNoti.setImageResource(R.drawable.noti_party);
                holder.tvDate.setText(dateTime);
                break;
            case Constants.NOTIFICATION_TYPE.BIRTHDAY_PARTY:
                holder.imgNoti.setImageResource(R.drawable.noti_birthday);
                holder.tvDate.setText(date);
                break;
        }
        holder.tvTitle.setText(item.getTitle());
        holder.tvContent.setText(item.getContent());
    }

    @Override
    public int getItemCount() {
        return mNotification.size();
    }

}
