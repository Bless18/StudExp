package com.bless.studexp.ui.Groups;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bless.studexp.models.Group;
import com.bless.studexp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GroupViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<Group>> groups;
    private DatabaseReference mRef;
    private final String curUser;
    public static final String TAG = "GroupViewModel";
    private List<String> userGroups;
    private  List<User> mUsers;
    private List<Group> groupList;

    public GroupViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
        groups = new MutableLiveData<>();
        mRef = FirebaseDatabase.getInstance().getReference();
        curUser = FirebaseAuth.getInstance().getUid();
        userGroups = new ArrayList<>();
        mUsers=new ArrayList<>();
        groupList= new ArrayList<>();
    }

    public LiveData<List<Group>> getGroups() {

        Query query=mRef.child("Users");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange: "+snapshot.getValue());
                for (DataSnapshot ds: snapshot.getChildren()) {
                    if (ds.getKey().equals(curUser)){
                        User u =ds.getValue(User.class);
                        Log.d(TAG, "onDataChange: "+ds.getValue());
                        userGroups.addAll(u.getGroupId());
                    }
                }

                for (String key:userGroups) {
                   mRef.child("Groups").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds: snapshot.getChildren() ) {

                                  Group g = ds.getValue(Group.class);
                                    Log.d(TAG, "onDataChange: Toast"+g);
                                 if(g.getId().equals(key)){
                                    groupList.add(g);
                               }
                                 groups.setValue(groupList);
                            }

                         //   groupList.clear();
                            Log.d(TAG, "onDataChange: test"+groups);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
     //   Log.d(TAG, "getGroups: VM"+groupList);
        return groups;
    }

    public LiveData<String> getText() {
        return mText;
    }
}