package com.quantam.test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInFragment extends Fragment {
    private FirebaseAuth mAuth;
    private EditText emailEditText,passwordEditText;
    private ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sign_in, container, false);
        emailEditText = view.findViewById(R.id.editEmailSignIN);
        passwordEditText = view.findViewById(R.id.editPassSignIn);
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading..!");
        mAuth = FirebaseAuth.getInstance();
        Button signInBtn = view.findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString();
                if (email.isEmpty() || password.isEmpty()){
                    dialog.dismiss();
                    passwordEditText.requestFocus();
                    passwordEditText.setError("Enter a Password");
                    emailEditText.requestFocus();
                    emailEditText.setError("Enter an Email Address");
                }else{
                    loginUserAccount(email,password);
                }
            }
        });
        return view;
    }

    private void loginUserAccount(String email,String password) {
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {
                            if (task.getException().getMessage() == "There is no user record corresponding to this identifier. The user may have been deleted."){
                                dialog.dismiss();
                                Toast.makeText(getContext(), "Email not registered! Please Signup!", Toast.LENGTH_SHORT).show();
                            }else {
                                dialog.dismiss();
                                Intent intent = new Intent(getContext(),HomeActivity.class);
                                startActivity(intent);
                            }
                        }catch (Exception e){

                        }
                        Toast.makeText(getContext(), "Login Successful.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(),HomeActivity.class);
                        dialog.dismiss();
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}