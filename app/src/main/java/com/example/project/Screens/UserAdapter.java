package com.example.project.Screens;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.models.User;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    Context context;
    List<User> users;

    public UserAdapter(Context context, int resource, List<User> users) {
        super(context, resource, users);
        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.userraw, parent, false);

        TextView tvfname = view.findViewById(R.id.tvfname);
        TextView tvlname = view.findViewById(R.id.tvlname);
        TextView tvphone = view.findViewById(R.id.tvphone);

        User user = users.get(position);

        tvfname.setText(user.getFname());
        tvlname.setText(user.getLname());
        tvphone.setText(user.getPhone());

        return view;
    }
}
