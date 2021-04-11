package com.example.emergencyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {

    private Context context;
    private List<User> userList = new ArrayList<>();

    public UserAdapter(@NonNull Context context, ArrayList<User> list) {
        super(context, 0, list);

        this.context = context;
        this.userList = list;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.user_list_item,parent,false);

        User currentUser = userList.get(position);

        /*
        for custom user image
         */
        ImageView image = (ImageView)listItem.findViewById(R.id.imageView_user);
        image.setImageResource(R.drawable.ic_user_icon);

        TextView name = (TextView) listItem.findViewById(R.id.textView_username);
        name.setText(currentUser.name);

        TextView release = (TextView) listItem.findViewById(R.id.textView_address);
        release.setText(currentUser.address.streetAddress);

        return listItem;
    }
}
