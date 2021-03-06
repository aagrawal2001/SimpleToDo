package com.andyagrawal.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by andy_agrawal on 7/18/16.
 */
public class TodoItemAdapter extends ArrayAdapter<TodoItem> {

    // View lookup cache
    private static class ViewHolder {
        TextView tvTitle;
    }

    public TodoItemAdapter(Context context, ArrayList<TodoItem> items) {
        super(context, R.layout.item_todo, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TodoItem item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_todo, parent, false);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTitle.setText(item.text);

        // Return the completed view to render on screen
        return convertView;
    }

}
