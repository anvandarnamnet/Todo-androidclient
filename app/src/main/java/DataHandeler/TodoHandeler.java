package DataHandeler;

import android.content.Context;
import android.text.Editable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.result.UserProfile;
import com.todo.rahle.todo_app.AddTodoItemActivity;
import com.todo.rahle.todo_app.AddTodoListActivity;
import com.todo.rahle.todo_app.StartActivity;
import com.todo.rahle.todo_app.TodoActivity;

import org.json.JSONArray;

/**
 * Created by rahle on 2016-12-22.
 */

public class TodoHandeler {

    // the instance of this class (this is a singlethon)
    private static TodoHandeler instance = null;


    protected TodoHandeler() {
        // Exists only to defeat instantiation.
    }

    // get the todohandeler instance
    public static TodoHandeler getInstance() {
        if(instance == null) {
            instance = new TodoHandeler();
        }

        return instance;
    }


    // Get the todo-lists
    public void getOuter(final Context context, final StartActivity startActivity){

        // the first step is to get the active user
        AuthenticationAPIClient client = new AuthenticationAPIClient(
                new Auth0("PzwLG899qFespCmk7RjoYR3pVeTpKkKD", "oskar.eu.auth0.com"));
        client.tokenInfo(CredentialsManager.getCredentials(context).getIdToken())
                .start(new BaseCallback<UserProfile, AuthenticationException>() {
                    @Override
                    public void onSuccess(UserProfile payload){

                        // the second step is to make a request to the server
                        RequestQueue queue = Volley.newRequestQueue(context);
                        String url = "https://fathomless-bayou-11388.herokuapp.com/api/gettodos";


                        String tokenID = CredentialsManager.getCredentials(context).getIdToken();

                        // the data we send to the server
                        JSONArray array = new JSONArray();
                        array.put(payload.getId());

                        RestHandeler authorizationRequest = new RestHandeler(Request.Method.POST,url,
                                tokenID, array, new Response.Listener<JSONArray>(){

                            @Override
                            public void onResponse(JSONArray response) {

                                // the response from the server sends back
                                startActivity.setList(response);

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println("Error while getting todos from server: " + error.toString());
                            }
                        });

                        queue.add(authorizationRequest);
                    }

                    @Override
                    public void onFailure(AuthenticationException error){
                    }
                });
    }


    // get the
    public void getInner(String id, final TodoActivity todo) {
        RequestQueue queue = Volley.newRequestQueue(todo);
        String url = "https://fathomless-bayou-11388.herokuapp.com/api/gettodoitems";


        String tokenID = CredentialsManager.getCredentials(todo).getIdToken();

        JSONArray array = new JSONArray();
        array.put(id);

        RestHandeler authorizationRequest = new RestHandeler(Request.Method.POST,url,
                tokenID, array, new Response.Listener<JSONArray>(){

            @Override
            public void onResponse(JSONArray response) {
                // Parse Response
                todo.setList(response);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error while getting todos from server: " + error.toString());
            }
        });

        queue.add(authorizationRequest);

    }


    public void deleteTodoListByid(String id, final TodoActivity todo){
        RequestQueue queue = Volley.newRequestQueue(todo);
        String url = "https://fathomless-bayou-11388.herokuapp.com/api/deletetodolist";

        String tokenID = CredentialsManager.getCredentials(todo).getIdToken();


        JSONArray array = new JSONArray();
        array.put(id);

        RestHandeler authorizationRequest = new RestHandeler(Request.Method.POST,url,
                tokenID, array, new Response.Listener<JSONArray>(){

            @Override
            public void onResponse(JSONArray response) {
                // Parse Response
                todo.isDeleted();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error while getting todos from server: " + error.toString());
            }
        });

        queue.add(authorizationRequest);
    }

    public void addTodoList(final String text, final String selectedDate, final AddTodoListActivity addTodoListActivity) {

        AuthenticationAPIClient client = new AuthenticationAPIClient(
                new Auth0("PzwLG899qFespCmk7RjoYR3pVeTpKkKD", "oskar.eu.auth0.com"));
        client.tokenInfo(CredentialsManager.getCredentials(addTodoListActivity).getIdToken())
                .start(new BaseCallback<UserProfile, AuthenticationException>() {
                    @Override
                    public void onSuccess(UserProfile payload){
                        RequestQueue queue = Volley.newRequestQueue(addTodoListActivity);
                        String url = "https://fathomless-bayou-11388.herokuapp.com/api/addtodolist";


                        String tokenID = CredentialsManager.getCredentials(addTodoListActivity).getIdToken();

                        JSONArray array = new JSONArray();
                        array.put(text);
                        array.put(selectedDate);
                        array.put(payload.getId());

                        RestHandeler authorizationRequest = new RestHandeler(Request.Method.POST,url,
                                tokenID, array, new Response.Listener<JSONArray>(){

                            @Override
                            public void onResponse(JSONArray response) {
                                // Parse Response

                                addTodoListActivity.todoListIsAdded();

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println("Error while getting todos from server: " + error.toString());
                            }
                        });

                        queue.add(authorizationRequest);
                    }

                    @Override
                    public void onFailure(AuthenticationException error){
                    }
                });

    }


    public void addTodoItem(final String desc, final String listId, final AddTodoItemActivity addTodoItemActivity){
        AuthenticationAPIClient client = new AuthenticationAPIClient(
                new Auth0("PzwLG899qFespCmk7RjoYR3pVeTpKkKD", "oskar.eu.auth0.com"));
        client.tokenInfo(CredentialsManager.getCredentials(addTodoItemActivity).getIdToken())
                .start(new BaseCallback<UserProfile, AuthenticationException>() {
                    @Override
                    public void onSuccess(UserProfile payload){
                        RequestQueue queue = Volley.newRequestQueue(addTodoItemActivity);
                        String url = "https://fathomless-bayou-11388.herokuapp.com/api/addtodoitem";


                        String tokenID = CredentialsManager.getCredentials(addTodoItemActivity).getIdToken();

                        JSONArray array = new JSONArray();
                        array.put(desc);
                        array.put(listId);
                        array.put(payload.getId());

                        RestHandeler authorizationRequest = new RestHandeler(Request.Method.POST,url,
                                tokenID, array, new Response.Listener<JSONArray>(){

                            @Override
                            public void onResponse(JSONArray response) {
                                // Parse Response

                                addTodoItemActivity.itemIsAdded();

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println("Error while getting todos from server: " + error.toString());
                            }
                        });

                        queue.add(authorizationRequest);
                    }

                    @Override
                    public void onFailure(AuthenticationException error){
                    }
                });
    }

    public void deleteListItemById(String id,final TodoActivity todo) {
        RequestQueue queue = Volley.newRequestQueue(todo);
        String url = "https://fathomless-bayou-11388.herokuapp.com/api/deletetodoitem";

        String tokenID = CredentialsManager.getCredentials(todo).getIdToken();


        JSONArray array = new JSONArray();
        array.put(id);

        RestHandeler authorizationRequest = new RestHandeler(Request.Method.POST,url,
                tokenID, array, new Response.Listener<JSONArray>(){

            @Override
            public void onResponse(JSONArray response) {
                // Parse Response
                todo.itemIsDeleted();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error while getting todos from server: " + error.toString());
            }
        });

        queue.add(authorizationRequest);

    }
}