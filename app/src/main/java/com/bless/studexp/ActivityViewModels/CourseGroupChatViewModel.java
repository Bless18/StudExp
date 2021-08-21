package com.bless.studexp.ActivityViewModels;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bless.studexp.R;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseGroupChatViewModel extends ViewModel {
    private MutableLiveData<List<Group>> groups;
    private DatabaseReference mRef;
    private List<Group> mGroups;
    private List<String> groupId;


    public CourseGroupChatViewModel(){
        groups= new MutableLiveData<>();
        mRef= FirebaseDatabase.getInstance().getReference();
       // mRef2= FirebaseDatabase.getInstance().getReference();
        mGroups=new ArrayList<>();
        groupId= new ArrayList<>();

    }
    public LiveData<List<Group>> showGroups(){
       Query query= mRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
       query.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               if (snapshot.exists()){
                   User u= snapshot.getValue(User.class);
                   groupId.addAll(u.getGroupId());
                   Query navigateGroup=mRef.child(String.valueOf(R.string.users));
                   for(String ids:groupId){
                       navigateGroup.addValueEventListener(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot snapshot) {
                               if (snapshot.getKey().equals(ids)){
                                   mGroups.add(snapshot.getValue(Group.class));
                               }
                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError error) {

                           }
                       }) ;
                   }
               }

           }
           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
        return  groups;
    }
}
