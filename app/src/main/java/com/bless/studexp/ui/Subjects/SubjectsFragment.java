package com.bless.studexp.ui.Subjects;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.bless.studexp.Adapters.SubjectRecyclerAdapter;
import com.bless.studexp.databinding.FragmentSubjectsBinding;
import com.bless.studexp.models.Course;
import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class SubjectsFragment extends Fragment implements SubjectRecyclerAdapter.OnCourseListener {

    private SubjectsViewModel mSubjectsViewModel;
    private FragmentSubjectsBinding binding;
    private FirebaseFirestore db;
    public static final String TAG="SUBJECT_FRAGMENT";
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSubjectsViewModel = new ViewModelProvider(this).get(SubjectsViewModel.class);

        binding = FragmentSubjectsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.shimmerFrameLayout.startShimmer();
        binding.shimmerImageSelected.startShimmer();
         mSubjectsViewModel.getSubjectList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Course>>() {
             @Override
             public void onChanged(ArrayList<Course> courses) {
                 displayCourses(courses);
                 displayMainImage(courses.get(0));
                 binding.subjectTitle.setText(courses.get(0).getName());
                 binding.shimmerFrameLayout.stopShimmer();
                 binding.shimmerFrameLayout.setVisibility(View.GONE);
                 binding.shimmerImageSelected.stopShimmer();
                 binding.shimmerImageSelected.setVisibility(View.GONE);
                 db = FirebaseFirestore.getInstance();
             }
         });
            return  root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void displayCourses(ArrayList<Course> courses){
        binding.recyclerView.setAdapter(new SubjectRecyclerAdapter(getActivity(),courses,this));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
    }

    @Override
    public void onItemClicked(int position, Course course) {
        displayMainImage(course);
        Log.d(TAG, "onItemClicked: "+course.getCategory());
    }

    private void displayMainImage(Course course) {
        String imageUrl = course.getImageUrl();
        Glide.with(getContext()).load(imageUrl).into(binding.imageBroad);
    }
    private void AddDataToCloudStore(Course course){

    }
}