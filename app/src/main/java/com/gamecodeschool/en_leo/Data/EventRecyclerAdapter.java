package com.gamecodeschool.en_leo.Data;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gamecodeschool.en_leo.Activities.EventDetailActivity;
import com.gamecodeschool.en_leo.Model.Event;
import com.gamecodeschool.en_leo.R;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.ViewHolder> {

    public Context context;
    private List<Event> EventList;

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public EventRecyclerAdapter(Context context, List<Event> eventlist) {
        this.context = context;
        this.EventList = eventlist;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        EventRecyclerAdapter.ViewHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_eventrow, parent, false);

        viewHolder = new ViewHolder(view, context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final Event event = EventList.get(position);

        holder.title.setText(event.getTitle());

        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
        String formattedDate = dateFormat.format(new Date(Long.valueOf(event.getTimestamp())).getTime());

        holder.timestamp.setText(formattedDate);

        Glide.with(context)
                .asBitmap()
                .load(event.getImage())
                .into(holder.image);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent intent = new Intent(context, EventDetailActivity.class);
                        intent.putExtra("event_image_url", event.getImage());
                        intent.putExtra("event_title", event.getTitle());
                        intent.putExtra("event_description", event.getDesc());
                        intent.putExtra("event_date", event.getDate());
                        intent.putExtra("event_time", event.getTime());
                        clearData();
                        context.startActivity(intent);
                    }
        });
    }

    @Override
    public int getItemCount() {
        return EventList.size();
    }

    public void clearData(){

        final int size = EventList.size();

        if(size > 0)
        {
            EventList.clear();
        }
        notifyItemRangeRemoved(0,size);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView desc;
        public TextView timestamp;
        public TextView date;
        public TextView time;
        public CircleImageView image;
        RelativeLayout parentLayout;

        public ViewHolder(View itemview, Context ctx) {
            super(itemview);

            context = ctx;
            title = itemview.findViewById(R.id.member_name);
            image = itemview.findViewById(R.id.image);
            timestamp = itemview.findViewById(R.id.image_timestamp);
            parentLayout = itemview.findViewById(R.id.parent_layout);

            itemview.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                }
            });
        }
    }
}
