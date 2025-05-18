package com.example.project.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.example.project.models.User;
import com.example.project.services.DatabaseService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class AdminPage extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lvAllUsers;
    private UserAdapter userAdapter;
    private ArrayList<User> users;
    private DatabaseService databaseService;

    private static final String ADMIN_EMAIL = "shalev@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // בדיקה אם המשתמש המחובר הוא אדמין
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null || !currentUser.getEmail().equals(ADMIN_EMAIL)) {
            Toast.makeText(this, "גישה לעמוד זה מותרת רק לאדמין", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_admin_page);

        lvAllUsers = findViewById(R.id.lvAllUsers);
        users = new ArrayList<>();

        userAdapter = new UserAdapter(this, 0, users);
        lvAllUsers.setAdapter(userAdapter);
        lvAllUsers.setOnItemClickListener(this);

        databaseService = DatabaseService.getInstance();

        databaseService.getUsers(new DatabaseService.DatabaseCallback<List<User>>() {
            @Override
            public void onCompleted(List<User> object) {
                users.clear();
                users.addAll(object);
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(Exception e) {
                Log.e("TAG", "Failed to load users: " + e.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        User clickedUser = users.get(position);
        // אפשר לפתוח מסך פרטים
    }

    public void MainActivitiy(View view) {
    }
}
