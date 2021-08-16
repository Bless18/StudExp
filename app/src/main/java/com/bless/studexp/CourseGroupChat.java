package com.bless.studexp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bless.studexp.databinding.ActivityCourseDetailsBinding;

public class CourseGroupChat extends AppCompatActivity {
     private ActivityCourseDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCourseDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}