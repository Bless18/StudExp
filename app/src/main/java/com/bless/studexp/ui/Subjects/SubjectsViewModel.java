package com.bless.studexp.ui.Subjects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bless.studexp.models.Course;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

/*
 * Subject view Model for providing data to the Subject Fragment to ensure smooth
 * user interface interaction
 */
public class SubjectsViewModel extends ViewModel {
    private MutableLiveData<String> mText;
    private MutableLiveData<ArrayList<Course>> mSubject;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDb;
    private DatabaseReference mRef;
    private ArrayList<Course> subData;
    public SubjectsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
        mAuth=FirebaseAuth.getInstance();
        mDb=FirebaseDatabase.getInstance();
        mRef=mDb.getReference();
        subData=new ArrayList<>();
        mSubject=new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }

    /**
     * this is the life data method which helps in the implementation of the mvvm model of android coding
     * which prevent data from being fetched from the repository at every moment of need
     * here  a live Data  is returned to ensure that the fetched data is always refreshed with the user interface. the method
     * is paired with the firebase CHILDEVENTLISTENER which will help to get the slightes data change from the firebase
     * repository
     * @return LiveData
     */
    public LiveData<ArrayList<Course>>  getSubjectList(){
        mRef.child("Subject").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Course course=snapshot.getValue(Course.class);
                course.setKey(snapshot.getKey());
                subData.add(course);
                mSubject.setValue(subData);
            }

            /**
             *
             * @param snapshot
             * @param previousChildName
             */
            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Course courses= snapshot.getValue(Course.class);
                courses.setKey(snapshot.getKey());
                ArrayList<Course> newCourse=new ArrayList<>();
                for(Course course:subData){
                    if(course.getKey().equals(courses)){
                        newCourse.add(courses);
                    }
                    else {
                        newCourse.add(course);
                    }
                }
                subData=newCourse;
                mSubject.setValue(subData);
            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        return  mSubject;
    }
}