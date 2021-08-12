package com.bless.studexp.ui.Subjects;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bless.studexp.models.Course;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/*
 * Subject view Model for providing data to the Subject Fragment to ensure smooth
 * user interface interaction
 */
public class SubjectsViewModel extends ViewModel {
    private MutableLiveData<String> mText;
    private MutableLiveData<ArrayList<Course>> mCourses;
    private MutableLiveData<ArrayList<Course>> mSubject;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDb;
    private DatabaseReference mRef;
    private ArrayList<Course> subData;
    private ArrayList<Course> courseList;
    private FirebaseFirestore db;
    public static final String TAG="SUBJECT FRAGMENT";
    public SubjectsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
        mAuth=FirebaseAuth.getInstance();
        mDb=FirebaseDatabase.getInstance();
        mRef=mDb.getReference();
        subData=new ArrayList<>();
        mSubject=new MutableLiveData<>();
        db = FirebaseFirestore.getInstance();
        mCourses= new MutableLiveData<>();
        courseList=new ArrayList<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<ArrayList<Course>> getCourseList(){
      db.collection("Courses")
              .get()
              .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                  @Override
                  public void onComplete(@NonNull Task<QuerySnapshot> task) {
                      if (!task.isSuccessful()){
                          Log.d(TAG, "onComplete:There was an error retrieving the data");
                      }
                      else{
                          for(QueryDocumentSnapshot q:task.getResult()){
                              Course course=q.toObject(Course.class);
                              courseList.add(course);
                          }
                          mCourses.setValue(courseList);
                      }
                  }
              });
        return  mCourses;
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