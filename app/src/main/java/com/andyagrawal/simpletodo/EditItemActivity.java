package com.andyagrawal.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    private EditText etEditItem;
    private DatePicker dpDueDate;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        etEditItem = (EditText) findViewById(R.id.etEditItem);
        String etText = getIntent().getStringExtra(MainActivity.TASK_TITLE_KEY);
        dpDueDate = (DatePicker) findViewById(R.id.dpDueDate);
        int dueDateYear = getIntent().getIntExtra("dueDateYear", 0);
        int dueDateMonth = getIntent().getIntExtra("dueDateMonth", 0);
        int dueDateDay = getIntent().getIntExtra("dueDateDay", 0);
        dpDueDate.init(dueDateYear, dueDateMonth, dueDateDay, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {

                    }
                }
        );
        etEditItem.setText(etText);
        etEditItem.setFocusable(true);
        etEditItem.requestFocus();
        pos = getIntent().getIntExtra(MainActivity.TASK_POSITION_KEY, 0);
    }

    public void onSubmit(View view) {
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra(MainActivity.TASK_TITLE_KEY, etEditItem.getText().toString());
        data.putExtra(MainActivity.TASK_POSITION_KEY, pos); // ints work too
        data.putExtra("dueDateYear", dpDueDate.getYear());
        data.putExtra("dueDateMonth", dpDueDate.getMonth());
        data.putExtra("dueDateDay", dpDueDate.getDayOfMonth());
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
}
