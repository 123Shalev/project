package com.example.project.Screens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.example.project.models.User;
import com.example.project.services.AuthenticationService;
import com.example.project.services.DatabaseService;

public class LoginActivitiy extends AppCompatActivity {
    private static final String TAG = "loginToFireBase";
    TextView txtLogin;
    EditText etEmailLogin, etPasswordLogin;
    Button btnLogin;

    String email2, pass2;
    public static final String MyPREFERENCES = "MyPrefs";

    DatabaseService databaseService;
    AuthenticationService authenticationService;
    SharedPreferences sharedpreferences;
    public static User theUser;
    public static Boolean isAdmin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activitiy);

        databaseService = DatabaseService.getInstance();
        authenticationService = AuthenticationService.getInstance();

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        initViews();

        email2 = sharedpreferences.getString("email", "");
        pass2 = sharedpreferences.getString("password", "");
        etEmailLogin.setText(email2);
        etPasswordLogin.setText(pass2);
    }

    private void initViews() {
        btnLogin = findViewById(R.id.btnLogin);
        etEmailLogin = findViewById(R.id.etEmailLog);
        etPasswordLogin = findViewById(R.id.etPassLog);

        btnLogin.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        email2 = etEmailLogin.getText().toString().trim();
        pass2 = etPasswordLogin.getText().toString().trim();

        if (email2.isEmpty() || pass2.isEmpty()) {
            Toast.makeText(LoginActivitiy.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        authenticationService.signIn(email2, pass2, new AuthenticationService.AuthCallback() {
            @Override
            public void onCompleted(String uid) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("email", email2);
                editor.putString("password", pass2);
                editor.apply();

                Log.d(TAG, "signInWithEmail:success");

                Intent go = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(go);
            }

            @Override
            public void onFailed(Exception e) {
                Log.w(TAG, "signInWithEmail:failure", e);
                Toast.makeText(LoginActivitiy.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onGobackClick2(View view) {
        Intent go = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(go);
    }

    public void Signup(View view) {
        Intent go = new Intent(getApplicationContext(), Signup.class);
        startActivity(go);
    }
}