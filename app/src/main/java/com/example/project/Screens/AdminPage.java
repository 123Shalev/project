package com.example.project.Screens;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.example.project.models.User;
import com.example.project.services.DatabaseService;

import java.util.ArrayList;
import java.util.List;

public class AdminPage extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lvAllUsers;
    private UserAdapter userAdapter;
    private ArrayList<User> users;

    private DatabaseService databaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        lvAllUsers = findViewById(R.id.lvAllUsers);
        users = new ArrayList<>();

        userAdapter = new UserAdapter(this, 0, users);
        lvAllUsers.setAdapter(userAdapter);
        lvAllUsers.setOnItemClickListener(this);

        databaseService = DatabaseService.getInstance();

        // Fetch users from the database

        databaseService.getUsers(new DatabaseService.DatabaseCallback<List<User>>() {

            @Override
            public void onCompleted(List<User> object) {

                users.clear();
                users.addAll(object);
                userAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailed(Exception e) {
                Log.e("TAG Failed to load teachers",e.getMessage().toString());
            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        User clickedUser = users.get(position);
        // Handle user click, e.g., open user details screen
    }
}
