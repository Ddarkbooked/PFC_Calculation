package com.donaldark.pfccalculation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.nsd.NsdManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.Layout;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText Email;
    private EditText Password;
    private Button SignInButton;
    private Button RegistrationButton;
    private Button LoginButton;

    MainActivity mainActivity = new MainActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        SignInButton = (Button) findViewById(R.id.sign_in_button);
        RegistrationButton = (Button) findViewById(R.id.registration_button);

        final String checkEmail = Email.getText().toString();
        final String checkPassword = Password.getText().toString();

        SignInButton.setEnabled(false);

            TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String usernameInput = Email.getText().toString().trim();
                String passwordInput = Password.getText().toString().trim();

                SignInButton.setEnabled(!usernameInput.isEmpty() && !passwordInput.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        Email.addTextChangedListener(textWatcher);
        Password.addTextChangedListener(textWatcher);

        SignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                    mainActivity.closeKeyboard();
                    signing(Email.getText().toString(), Password.getText().toString());
            }
        });
        RegistrationButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                    // registration(Email.getText().toString(), Password.getText().toString());
                    mainActivity.closeKeyboard();
                    Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                    startActivity(intent);

            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            mAuth.getCurrentUser();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            return;
        }

//        // Initialize Facebook Login button
//        mCallbackManager = CallbackManager.Factory.create();
//        LoginButton loginButton = findViewById(R.id.buttonFacebookLogin);
//        loginButton.setReadPermissions("email", "public_profile");
//        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Log.d(TAG, "facebook:onSuccess:" + loginResult);
//                handleFacebookAccessToken(loginResult.getAccessToken());
//            }
//
//            @Override
//            public void onCancel() {
//                Log.d(TAG, "facebook:onCancel");
//                // ...
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Log.d(TAG, "facebook:onError", error);
//                // ...
//            }
//        });
//        // ...
//        @Override
//        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//            super.onActivityResult(requestCode, resultCode, data);
//
//            // Pass the activity result back to the Facebook SDK
//            mCallbackManager.onActivityResult(requestCode, resultCode, data);
//        }


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    public void signing(String email, String password) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginActivity.this, "Вы не авторизировались", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}