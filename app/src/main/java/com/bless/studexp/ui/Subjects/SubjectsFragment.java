package com.bless.studexp.ui.Subjects;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
//         mSubjectsViewModel.getSubjectList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Course>>() {
//             @Override
//             public void onChanged(ArrayList<Course> courses) {
//
//
//             }
//         });
         mSubjectsViewModel.getCourseList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Course>>() {
             @Override
             public void onChanged(ArrayList<Course> courses) {
                   displayCourses(courses);
                 displayMainImage(courses.get(0));
                 Toast.makeText(getActivity(), courses.get(0).getName(), Toast.LENGTH_SHORT).show();
                 Toast.makeText(getActivity(), courses.get(0).getImageUrl(), Toast.LENGTH_SHORT).show();
                 binding.subjectTe.setText(courses.get(0).getName());
                 binding.subjectDesc.setText(courses.get(0).getDescription());
                 binding.shimmerFrameLayout.stopShimmer();
                 binding.shimmerFrameLayout.setVisibility(View.GONE);
                 binding.shimmerImageSelected.stopShimmer();
                 binding.shimmerImageSelected.setVisibility(View.GONE);
                 binding.relativeLayout.setVisibility(View.VISIBLE);
             }
         });
         binding.testUpload.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 db = FirebaseFirestore.getInstance();
                 AddDataToCloudStore(new Course("Phylosophy",
                         "Art",
                         "the study of theology",
                         "https://ichef.bbci.co.uk/news/976/cpsprodpb/A72C/production/_118569724_gettyimages-1130153707.jpg0",
                         "University"
                 ));
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
        binding.subjectDesc.setText(course.getDescription());
        binding.subjectTe.setText(course.getName());
    }
    private void AddDataToCloudStore(Course course){
        Map<String,String> courseUpdate=new HashMap<>();
        courseUpdate.put("name",course.getName());
        courseUpdate.put("category",course.getCategory());
        courseUpdate.put("description",course.getDescription());
        courseUpdate.put("imageUrl",course.getImageUrl());
        courseUpdate.put("level",course.getLevel());

        db.collection("Courses")
                .add(courseUpdate)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(), "Successfully uploaded", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Load Failure");
                    }
                });
        
    }
}