package com.todo.rahle.todo_app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import DataHandeler.ListAdapter;
import DataHandeler.TodoHandeler;

public class TodoActivity extends AppCompatActivity {

    // the todohandeler
    private TodoHandeler todo;

    // our list adapter
    private ListAdapter adapter;

    //add new item btn
    private Button addNewItem;

    // the linear layout to present the loading view
    private LinearLayout linlaHeaderProgress;

    // our listview
    private SwipeMenuListView listView;

    // the todos and the ids
    private ArrayList<String> todos = new ArrayList<>();
    private ArrayList<String> todoId = new ArrayList<>();

    public static Activity activity;

    private String listId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // to present the loading view
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setProgressBarIndeterminateVisibility(true);
        setContentView(R.layout.activity_todo);

        // get the todohandeler instance
        todo = TodoHandeler.getInstance();

        // setup some ui
        listView = (SwipeMenuListView) findViewById(R.id.listView);
        addNewItem = (Button) findViewById(R.id.btnAdd);
        linlaHeaderProgress = (LinearLayout) findViewById(R.id.lin);

        // set click listener for our add new item button
        addNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddItemPage();
            }
        });

        // get the todo list title
        String tit = getIntent().getExtras().getString("title");
        setTitle(tit);
        activity = this;


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());

                // set item width
                openItem.setWidth(400);

                // set item title
                openItem.setTitle("Delete");

                // set item title fontsize
                openItem.setTitleSize(18);

                // set item title font color
                openItem.setTitleColor(Color.BLACK);

                // add to menu
                menu.addMenuItem(openItem);
            }
        };

        // setup the swipe settings in the listview
        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open
                        deleteListItem(position);
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    // delete a list item
    private void deleteListItem(int position) {
        String id = todoId.get(position);
        todos.remove(position);
        adapter = new ListAdapter(this, todos, null);
        listView.setAdapter(adapter);
        todo.deleteListItemById(id, this);


    }

    @Override
    public void onResume(){
        super.onResume();
        linlaHeaderProgress.setVisibility(View.VISIBLE);
        setupListData();
    }

    // show the add item page and send some data to it
    private void showAddItemPage() {
        Intent intent = new Intent(this, AddTodoItemActivity.class);
        intent.putExtra("id", listId);
        intent.putExtra("title", getIntent().getExtras().getString("title"));
        startActivity(intent);
    }

    // setup the list
    private void setupListData() {
        String id = getIntent().getExtras().getString("id");
        listId = id;
        todo.getInner(id, this);
    }

    // Callback function from the todohandeler
    public void setList(JSONArray array){
        todos.clear();
        todoId.clear();

        for(int i = 0; i < array.length(); i++){
            try {
                JSONObject object = array.getJSONObject(i);
                todos.add(object.getString("description"));
                todoId.add(object.getString("_id"));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        adapter = new ListAdapter(this, todos, null);
        listView.setAdapter(adapter);
        linlaHeaderProgress.setVisibility(View.GONE);

    }

    // set our status bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo_menu, menu);
        return true;
    }

    // what happens if someone clicks our statusbar?
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.userPage){
            // go to user page
        }

        return super.onOptionsItemSelected(item);
    }

    // callback function when the list is deleted
    public void isDeleted() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        finish();
    }

    // callbackfunction when a item is deleted
    public void itemIsDeleted() {
        setupListData();
    }
}
