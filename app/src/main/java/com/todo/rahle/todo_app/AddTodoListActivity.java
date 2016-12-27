package com.todo.rahle.todo_app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import DataHandeler.TodoHandeler;

public class AddTodoListActivity extends AppCompatActivity {

    private TodoHandeler todo;

    private EditText descriptionField;
    private CalendarView dateField;
    private Button addTodoBtn;

    private String selectedDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo_list);
        todo = TodoHandeler.getInstance();

        descriptionField = (EditText) findViewById(R.id.desc);
        dateField = (CalendarView) findViewById(R.id.date);
        addTodoBtn = (Button) findViewById(R.id.addTodoListBtn);

        // set the selected date variable to today
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        selectedDate = dateFormat.format(date);

        dateField.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String yearS = year + "";
                String monthS = month + "";
                String dayOfMonthS = dayOfMonth + "";

                if (monthS.length() == 1) {
                    monthS = "0" + monthS;
                }
                if (dayOfMonthS.length() == 1) {
                    dayOfMonthS = "0" + dayOfMonthS;
                }

                selectedDate = yearS + "-" + monthS + "-" + dayOfMonthS;
            }
        });

        addTodoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(descriptionField.getText().toString().trim().equals(""))
                addTodoBtn.setEnabled(false);
                addTodoList();
            }
        });

        descriptionField.requestFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);

    }

    private void addTodoList() {
        String desc = descriptionField.getText().toString();
        desc.trim();

        todo.addTodoList(desc, selectedDate, this);
    }


    public void todoListIsAdded(){
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        finish();

    }

}
