package com.bless.studexp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail,edtPassword;
    private Button btnSignIn;
    private TextView tvForgotPassword;
    FirebaseAuth mAuth;
    FirebaseDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser()!=null){
            Toast.makeText(this, "id"+mAuth.getCurrentUser().getUid(), Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,HomeActivity.class));
            finish();
        }
        else {
            init();
            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signIn();
                }
            });
            tvForgotPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayDialog();
                }
            });
        }

    }

    private void displayDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enter Email To receive Reset link");
        final LinearLayout rel=new LinearLayout(this);
        rel.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 40));
        rel.setPadding(30,0,30,0);
        final EditText emailInput=new EditText(this);
        emailInput.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rel.addView(emailInput);
        builder.setView(rel);
        builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(!emailInput.getText().toString().equals("")){
                    mAuth.sendPasswordResetEmail(emailInput.getText().toString());
                    Toast.makeText(LoginActivity.this, "Check Email for password retrieval", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "invalid email", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void goToRegistrationAct(View view) {
        startActivity(new Intent(this,RegisterActivity.class));
    }
    private void init(){
        edtEmail=findViewById(R.id.edt_email);
        edtPassword=findViewById(R.id.edt_password);
        btnSignIn=findViewById(R.id.btn_signIn);
        tvForgotPassword=findViewById(R.id.tvForgotPassword);

        mDb=FirebaseDatabase.getInstance();
    }
    private void signIn() {
        String email,password;
        email=edtEmail.getText().toString();
        password=edtPassword.getText().toString();
        if(email.equals("")&&password.equals("")){
            Toast.makeText(this, "None of the fields can be empty", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "invalid credentials", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "successful login", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                                finish();
                            }
                        }
                    });
        }
    }

}