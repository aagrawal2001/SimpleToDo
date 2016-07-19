package com.andyagrawal.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<TodoItem> items;
    ArrayAdapter<TodoItem> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new TodoItemAdapter(this, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    public void onAddItem(View view) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        TodoItem item = new TodoItem(itemText);
        itemsAdapter.add(item);
        etNewItem.setText("");
        writeItems();
    }

    // Constants defining how data is passed from this activity to others
    public static final String TASK_TITLE_KEY = "value";
    public static final String TASK_POSITION_KEY = "pos";

    // REQUEST_CODE can be any value we like, used to determine the result type later
    private final int REQUEST_CODE = 20;

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                }
        );

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                        LinearLayout layout = (LinearLayout) item;
                        TextView itemText = (TextView) layout.getChildAt(0);
                        i.putExtra(TASK_TITLE_KEY, itemText.getText());
                        i.putExtra(TASK_POSITION_KEY, pos);
                        startActivityForResult(i, REQUEST_CODE);
                    }
                }

        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String value = data.getExtras().getString(TASK_TITLE_KEY);
            int pos = data.getExtras().getInt(TASK_POSITION_KEY, 0);
            items.set(pos, new TodoItem(value));
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

    private void readItems() {
        List<TodoItem> todoItems = new Select().from(TodoItem.class)
                .orderBy("position ASC").limit(100).execute();
        items = new ArrayList<>();
        for (TodoItem todoItem: todoItems) {
            items.add(todoItem);
        }
    }

    private void writeItems() {
        // The logic for writes might look a bit confusing. We first delete all records
        // and then re-insert them. This makes it easier than figuring out the differences
        // and selectively updating/deleting/inserting records.
        new Delete().from(TodoItem.class).execute();
        int pos = 1;
        for (TodoItem item: items) {
            // Ideally, we'd just do item.save(), but each item was loaded from the database,
            // so, ActiveAndroid converts that into a database update operation. Instead, we
            // want everything to be an insert, because we wiped out everything at the beginning
            // of the function.
            TodoItem newItem = new TodoItem(item.text);
            newItem.position = pos;
            newItem.save();
            pos++;
        }
   }

}
