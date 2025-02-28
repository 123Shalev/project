package com.example.project.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.example.project.models.User;
import com.example.project.services.AuthenticationService;
import com.example.project.services.DatabaseService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    Button btnSign, btnLog, btnMyTasks, btnAddTask, btnAdmin, btnInfo, btnLogOut;
    private static final String ADMIN_EMAIL = "shalev@gmail.com";
    DatabaseService databaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseService = DatabaseService.getInstance();

        btnSign = findViewById(R.id.btnSign);
        btnLog = findViewById(R.id.btnLog);
        btnMyTasks = findViewById(R.id.btnMyTasks);
        btnAddTask = findViewById(R.id.btnAddTask);
        btnAdmin = findViewById(R.id.btnAdmin);
        btnInfo = findViewById(R.id.btnInfo);
        btnLogOut = findViewById(R.id.btnLogOut); // כפתור התנתקות

        btnSign.setOnClickListener(this);
        btnLog.setOnClickListener(this);
        btnMyTasks.setOnClickListener(this);
        btnAddTask.setOnClickListener(this);
        btnAdmin.setOnClickListener(this);
        btnInfo.setOnClickListener(this);
        btnLogOut.setOnClickListener(this);
        boolean isUserLogin = AuthenticationService.getInstance().isUserSignedIn();
        if (isUserLogin) {
            databaseService.getUser(AuthenticationService.getInstance().getCurrentUserId(), new DatabaseService.DatabaseCallback<User>() {
                @Override
                public void onCompleted(User user) {
                    if (user.getEmail().equals(ADMIN_EMAIL)) {
                        btnAdmin.setVisibility(View.VISIBLE);
                    } else {
                        btnAdmin.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailed(Exception e) {
                    Log.e(TAG, e.getMessage());
                }});
        }


    }

    @Override
    public void onClick(View v) {

        boolean isUserLogin = AuthenticationService.getInstance().isUserSignedIn();
        if (v == btnSign) {
            startActivity(new Intent(getApplicationContext(), Signup.class));
        }
        else if (v == btnLog) {
            startActivity(new Intent(getApplicationContext(), LoginActivitiy.class));
        }
        else if (v == btnMyTasks) {
            Log.d(TAG, "btnMyTasks was clicked");
            Intent intent;
            if (isUserLogin) {
                Log.d(TAG, "btnMyTasks was clicked and UserLogin");
                intent = new Intent(this, MyTasks.class);
            } else {
                Log.d(TAG, "btnMyTasks was clicked and UserNotLogin");
                Toast.makeText(this, "עליך להתחבר למערכת תחילה!", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, Signup.class);
            }
            startActivity(intent);
        }
        else if (v == btnAddTask) {
            if (isUserLogin) {
                startActivity(new Intent(getApplicationContext(), AddTask.class));
            } else {
                Toast.makeText(this, "עליך להתחבר למערכת תחילה!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Signup.class));
            }
        }
        else if (v == btnAdmin) {
            startActivity(new Intent(getApplicationContext(), AdminPage.class));
        }
        else if (v == btnInfo) {
            startActivity(new Intent(getApplicationContext(), Info.class));
        }
        else if (v == btnLogOut) { // כפתור התנתקות
            if (isUserLogin) {
                AuthenticationService.getInstance().signOut();
                Toast.makeText(this, "התנתקת בהצלחה!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Signup.class));
                finish();
            } else {
                Toast.makeText(this, "אינך מחובר!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
