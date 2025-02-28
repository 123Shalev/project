package com.example.project.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTask extends AppCompatActivity {

    private EditText etTaskName, etDeadlineDate, etDeadlineTime;
    private Button btnSaveTask;
    private DatabaseReference databaseTasks;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        etTaskName = findViewById(R.id.etTaskName);
        etDeadlineDate = findViewById(R.id.etDeadlineDate);
        etDeadlineTime = findViewById(R.id.etDeadlineTime);
        btnSaveTask = findViewById(R.id.btnSaveTask);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            databaseTasks = FirebaseDatabase.getInstance().getReference("tasks").child(user.getUid());

            btnSaveTask.setOnClickListener(v -> saveTask());
        }
    }

    private void saveTask() {
        String taskName = etTaskName.getText().toString().trim();
        String deadlineDate = etDeadlineDate.getText().toString().trim();
        String deadlineTime = etDeadlineTime.getText().toString().trim();

        if (TextUtils.isEmpty(taskName) || TextUtils.isEmpty(deadlineDate) || TextUtils.isEmpty(deadlineTime)) {
            Toast.makeText(this, "נא למלא את כל השדות", Toast.LENGTH_SHORT).show();
            return;
        }

        String taskId = databaseTasks.push().getKey();
        Task task = new Task(taskId, taskName, deadlineDate, deadlineTime);

        if (taskId != null) {
            databaseTasks.child(taskId).setValue(task)
                    .addOnCompleteListener(task1 -> {
                        Toast.makeText(AddTask.this, "משימה נוספה בהצלחה", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddTask.this, MyTasks.class));
                        finish();
                    });
        }
    }
}
