package com.todo.rahle.todo_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import DataHandeler.TodoHandeler;

public class AddTodoItemActivity extends AppCompatActivity {

    // the description-input
    private EditText description;

    // the add todo item button
    private Button addItem;

    // the todo handeler
    private TodoHandeler todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo_item);

        // set the description input
        description = (EditText) findViewById(R.id.itemdesc);
        // set the add item button
        addItem = (Button) findViewById(R.id.btnAddItem);

        // set the todo handeler
        todo = TodoHandeler.getInstance();

        // set click listener to the button
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if desceription is empty
                if(description.getText().toString().trim().equals("")){
                    return;
                }
                // add the item
                addItem.setEnabled(false);
                addItem();
            }
        });

        // request focus to the description
        description.requestFocus();

        // open the keyboard
        InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    // method to add the new item
    private void addItem() {
        String id = getIntent().getExtras().getString("id");
        todo.addTodoItem(description.getText().toString(), id, this);
    }

    // the callback function when the item is added
    public void itemIsAdded(){
        TodoActivity.activity.finish();
        Intent intent = new Intent(this, TodoActivity.class);
        intent.putExtra("id", getIntent().getExtras().getString("id"));
        intent.putExtra("title", getIntent().getExtras().getString("title"));
        startActivity(intent);
        finish();
    }
}
