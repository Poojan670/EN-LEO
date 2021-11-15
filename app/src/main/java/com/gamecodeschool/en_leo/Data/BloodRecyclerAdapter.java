package com.gamecodeschool.en_leo.Data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gamecodeschool.en_leo.Model.UserProfile;
import com.gamecodeschool.en_leo.R;

import org.w3c.dom.Text;

import java.util.List;

public class BloodRecyclerAdapter extends RecyclerView.Adapter<BloodRecyclerAdapter.ViewHolder> {

    public Context context;
    private List<UserProfile> UserList;

    public BloodRecyclerAdapter(Context context, List<UserProfile> userList){
        this.context = context;
        this.UserList = userList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BloodRecyclerAdapter.ViewHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_bloodrow, parent, false);

        viewHolder = new ViewHolder(view, context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final UserProfile userProfile = UserList.get(position);

        holder.profilename.setText(userProfile.getUserFullName());
        holder.bloodgroup.setText(userProfile.getUserBloodGroup());
        holder.call.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("PrivateApi")
            @Override
            public void onClick(View v) {
                Intent intent = null;
                try {
                    intent = new Intent(context, Class.forName(Intent.ACTION_CALL));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                assert intent != null;
                intent.setData(Uri.parse("tel:" + userProfile.getUserContact()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return UserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView profilename;
        public TextView bloodgroup;
        public ImageButton call;
        RelativeLayout parentlayout;

        public ViewHolder(View itemView, Context ctx) {
            super(itemView);
            context = ctx;
            profilename = itemView.findViewById(R.id.member_name);
            bloodgroup = itemView.findViewById(R.id.member_bloodgroup);
            call = itemView.findViewById(R.id.callButton);
            parentlayout = itemView.findViewById(R.id.parent_layout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
