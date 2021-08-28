package com.bless.studexp.ui.Subjects;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bless.studexp.Adapters.SubjectRecyclerAdapter;
import com.bless.studexp.CourseDetailsActivity;
import com.bless.studexp.R;
import com.bless.studexp.databinding.FragmentSubjectsBinding;
import com.bless.studexp.models.Course;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubjectsFragment extends Fragment implements SubjectRecyclerAdapter.OnCourseListener {

    private FragmentSubjectsBinding binding;
    private FirebaseFirestore db;
    private FirebaseDatabase mDb;
    private DatabaseReference mRef , mRef2;;
    private ArrayList<Course> course2;
    private Course displayingCourse;
    private FirebaseAuth mAuth;
    private String user_key;
    public static final String TAG = "SUBJECT_FRAGMENT";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SubjectsViewModel subjectsViewModel = new ViewModelProvider(this).get(SubjectsViewModel.class);
        binding = FragmentSubjectsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        init();
        subjectsViewModel.getCourseList().observe(getViewLifecycleOwner(), courses -> {
            course2 = courses;
            displayingCourse = courses.get(0);
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
          //  getCounts();
        });
        binding.testUpload.setOnClickListener(view -> {
            db = FirebaseFirestore.getInstance();
            AddDataToCloudStore(new Course("Biology",
                    "Science",
                    "The Study of life",
                    "https://image.shutterstock.com/z/stock-photo-healthy-weaned-piglet-in-farrowing-unit-1311114293.jpg",
                    "Secondary School",
                    "this has to be modified "
            ));
        });
        binding.relativeLayout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CourseDetailsActivity.class);
            intent.putExtra(getString(R.string.displaying_course), displayingCourse);
            startActivity(intent);
        });

        return root;
    }



    private void init() {
        binding.shimmerFrameLayout.startShimmer();
        binding.shimmerImageSelected.startShimmer();
        course2 = new ArrayList<>();
        displayingCourse = new Course();
        mDb = FirebaseDatabase.getInstance();
        mRef = mDb.getReference();
        mAuth = FirebaseAuth.getInstance();
        user_key = mAuth.getUid();
        mRef2 = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void displayCourses(ArrayList<Course> courses) {
        binding.recyclerView.setAdapter(new SubjectRecyclerAdapter(getActivity(), courses, this));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
      //  getCounts();
    }

    @Override
    public void onItemClicked(int position, Course course) {
        displayMainImage(course);
        Log.d(TAG, "onItemClicked: " + course.getCategory());
        displayingCourse = course;

    }

    private void displayMainImage(Course course) {
        String imageUrl = course.getImageUrl();
        Glide.with(getContext()).load(imageUrl).into(binding.imageBroad);
        binding.subjectDesc.setText(course.getDescription());
        binding.subjectTe.setText(course.getName());

    }

    private void AddDataToCloudStore(Course course) {
        Map<String, String> courseUpdate = new HashMap<>();
        courseUpdate.put("name", course.getName());
        courseUpdate.put("category", course.getCategory());
        courseUpdate.put("description", course.getDescription());
        courseUpdate.put("imageUrl", course.getImageUrl());
        courseUpdate.put("level", course.getLevel());

        db.collection("Courses")
                .add(courseUpdate)
                .addOnSuccessListener(documentReference -> Toast.makeText(getActivity(), "Successfully uploaded", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(ze -> Log.d(TAG, "onFailure: Load Failure"));

    }
}