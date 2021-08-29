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
import com.bless.studexp.Utils.OnGroupClickListener;
import com.bless.studexp.models.Group;
import com.bumptech.glide.Glide;

import java.util.List;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.GroupViewHolder> {
    private  final Context context;
    private  final List<Group> groups;
    private OnGroupClickListener onGroupListener;

    public GroupListAdapter(Context context, List<Group> groups,OnGroupClickListener onGroupListener) {
        this.context = context;
        this.groups = groups;
        this.onGroupListener=onGroupListener;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new GroupViewHolder(LayoutInflater.from(context).inflate(R.layout.group_display,parent,false),onGroupListener);
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

    public class GroupViewHolder extends RecyclerView.ViewHolder implements   View.OnClickListener {
        private ImageView profileImage;
        private TextView profileName;;
        OnGroupClickListener onGroupListener;
        public GroupViewHolder(@NonNull View itemView,OnGroupClickListener onGroupListener) {
            super(itemView);
            this.onGroupListener=onGroupListener;
            profileImage=itemView.findViewById(R.id.profile_image);
            profileName=itemView.findViewById(R.id.profile_name);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            onGroupListener.onItemClicked(groups.get(getAdapterPosition()));
        }
    }

}
