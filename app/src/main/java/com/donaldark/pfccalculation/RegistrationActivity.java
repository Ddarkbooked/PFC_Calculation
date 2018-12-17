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
        FirebaseUser currentUser = mAuth.getCurrentUser();
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
            Toast.makeText(RegistrationActivity.this, "Введите почту", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(RegistrationActivity.this, "Введите пароль", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(passwordAgain)) {
            Toast.makeText(RegistrationActivity.this, "Подтвердите пароль", Toast.LENGTH_LONG).show();
            return;
        }

        if (!password.equals(passwordAgain)) {
            Toast.makeText(RegistrationActivity.this, "Пароли не совпадают", Toast.LENGTH_LONG).show();
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
                            Toast.makeText(RegistrationActivity.this, "Вы не зарегистрировались, попробуйте еще раз", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
}