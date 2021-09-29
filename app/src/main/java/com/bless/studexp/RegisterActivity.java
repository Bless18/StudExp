package com.bless.studexp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bless.studexp.Utils.ImageManager;
import com.bless.studexp.Utils.OnEmailCheckListener;
import com.bless.studexp.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {
    private ImageView img;
    private EditText edt_name,edt_level,edt_town,edt_email,edt_password;
    private com.google.android.gms.common.internal.SignInButtonImpl btn_sign_up;
    private TextView tvChange;
    private Uri imgUrl;
    private String img_url;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDb;
    private DatabaseReference mRef;
    private FirebaseStorage mFS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        String imageUrl=null;
        init();
        tvChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");

            }
        });
        btn_sign_up.setOnClickListener(v -> createNewUser());
    }
    private void init(){
        img=findViewById(R.id.login_background);
        edt_email=findViewById(R.id.edt_email);
        edt_name=findViewById(R.id.edt_name);
        edt_level=findViewById(R.id.edt_level);
        edt_town=findViewById(R.id.edt_town);
        edt_password=findViewById(R.id.edt_password);
        btn_sign_up=findViewById(R.id.btn_register);
        tvChange=findViewById(R.id.upload);
        mFS=FirebaseStorage.getInstance();
        mAuth=FirebaseAuth.getInstance();
        mDb=FirebaseDatabase.getInstance();
        mRef=mDb.getReference().child(getString(R.string.users));

    }
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    // Handle the returned Uri
                    if(result!=null){
                        img.setImageURI(result);
                        imgUrl=result;
                        uploadPhoto();
                    }
                }
            });
    private void uploadPhoto() {
        if(imgUrl!=null){
            Toast.makeText(this, "there is an image", Toast.LENGTH_SHORT).show();
            Bitmap bm = null;
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),imgUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap bt= ImageManager.reduceBitmapSize(bm,1000000);
            byte[] imgByte=ImageManager.getByteFromBitMap(bt);
            StorageReference storageReference=mFS.getReference().child("images/"+ UUID.randomUUID().toString());
            UploadTask uploadTask;
            uploadTask=storageReference.putBytes(imgByte);
            uploadTask.addOnSuccessListener(RegisterActivity.this, taskSnapshot -> {
                Task<Uri> filePath=taskSnapshot.getStorage().getDownloadUrl();
                filePath.addOnCompleteListener(task -> img_url=task.getResult().toString());
            }).addOnFailureListener(e -> {

            });
        }
        else{
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }
    }
    private void createNewUser() {
        String name=edt_name.getText().toString();
        String email=edt_email.getText().toString();
        String town=edt_town.getText().toString();
        String level=edt_level.getText().toString();
        String password=edt_password.getText().toString();
        if(!name.equals("")&&!email.equals("")&&!town.equals("")&&!level.equals("")&&!password.equals("")){
            if(password.length()<6){
                Toast.makeText(this, "password too Short", Toast.LENGTH_SHORT).show();
            }
            else{
                isCheckEmail(email, isRegistered -> {
                    if (isRegistered){
                        Toast.makeText(RegisterActivity.this, "Account with such Email Already exist", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        List<String> arr = new ArrayList<>();
                        arr.add("Useless 1");
                        arr.add("useless 2");
                        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this, task -> {
                            if(task.isSuccessful()){
                                mRef.child(mAuth.getUid())
                                        .setValue(new User(name,
                                                level
                                                ,email
                                                ,null,
                                                0,
                                                town,
                                                img_url))
                                        .addOnCompleteListener(task1 -> {
                                            if(!task1.isSuccessful()){
                                                Toast.makeText(RegisterActivity.this, "An error occurred\n please try again", Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                Toast.makeText(RegisterActivity.this, "Successful Registration", Toast.LENGTH_SHORT).show();
                                                DatabaseReference mRee = FirebaseDatabase.getInstance().getReference("User");
                                                mRee.child(FirebaseAuth.getInstance().getUid()).child("groupId")
                                                        .child(String.valueOf(0))
                                                        .setValue("General Welcome Group").addOnCompleteListener(task2 -> {
                                                            if(task2.isSuccessful()){
                                                                startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
                                                                finish();
                                                            }
                                                            else{
                                                                Toast.makeText(getApplicationContext(), "An Unexpected error occurred", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });

                                            }
                                        });
                            }
                        });
                    }
                });

            }
        }
        else{
            Toast.makeText(this, "All the fields must be filled", Toast.LENGTH_SHORT).show();
        }
    }
    public void isCheckEmail(final String email,final OnEmailCheckListener listener){
        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(task -> {
            boolean check = !task.getResult().getSignInMethods().isEmpty();
            listener.onSuccess(check);
        });
    }
    public void goToLoginAct(View view) {
        startActivity(new Intent(this,LoginActivity.class));
    }
}