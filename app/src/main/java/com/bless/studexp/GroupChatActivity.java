package com.bless.studexp;

import static com.bless.studexp.ui.Groups.GroupFragment.GROUP_FRAGMENT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bless.studexp.databinding.ActivityGroupChatBinding;
import com.bless.studexp.models.Course;
import com.bless.studexp.models.Group;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GroupChatActivity extends AppCompatActivity {
    private Group group;
    private ActivityGroupChatBinding binding;
    private  String type="text_message";
    private FirebaseDatabase mDb;
    private DatabaseReference mRef;

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
        init();
        

    }
    private void init(){
        mDb=FirebaseDatabase.getInstance();
        mRef=mDb.getReference();
    }
    public void sentMessage(View view) {
        if(binding.textMessage.getText().equals(null)){
            }
        else{
            String text=binding.textMessage.getText().toString().trim();
            String senderId= FirebaseAuth.getInstance().getCurrentUser().getUid();
            SimpleDateFormat formatDate = new SimpleDateFormat("hh:mm a");
            String sendTime=formatDate.toString();

        }
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