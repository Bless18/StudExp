package com.bless.studexp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bless.studexp.AvctivityViewModels.CourseDetailsViewModel;
import com.bless.studexp.databinding.ActivityCourseDetailsBinding;
import com.bless.studexp.models.Course;
import com.bumptech.glide.Glide;

public class CourseDetailsActivity extends AppCompatActivity {
   private ActivityCourseDetailsBinding binding;
   private CourseDetailsViewModel vm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm=new ViewModelProvider(this).get(CourseDetailsViewModel.class);
        binding=ActivityCourseDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent= getIntent();
        Course course = (Course) intent.getSerializableExtra(getString(R.string.displaying_course));
        Toast.makeText(this, course.getDescription(), Toast.LENGTH_SHORT).show();

        vm.getCourse(course).observe(this, new Observer<Course>() {
            @Override
            public void onChanged(Course course) {
                binding.subjectTitle.setText(course.getName());
                binding.subectCategory.setText(course.getCategory());
                binding.subjectDescription.setText(course.getDescription());
                Glide.with(CourseDetailsActivity.this).load(course.getImageUrl()).into(binding.subjectImage);
            }
        });
    }
}