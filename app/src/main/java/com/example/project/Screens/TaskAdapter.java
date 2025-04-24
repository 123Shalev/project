package com.example.project.Screens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {
    private Context context;
    private List<Task> taskList;

    public TaskAdapter(Context context, List<Task> taskList) {
        super(context, R.layout.task_item, taskList);
        this.context = context;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
        }

        Task task = taskList.get(position);

        TextView tvTaskName = convertView.findViewById(R.id.tvTaskName);
        TextView tvDeadline = convertView.findViewById(R.id.tvDeadline);
        ImageButton btnDeleteTask = convertView.findViewById(R.id.btnDeleteTask);

        if (task != null) {
            tvTaskName.setText(task.getName());
            tvDeadline.setText(task.getDeadlineDate() + " - " + task.getDeadlineTime());

            btnDeleteTask.setOnClickListener(v -> {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null && task.getTaskId() != null) {
                    FirebaseDatabase.getInstance()
                            .getReference("tasks")
                            .child(user.getUid())
                            .child(task.getTaskId())
                            .removeValue()
                            .addOnCompleteListener(t -> {
                                if (t.isSuccessful()) {
                                    taskList.remove(position);
                                    notifyDataSetChanged();
                                }
                            });
                }
            });
        }

        return convertView;
    }
}
