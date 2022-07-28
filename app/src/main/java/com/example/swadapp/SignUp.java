package com.example.swadapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.swadapp.Models.User;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUp extends AppCompatActivity {
    CircleImageView image;
    Button register;
    ImageView signup;
    TextView view, txt;
    EditText username, password, email;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;   // whenever you will click on signup it will load
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        register = findViewById(R.id.btnregister);
        view = findViewById(R.id.txtalready);
        username=findViewById(R.id.username_of_signup);
        password=findViewById(R.id.editTextTextPassword);
        email = findViewById(R.id.email);


        progressDialog = new ProgressDialog(SignUp.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We are creating You Account");


        // taking image from camera and showing on the image section


        image = findViewById(R.id.profile_image);
        txt = findViewById(R.id.upload_img_txt);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 /*
                     Refer to : "https://github.com/Dhaval2404/ImagePicker"  for various customizations
                  */
                ImagePicker.with(SignUp.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, SignIn.class);
                startActivity(intent);
                finish();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        signup = findViewById(R.id.gmail_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

        // Email Authentication Work Below

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if((username.getText().toString()).length() == 0  || (password.getText().toString()).length()==0 || (email.getText().toString()).length()==0){
                    new AlertDialog.Builder(SignUp.this)
                            .setIcon(R.drawable.ic_baseline_warning_24)
                            .setTitle("Alert")
                            .setMessage("Name, Email Address and Password Can not be left Empty")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .show();
                }
                else {

                    progressDialog.show();    // jb user register pr click krega tb ye progress dialog active ho jaaega
                    mAuth.createUserWithEmailAndPassword(
                            email.getText().toString(),
                            password.getText().toString()
                    ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();  // jb user ka data store ho jaaega to progress dialog apne aap band ho jaaega

                            if (task.isSuccessful()) {

                                User user = new User(username.getText().toString(), email.getText().toString(), password.getText().toString());
                                String id = task.getResult().getUser().getUid();
                                database.getReference().child("Users").child(id).setValue(user);

                                Toast.makeText(SignUp.this, "You have been Successfully Registered", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(SignUp.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                new AlertDialog.Builder(SignUp.this)
                                        .setIcon(R.drawable.ic_baseline_warning_24)
                                        .setTitle("Warning")
                                        .setMessage(task.getException().getMessage())
                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();

                                            }
                                        })
                                        .show();
                            }
                        }
                    });
                }
            }
        });

    }


    // Signin using gmail directly, work below

    private static final int REQ_ONE_TAP = 3;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;
    private void signUp(){
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, REQ_ONE_TAP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = data.getData();
        image.setImageURI(uri);

        if(requestCode == REQ_ONE_TAP){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign in was successful, authenticate with firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "FirebaseAuthWithGoogle:"+account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            }catch (ApiException e){
                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }


    private void firebaseAuthWithGoogle(String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            // Sign in success, updtae UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);

                            User user1 = new User();
                            user1.setUserId(user.getUid());
                            user1.setUsername(user.getDisplayName());
                            user1.setProfilepic(user.getPhotoUrl().toString());
                            database.getReference().child("Users").child(user.getUid()).setValue(user1);

                            Toast.makeText(SignUp.this, "Successfully Signed in with Google", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUp.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        }
                        else{
                            // If sign in fails, display a message to the user
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }   //updateUI(null);
                    }
                });
    }
}