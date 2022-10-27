package com.example.smarthomeapplication;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.Task;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    TextInputLayout emailInput, passwordInput, passwordConfirmationInput;
    Button registerButton;

    public static final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailInput = findViewById(R.id.registerEmail);
        passwordInput = findViewById(R.id.registerPassword);
        passwordConfirmationInput = findViewById(R.id.registerPasswordConfirmation);
        registerButton = findViewById(R.id.RegisterButton);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAuthentication();
            }
        });

    }

    private void performAuthentication() {
        String email = emailInput.getEditText().getText().toString();
        String password = String.valueOf(passwordInput.getEditText().getText());
        String passwordConfirmation = String.valueOf(passwordConfirmationInput.getEditText().getText());

        if(!email.matches(emailPattern)) {
            emailInput.setError("Enter a Correct Email Form!");
            emailInput.requestFocus();
        } else if (password.isEmpty() || password.length() < 6) {
            passwordInput.setError("Enter a Proper Password!");
            passwordInput.requestFocus();
        } else if (!password.matches(passwordConfirmation)) {
            passwordConfirmationInput.setError("Passwords don't match!");
            passwordConfirmationInput.requestFocus();
        } else {
            progressDialog.setMessage("Please Wait...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                progressDialog.dismiss();
                                redirectToNextActivity();
                                Toast.makeText(
                                        RegisterActivity.this,
                                        "Registration Succeeded",
                                        Toast.LENGTH_SHORT
                                ).show();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(
                                        RegisterActivity.this,
                                        ""+task.getException(),
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        }
                    });
        }
    }

    private void redirectToNextActivity() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
