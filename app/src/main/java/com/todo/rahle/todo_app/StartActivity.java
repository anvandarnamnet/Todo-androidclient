package com.todo.rahle.todo_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import DataHandeler.CredentialsManager;
import DataHandeler.ListAdapter;
import DataHandeler.TodoHandeler;

public class StartActivity extends AppCompatActivity {

    // our helper classes
    private TodoHandeler todo;
    private ListAdapter adapter;

    // the views
    private ListView list;
    private Button addTodoList;
    private LinearLayout linlaHeaderProgress;

    // the lists to fill the listview
    private ArrayList<String> todos = new ArrayList<String>();
    private ArrayList<String> id = new ArrayList<>();
    private ArrayList<String> dates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // initial setup
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setProgressBarIndeterminateVisibility(true);
        setContentView(R.layout.activity_start);

        // setup the todohandeler (init the todo-class and download the todos)
        todo = TodoHandeler.getInstance();


        // setup the add a new todo button
        addTodoList = (Button) findViewById(R.id.outer);
        addTodoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to some place
                showAddTodoListActivity();
            }
        });

        // setup the listview and asign a clicklistener
        list=(ListView)findViewById(R.id.list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // when a todolist is clicked, show the todopage
                showTodoPage(position);

            }
        });

        linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
    }

    @Override
    public void onResume(){
        super.onResume();
        updateListView();
    }

    private void updateListView(){
        linlaHeaderProgress.setVisibility(View.VISIBLE);
        todo.getOuter(this,this);
    }
    private void showAddTodoListActivity() {
        Intent intent = new Intent(this, AddTodoListActivity.class);
        startActivity(intent);
    }

    // show the todopage and give the todopage the right todolist-id
    private void showTodoPage(int pos) {
        Intent intent = new Intent(this, TodoActivity.class);
        intent.putExtra("id", id.get(pos));
        intent.putExtra("title", todos.get(pos));

        startActivity(intent);
    }


    // set the listadapter with the right data
    public void setList(JSONArray array){
        todos.clear();
        id.clear();
        dates.clear();

        for(int i = 0; i < array.length(); i++){
            try {
                JSONObject object = array.getJSONObject(i);
                todos.add(object.getString("description"));
                dates.add(object.getString("date").substring(0,10));
                id.add(object.getString("_id"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        adapter = new ListAdapter(this, todos, dates);
        list.setAdapter(adapter);
        linlaHeaderProgress.setVisibility(View.GONE);
    }


    // set our status bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // what happens if someone clicks our statusbar?
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.update){
            updateListView();
        }
        else if (id == R.id.userPage){
            // go to user page
            logout();
        }

        return super.onOptionsItemSelected(item);
    }

    // Logout the user and show the login activity
    private void logout() {
        CredentialsManager.deleteCredentials(this);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
