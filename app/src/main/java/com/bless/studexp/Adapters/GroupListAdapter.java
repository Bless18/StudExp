package com.bless.studexp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bless.studexp.R;
import com.bless.studexp.models.Group;
import com.bumptech.glide.Glide;

import java.util.List;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.GroupViewHolder> {
    private  final Context context;
    private  final List<Group> groups;

    public GroupListAdapter(Context contextt, List<Group> groups) {
        this.context = contextt;
        this.groups = groups;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new GroupViewHolder(LayoutInflater.from(context).inflate(R.layout.group_display,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        Group group=groups.get(position);
        Glide.with(context).load(group.getGroupIcon()).into(holder.profileImage);
        holder.profileName.setText(group.getName());
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        private ImageView profileImage;
        private TextView profileName;;
        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage=itemView.findViewById(R.id.profile_image);
            profileName=itemView.findViewById(R.id.profile_name);
        }
    }
}
