package DataHandeler;


import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.todo.rahle.todo_app.MainActivity;
import com.todo.rahle.todo_app.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by rahle on 2016-12-20.
 * This class handels the rest request header.
 */

public class RestHandeler extends JsonArrayRequest {

    private String headerTokenID = null;


    public RestHandeler(int method, String url, String tokenID, JSONArray jsonRequest,
                                      Response.Listener listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        headerTokenID = tokenID;
    }

    @Override
    public Map getHeaders() throws AuthFailureError {
        Map headers = new HashMap();
        headers.put("Authorization", "Bearer " + headerTokenID);
        return headers;
    }
}


