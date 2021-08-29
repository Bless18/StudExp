package com.bless.studexp.ui.Groups;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bless.studexp.Adapters.GroupListAdapter;
import com.bless.studexp.GroupChatActivity;
import com.bless.studexp.Utils.OnGroupClickListener;
import com.bless.studexp.databinding.FragmentGroupsBinding;
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


public class GroupFragment extends Fragment implements OnGroupClickListener {

    public static final String TAG = "GroupFragment";
    private GroupViewModel mGroupViewModel;
    private FragmentGroupsBinding binding;
    private DatabaseReference mRef;
    private String curUser;
    private List<String> userGroups;
    private List<User> mUsers;
    private List<Group> groupList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mGroupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        binding = FragmentGroupsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.button.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            getActivity().finish();
        });

        init();
        getGroups();

        return root;
    }

    private void init() {
        mRef = FirebaseDatabase.getInstance().getReference();
        curUser = FirebaseAuth.getInstance().getUid();
        userGroups = new ArrayList<>();
        groupList = new ArrayList<>();
    }

    private void displayListGroups(List<Group> groups) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(new GroupListAdapter(getActivity(), groups,this));

    }

    public void getGroups() {
        Query query = mRef.child("Users");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange: " + snapshot.getValue());
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.getKey().equals(curUser)) {
                        User u = ds.getValue(User.class);
                        Log.d(TAG, "onDataChange: " + ds.getValue());
                        userGroups.addAll(u.getGroupId());
                    }
                }

                for (String key : userGroups) {
                    mRef.child("Groups").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                Group g = ds.getValue(Group.class);
                                Log.d(TAG, "onDataChange: Toast" + g);
                                if (g.getId().equals(key)) {
                                    groupList.add(g);
                                }
                                displayListGroups(groupList);
                            }
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
        ;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClicked(Group group) {
        startActivity(new Intent(getActivity(), GroupChatActivity.class));
    }
}