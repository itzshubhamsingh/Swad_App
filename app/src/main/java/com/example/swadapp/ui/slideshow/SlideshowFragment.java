package com.example.swadapp.ui.slideshow;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.swadapp.MainActivity;
import com.example.swadapp.MainActivity2;
import com.example.swadapp.Models.User;
import com.example.swadapp.SignIn;
import com.example.swadapp.SignUp;
import com.example.swadapp.databinding.FragmentSlideshowBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class SlideshowFragment extends Fragment {

    EditText username, password, email, phone, postal;
    Button update;
    FirebaseDatabase database;
    ProgressDialog progressDialog;   // whenever you will click on signup it will load
    User user;
    String id;
    private FirebaseAuth mAuth;

    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        username = binding.usernameOfAccount;
        password = binding.editTextTextPassword;
        email = binding.email;
        phone = binding.phoneno;
        postal = binding.postalcode;
        update = binding.update;

        Context context = this.getContext();

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // creating Progress dialog box

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Updating Account");
        progressDialog.setMessage("We are Updating Your Account");

        // Retrieving Data from firebase




        if(mAuth.getCurrentUser() == null){
            Toast.makeText(context, "You must be Signed In", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), SignIn.class);
            startActivity(intent);
        }
        else {
            id = mAuth.getCurrentUser().getUid();
            database.getReference().child("User Details").child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String use = snapshot.child("username").getValue().toString();
                    String pass = snapshot.child("password").getValue().toString();
                    String emai = snapshot.child("email").getValue().toString();
                    String phon = snapshot.child("phone").getValue().toString();
                    String post = snapshot.child("postal").getValue().toString();
                    username.setText(use);
                    password.setText(pass);
                    email.setText(emai);
                    phone.setText(phon);
                    postal.setText(post);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(context, "Error is" + error, Toast.LENGTH_SHORT).show();
                }
            });

//        user = getActivity().getIntent().getParcelableExtra("User Details");
//        if(user!=null){
//
////            database.getReference().child("User Details").child()
////
//            username.setText(user.getUsername());
//            password.setText(user.getPassword());
//            postal.setText(user.getPostal());
//            email.setText(user.getEmail());
//            phone.setText(user.getPhone());
//            id = user.getUserId();
//        }
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressDialog.show();
                    Map<String, Object> map = new HashMap<>();
                    map.put("email", email.getText().toString());
                    map.put("password", password.getText().toString());
                    map.put("postal", postal.getText().toString());
                    map.put("phone", phone.getText().toString());
                    map.put("username", username.getText().toString());
//                map.put("UserID", id);


                    database.getReference().addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            progressDialog.dismiss();
                            database.getReference().child("User Details").child(id).updateChildren(map);
                            Toast.makeText(context, "Successfully Updated...", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(context, MainActivity.class);
                            startActivity(intent);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                            Toast.makeText(context, "Error Occurred", Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            });
        }








        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}