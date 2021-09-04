package com.bless.studexp;

import static com.bless.studexp.ui.Groups.GroupFragment.GROUP_FRAGMENT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bless.studexp.databinding.ActivityGroupChatBinding;
import com.bless.studexp.models.Course;
import com.bless.studexp.models.Group;
import com.bumptech.glide.Glide;

public class GroupChatActivity extends AppCompatActivity {
    private Group group;
    private ActivityGroupChatBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        Intent intent =getIntent();
        group= (Group) intent.getSerializableExtra(GROUP_FRAGMENT);
        Glide.with(this).load(group.getGroupIcon()).into(binding.groupIcon);
        binding.groupName.setText(group.getName());

    }

    public void sentMessage(View view) {
    }

    public void OpenContentCard(View view) {
        if (binding.contentCard.getVisibility()==View.VISIBLE){
            binding.contentCard.setVisibility(View.GONE);
        }
        else {
            binding.contentCard.setVisibility(View.VISIBLE);
        }

    }
}