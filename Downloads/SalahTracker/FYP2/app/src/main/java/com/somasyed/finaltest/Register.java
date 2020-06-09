package com.somasyed.finaltest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import android.os.Bundle;

public class Register extends AppCompatActivity {
    private Button chooseImgBtn;
    private Bitmap profilePicture;
    private Uri filePath;
    private ImageView imageView;
    private final int PICK_IMAGE_REQUEST = 71;

    EditText username_su;
    EditText password_su;
    EditText name_su;
    Button signupBtn;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        username_su = findViewById(R.id.username_su);
        password_su = findViewById(R.id.password_su);
        name_su = findViewById(R.id.name_su);

        signupBtn = findViewById(R.id.signupBtn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        imageView = (ImageView) findViewById(R.id.imageView);
        chooseImgBtn = findViewById(R.id.chooseImgBtn);
        chooseImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
                uploadImageToFirebase();
            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"), PICK_IMAGE_REQUEST);
    }

    private void uploadImageToFirebase() {
        if(filePath != null)
        {
            try {
                profilePicture = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                profilePicture = null;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void registerUser() {
        final String username = username_su.getText().toString().trim();
        final String password = password_su.getText().toString().trim();

        if (username.isEmpty()) {
            username_su.setError("Email is required");
            username_su.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            username_su.setError("Please enter a valid email address");
            username_su.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            password_su.setError("Password is required");
            password_su.requestFocus();
            return;
        }

        if (password.length() < 6) {
            password_su.setError("Please enter a password of 6 or more characters");
            password_su.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("users");

                    mAuth.signInWithEmailAndPassword(username, password);
                    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    myRef.child(currentuser).child("name").setValue(name_su.getText().toString());
                    myRef.child(currentuser).child("email id").setValue(username_su.getText().toString());
                    myRef.child(currentuser).child("profile picture").setValue(profilePicture);

                    Toast.makeText(getApplicationContext(), "Sign-up successful. Logged in!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void onClickLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }
}
