package com.example.project.Screens;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyTasks extends AppCompatActivity {
    private ListView listViewTasks;
    private List<Task> taskList;
    private TaskAdapter adapter;
    private DatabaseReference databaseTasks;
    private FirebaseUser user;
    private Button btnDeleteAllTasks;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tasks);
        btnDeleteAllTasks = findViewById(R.id.btnDeleteAllTasks);
        listViewTasks = findViewById(R.id.listViewTasks);
        if (listViewTasks == null) {
            Toast.makeText(this, "שגיאה: רשימת המשימות לא נמצאה!", Toast.LENGTH_LONG).show();
            finish(); // סגירת הפעילות במקרה של שגיאה חמורה
            return;
        }
        btnDeleteAllTasks.setOnClickListener(v -> {
            if (user != null) {
                databaseTasks.removeValue().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        taskList.clear();

                        Toast.makeText(MyTasks.this, "כל המשימות נמחקו", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MyTasks.this, "שגיאה במחיקת המשימות", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        taskList = new ArrayList<>();
        adapter = new TaskAdapter(this, taskList);
        listViewTasks.setAdapter(adapter);

        user = FirebaseAuth.getInstance().getCurrentUser();


        databaseTasks = FirebaseDatabase.getInstance().getReference("tasks").child(user.getUid());
        databaseTasks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                taskList.clear();
                for (DataSnapshot taskSnapshot : snapshot.getChildren()) {
                    Task task = taskSnapshot.getValue(Task.class);
                    if (task != null) {
                        taskList.add(task);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyTasks.this, "שגיאה בטעינת המשימות: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}