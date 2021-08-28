package com.bless.studexp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bless.studexp.ActivityViewModels.CourseDetailsViewModel;
import com.bless.studexp.databinding.ActivityCourseDetailsBinding;
import com.bless.studexp.models.Course;
import com.bless.studexp.models.Group;
import com.bless.studexp.models.User;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CourseDetailsActivity extends AppCompatActivity {
    private ActivityCourseDetailsBinding binding;
    private CourseDetailsViewModel vm;
    private DatabaseReference mRef, mRef2;
    private String user_key;
    public static final String TAG = "CourseDetailsActivity";
     private Course course;
    private Integer numOfGroups=0;
    private Integer numMembers=0;


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
        getCounts();
        binding.btnJoin.setOnClickListener(view -> addUserToTheGroup());
    }

    public void init() {
        mRef = FirebaseDatabase.getInstance().getReference();
        mRef2 = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        user_key = auth.getCurrentUser().getUid();
    }
    private void getCounts() {
        DatabaseReference mReef= FirebaseDatabase.getInstance().getReference("Users");
        mReef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()) {
                    if (ds.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        User u = ds.getValue(User.class);
                        Log.d(TAG, "onDataChange: Users "+u.getGroupId().size());
                        numOfGroups=u.getGroupId().size();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference mRee=FirebaseDatabase.getInstance().getReference("Groups");
        mRee.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()) {
                    Log.d(TAG, "onDataChange: Groups "+course.getKey());
                    if(ds.getKey().equals(course.getKey())){
                        Group g=ds.getValue(Group.class);
                        numMembers=g.getMembers().size();
                        Log.d(TAG, "onDataChange: Groups "+numMembers);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private void addUserToTheGroup() {
        mRef.child("Groups").child(course.getKey()).child("members").child(String.valueOf(numMembers)).setValue(user_key)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(CourseDetailsActivity.this, "Error joining group", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onComplete: unable o add user id to group node");
                    } else {
                        mRef2.child("Users").child(user_key).child("groupId").child(String.valueOf(numOfGroups)).setValue(course.getKey())
                                .addOnCompleteListener(task1 -> {
                                    if (!task1.isSuccessful()) {
                                        Log.d(TAG, "onComplete: unable to add groupId to used node ");
                                    } else {
                                        Toast.makeText(CourseDetailsActivity.this, "Successfully Joined", Toast.LENGTH_SHORT).show();
                                     finish();
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