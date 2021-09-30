package com.bless.studexp;

import static com.bless.studexp.ui.Groups.GroupFragment.GROUP_FRAGMENT;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bless.studexp.Utils.ImageManager;
import com.bless.studexp.Utils.RequiredVariables;
import com.bless.studexp.databinding.ActivityGroupChatBinding;
import com.bless.studexp.models.Group;
import com.bless.studexp.models.Message;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class GroupChatActivity extends AppCompatActivity {
    private Group group;
    private ActivityGroupChatBinding binding;
    private String type = "text_message";
    private FirebaseDatabase mDb;
    private DatabaseReference mRef;
    private String imageUrl = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        Intent intent = getIntent();
        group = (Group) intent.getSerializableExtra(GROUP_FRAGMENT);
        Glide.with(this).load(group.getGroupIcon()).into(binding.groupIcon);
        binding.groupName.setText(group.getName());
        init();
        binding.takePhoto.setOnClickListener(view -> {
            Intent canIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(canIntent, RequiredVariables.picId);
        });
        binding.openGallery.setOnClickListener(view -> {
            Intent stIntent= new Intent();
            stIntent.setType("image/*");
        });

    }

    private void init() {
        mDb = FirebaseDatabase.getInstance();
        mRef = mDb.getReference();
    }

    public void sentMessage(View view) {
        if (binding.textMessage.getText().toString().equals(" ")) {
            Toast.makeText(this, "Sorry Cannot Send Empty message", Toast.LENGTH_SHORT).show();
        } else {
            String text = binding.textMessage.getText().toString().trim();
            long msgTime = System.currentTimeMillis();
            Message message = new Message(type, "", RequiredVariables.userId, text, msgTime, "", "", "");
            mRef.child("GroupMessages").child(group.getId()).push()
                    .setValue(message).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    binding.textMessage.setText("");
                    DatabaseReference tRef=FirebaseDatabase.getInstance()
                            .getReference(String.valueOf(R.string.users)).child(RequiredVariables.userId)
                            .child("lActiveness");
                    tRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int lActiveness= (int) snapshot.getValue();
                            lActiveness=lActiveness+1;
                            tRef.setValue(lActiveness);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    binding.textMessage.setText("");
                    Toast.makeText(GroupChatActivity.this, "error sending message", Toast.LENGTH_SHORT).show();
                    Log.d("GroupChatActivity", "sentMessage: " + task.getException());
                }
            });
        }
    }

    public void OpenContentCard(View view) {
        if (binding.contentCard.getVisibility() == View.VISIBLE) {
            binding.contentCard.setVisibility(View.GONE);
        } else {
            binding.contentCard.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RequiredVariables.picId){
            Bitmap bm = (Bitmap) data.getExtras().get("data");
           uploadBitmap(bm);
    }
        else if(requestCode==RequiredVariables.st_picId){
            Bitmap bm1 = (Bitmap) data.getExtras().get("data");
          uploadBitmap(bm1);
        }
}
private void uploadBitmap(Bitmap bm){
    Toast.makeText(this, bm.getWidth()+"height:"+bm.getHeight(), Toast.LENGTH_SHORT).show();
    bm= ImageManager.reduceBitmapSize(bm,1000000);
    byte[] imgByte=ImageManager.getByteFromBitMap(bm);
    StorageReference storageReference= FirebaseStorage.getInstance().getReference("Group_Images")
            .child(group.getId()).child(String.valueOf(System.currentTimeMillis()));
    UploadTask uploadTask;
    long timeNow= System.currentTimeMillis();
    uploadTask=storageReference.putBytes(imgByte);
    uploadTask.addOnSuccessListener(GroupChatActivity.this, taskSnapshot -> {
        Task<Uri> filePath = taskSnapshot.getStorage().getDownloadUrl();
        filePath.addOnCompleteListener(task -> {
            imageUrl = task.getResult().toString();
            Message message = new Message(RequiredVariables.image_type, "", RequiredVariables.userId, "", timeNow, "", imageUrl, "");
            mRef.child("GroupMessages").child(group.getId()).push()
                    .setValue(message).addOnCompleteListener(task1 -> {
                if (!task1.isSuccessful()) {
                    Toast.makeText(GroupChatActivity.this, "error sending Message", Toast.LENGTH_SHORT).show();
                }
                else{
                    DatabaseReference tRef=FirebaseDatabase.getInstance()
                            .getReference(String.valueOf(R.string.users)).child(RequiredVariables.userId)
                            .child("lActiveness");
                    tRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    int lActiveness= (int) snapshot.getValue();
                                    lActiveness=lActiveness+1;
                                    tRef.setValue(lActiveness);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                }
            });
        });

    });
}
}
