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

    private EditText description;
    private Button addItem;
    private TodoHandeler todo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo_item);

        description = (EditText) findViewById(R.id.itemdesc);
        addItem = (Button) findViewById(R.id.btnAddItem);
        todo = TodoHandeler.getInstance();

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(description.getText().toString().trim().equals("")){
                    return;
                }
                addItem.setEnabled(false);
                addItem();
            }
        });

        description.requestFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    private void addItem() {
        String id = getIntent().getExtras().getString("id");
        todo.addTodoItem(description.getText().toString(), id, this);
    }

    public void itemIsAdded(){
        TodoActivity.activity.finish();
        Intent intent = new Intent(this, TodoActivity.class);
        intent.putExtra("id", getIntent().getExtras().getString("id"));
        intent.putExtra("title", getIntent().getExtras().getString("title"));
        startActivity(intent);
        finish();
    }
}
