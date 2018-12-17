package com.donaldark.pfccalculation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private LinearLayout registrationLinearLayout;
    private EditText registrationEmail;
    private EditText registrationPassword;
    private EditText registrationPasswordAgain;
    private Button registrationRegistrationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();

        registrationLinearLayout = (LinearLayout) findViewById(R.id.registration_linearLayout);
        registrationEmail = (EditText) findViewById(R.id.registration_email);
        registrationPassword = (EditText) findViewById(R.id.registration_password);
        registrationPasswordAgain = (EditText) findViewById(R.id.registration_password_again);
        registrationRegistrationButton = (Button) findViewById(R.id.registration_registration_button);



        registrationRegistrationButton.setEnabled(false);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String emailInput = registrationEmail.getText().toString().trim();
                String passwordInput = registrationPassword.getText().toString().trim();
                String passwordAgainInput = registrationPasswordAgain.getText().toString().trim();

                registrationRegistrationButton.setEnabled(!emailInput.isEmpty() && !passwordInput.isEmpty() && !passwordAgainInput.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
            registrationEmail.addTextChangedListener(textWatcher);
            registrationPassword.addTextChangedListener(textWatcher);
            registrationPasswordAgain.addTextChangedListener(textWatcher);

//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(RegistrationActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//
//                        // ...
//                    }
//                });

        registrationRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // updateUI(currentUser);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private void registerUser() {
        String email = registrationEmail.getText().toString().trim();
        String password = registrationPassword.getText().toString().trim();
        String passwordAgain = registrationPasswordAgain.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Snackbar.make(registrationLinearLayout, "Введите почту", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Snackbar.make(registrationLinearLayout, "Введите пароль", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(passwordAgain)) {
            Snackbar.make(registrationLinearLayout, "Подтвердите пароль", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (!password.equals(passwordAgain)) {
            Snackbar.make(registrationLinearLayout, "Пароли не совпадают", Snackbar.LENGTH_LONG).show();
            return;
        }

        Log.d("reg:", "email: "+email + "pass: "+password);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this, "Вы успешно зарегистрировались", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                            startActivity(intent);

                        } else {
                            Snackbar.make(registrationLinearLayout, "Вы не зарегистрировались, попробуйте еще раз", Snackbar.LENGTH_LONG).show();

                        }
                    }
                });
    }


}




//    public void registration(String email, String password, String passwordAgain) {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (email.isEmpty() || password.isEmpty()) {
//            Snackbar.make(Email, "Введите правильные данные", Snackbar.LENGTH_LONG).show();
//            return;
//        }
//
//        mAuth.createUserWithEmailAndPassword(email, password, passwordAgain).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    FirebaseUser user = mAuth.getCurrentUser();
//                    Snackbar.make(Email, "Вы зарегистрировались", Snackbar.LENGTH_LONG).show();
//                } else {
//                    Snackbar.make(Email, "Введите правильные данные", Snackbar.LENGTH_LONG).show();
//                }
//            }
//        });
//    }