package com.bless.studexp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bless.studexp.ActivityViewModels.CourseDetailsViewModel;
import com.bless.studexp.databinding.ActivityCourseDetailsBinding;
import com.bless.studexp.models.Course;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CourseDetailsActivity extends AppCompatActivity {
    private ActivityCourseDetailsBinding binding;
    private CourseDetailsViewModel vm;
    private DatabaseReference mRef, mRef2;
    private String user_key;
    public static final String TAG = "CourseDetailsActivity";
    Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(this).get(CourseDetailsViewModel.class);
        binding = ActivityCourseDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        course = (Course) intent.getSerializableExtra(getString(R.string.displaying_course));
        Toast.makeText(this, course.getDescription(), Toast.LENGTH_SHORT).show();
        vm.getCourse(course).observe(this, course -> {
            binding.subjectTitle.setText(course.getName());
            binding.subectCategory.setText(course.getCategory());
            binding.subjectDescription.setText(course.getDescription());
            Glide.with(CourseDetailsActivity.this).load(course.getImageUrl()).into(binding.subjectImage);
        });
        init();
        binding.btnJoin.setOnClickListener(view -> addUserToTheGroup());
    }

    public void init() {
        mRef = FirebaseDatabase.getInstance().getReference();
        mRef2 = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        user_key = auth.getCurrentUser().getUid();
    }

    private void addUserToTheGroup() {
        mRef.child("Groups").child(course.getKey()).child("members").setValue(user_key)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(CourseDetailsActivity.this, "Error joining group", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onComplete: unable o add user id to group node");
                    } else {
                        mRef2.child(String.valueOf(R.string.users)).child(user_key).child("groupId").setValue(course.getKey())
                                .addOnCompleteListener(task1 -> {
                                    if (!task1.isSuccessful()) {
                                        Log.d(TAG, "onComplete: unable to add groupId to used node ");
                                    } else {
                                        Toast.makeText(CourseDetailsActivity.this, "Successfully Joined", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(CourseDetailsActivity.this, CourseGroupChat.class));
                                    }
                                });

                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding=null;
    }
}