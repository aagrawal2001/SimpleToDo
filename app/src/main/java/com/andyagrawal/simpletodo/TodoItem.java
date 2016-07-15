package com.andyagrawal.simpletodo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by andy_agrawal on 7/14/16.
 */
@Table(name = "TodoItems")
public class TodoItem extends Model {
    @Column(name = "text")
    public String text;

    @Column(name = "position")
    public int position;

    public TodoItem(){
        super();
    }

    public TodoItem(String text) {
        super();
        this.text = text;
    }

    public String toString() {
        return text;
    }
}

