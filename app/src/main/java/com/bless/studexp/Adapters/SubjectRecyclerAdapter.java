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
import com.bless.studexp.databinding.SubjectXxmlBinding;
import com.bless.studexp.models.Course;
import com.bumptech.glide.Glide;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SubjectRecyclerAdapter extends  RecyclerView.Adapter<SubjectRecyclerAdapter.ViewHolder>{
    private final ArrayList<Course> courses;
    private  final Context context;
    private OnCourseListener onCourseListener;

    public SubjectRecyclerAdapter(Context context,ArrayList<Course> courses, OnCourseListener onCourseListener) {
        this.courses = courses;
        this.context = context;
        this.onCourseListener=onCourseListener;

    }

    @NonNull
    @Override
    public SubjectRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.subject_xxml,parent,false),onCourseListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectRecyclerAdapter.ViewHolder holder, int position) {
        Course course=courses.get(position);
        holder.txtDescription.setText(course.getLevel());
        Glide.with(context).load(course.getImageUrl()).into(holder.imgDisp);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imgDisp;
        private TextView txtDescription;

        OnCourseListener onCourseListener;
        public ViewHolder(@NonNull View itemView,OnCourseListener onCourseListener) {
            super(itemView);
            this.onCourseListener=onCourseListener;
            imgDisp=itemView.findViewById(R.id.image_subject);
            txtDescription=itemView.findViewById(R.id.subject_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
        onCourseListener.onItemClicked(getAdapterPosition(),courses.get(getAdapterPosition()));
        }
    }
    public interface  OnCourseListener{
        void onItemClicked(int position,Course course);
    }
}