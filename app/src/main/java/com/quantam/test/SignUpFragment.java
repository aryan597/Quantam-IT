package com.quantam.test;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    private EditText emailEditText, passwordEditText;
    private Button Btn;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;
    private CheckBox aggrement;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sign_up, container, false);
        mAuth = FirebaseAuth.getInstance();
        emailEditText = view.findViewById(R.id.editEmailSignUp);
        passwordEditText = view.findViewById(R.id.editPassSignUp);
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
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (email.isEmpty()){
                    Toast.makeText(getContext(), "Please enter an email address", Toast.LENGTH_SHORT).show();
                    emailEditText.requestFocus();
                    emailEditText.setError("Enter an email");
                }else if (password.isEmpty()){
                    Toast.makeText(getContext(), "Please enter a password", Toast.LENGTH_SHORT).show();
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
                        startActivity(new Intent(getContext(),HomeActivity.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}