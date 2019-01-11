package com.senior.project.genealogy.view.fragment.branch.DetailEventBranchFragment.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.senior.project.genealogy.R;
import com.senior.project.genealogy.response.Event;
import com.senior.project.genealogy.response.Notification;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    private Context mContext;
    private List<Event> mEvent;
    private FragmentManager mFragment;

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvContent, tvDate;

        MyViewHolder(View view) {
            super(view);
            tvDate = view.findViewById(R.id.tvDate);
            tvContent = view.findViewById(R.id.tvContent);
        }
    }

    public EventAdapter(Context context, FragmentManager fragment, List<Event> events) {
        mContext = context;
        mFragment = fragment;
        mEvent = events;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Event item = mEvent.get(position);
        holder.tvDate.setText(item.getDate());
        holder.tvContent.setText(item.getContent());
    }

    @Override
    public int getItemCount() {
        return mEvent.size();
    }

    public void addEvent(Event event) {
        mEvent.add(event);
        notifyDataSetChanged();
    }
}
