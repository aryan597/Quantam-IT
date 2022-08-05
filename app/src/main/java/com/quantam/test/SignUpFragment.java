package com.quantam.test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class SignUpFragment extends Fragment {

    private EditText emailEditText, passwordEditText;
    private Button Btn;
    private ProgressDialog dialog;
    private FirebaseAuth mAuth;
    private CheckBox aggrement;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sign_up, container, false);
        mAuth = FirebaseAuth.getInstance();
        emailEditText = view.findViewById(R.id.editEmailSignUp);
        passwordEditText = view.findViewById(R.id.editPassSignUp);
        dialog = new ProgressDialog(getContext());
        aggrement = view.findViewById(R.id.checkBox);
        Button signUpBtn = view.findViewById(R.id.signUpBtn);
        Spinner spinner = view.findViewById(R.id.spinner);
        TextView tv = view.findViewById(R.id.textView2);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("+91");
        arrayList.add("+92");
        arrayList.add("+1");
        arrayList.add("+23");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tutorialsName = parent.getItemAtPosition(position).toString();
                if (tutorialsName.equals("+92")){
                    tv.setText("PK");
                }
                else if (tutorialsName.equals("+1")){
                    tv.setText("US");
                }
                else if (tutorialsName.equals("+23")){
                    tv.setText("IND");
                }
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
        signUpBtn.setVisibility(View.GONE);
        aggrement.setChecked(false);
        aggrement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){
                    signUpBtn.setVisibility(View.VISIBLE);
                }
            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (email.isEmpty()||password.isEmpty()){
                    dialog.dismiss();
                    emailEditText.requestFocus();
                    emailEditText.setError("Enter an email");
                    passwordEditText.requestFocus();
                    passwordEditText.setError("Enter a Password!");
                }else {
                    registerNewUser(email,password);
                }
            }
        });
        return view;
    }
    private void registerNewUser(String email,String password){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(getContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        startActivity(new Intent(getContext(),HomeActivity.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
}