package com.example.project.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.example.project.models.User;
import com.example.project.services.AuthenticationService;
import com.example.project.services.DatabaseService;

public class Signup extends AppCompatActivity {
    private EditText etFName, etLName, etPhone, etEmail, etPass;
    private Button buttonSignUp;

    DatabaseService databaseService;
    AuthenticationService authenticationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        databaseService = DatabaseService.getInstance();
        authenticationService = AuthenticationService.getInstance();

        etFName = findViewById(R.id.etFname);
        etLName = findViewById(R.id.etLname);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPassword);
        buttonSignUp = findViewById(R.id.btnReg);

        buttonSignUp.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String fName = etFName.getText().toString().trim();
        String lName = etLName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPass.getText().toString().trim();

        if (TextUtils.isEmpty(fName) || TextUtils.isEmpty(lName) || TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "נא למלא את כל השדות", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "הסיסמה חייבת להכיל לפחות 6 תווים", Toast.LENGTH_SHORT).show();
            return;
        }

        authenticationService.signUp(email, password, new AuthenticationService.AuthCallback() {
            @Override
            public void onCompleted(String uid) {
                User user = new User(uid, fName, lName, phone, email, password);
                databaseService.createNewUser(user, new DatabaseService.DatabaseCallback<Void>() {
                    @Override
                    public void onCompleted(Void object) {
                        Toast.makeText(Signup.this, "נרשמת בהצלחה!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailed(Exception e) {
                        Toast.makeText(Signup.this, "שגיאה בשמירת הנתונים", Toast.LENGTH_SHORT).show();
                        authenticationService.signOut();
                    }
                });
            }

            @Override
            public void onFailed(Exception e) {
                Toast.makeText(Signup.this, "שגיאה בשמירת הנתונים", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void MainActivitiy(View view) {
        Intent go = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(go);
    }
}