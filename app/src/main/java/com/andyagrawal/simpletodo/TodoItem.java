package com.andyagrawal.simpletodo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import java.util.Date;

/**
 * Created by andy_agrawal on 7/14/16.
 */
@Table(name = "TodoItems")
public class TodoItem extends Model {
    @Column(name = "text")
    public String text;

    @Column(name = "position")
    public int position;

    @Column(name = "duedate")
    public Date dueDate;

    public TodoItem(){
        super();
    }

    public TodoItem(String text, Date dueDate) {
        super();
        this.text = text;
        this.dueDate = dueDate;
    }

    public String toString() {
        return text;
    }
}

