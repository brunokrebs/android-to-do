package com.auth0.microblog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONArray>, Response.ErrorListener {
    private AuthenticationHandler authenticationHandler;
    private MicroPostAdapter microPostsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create and configure the adapter
        microPostsAdapter = new MicroPostAdapter(this);
        ListView microPostsListView = findViewById(R.id.micro_posts);
        microPostsListView.setAdapter(microPostsAdapter);

        // issue the request
        String url = "http://10.0.2.2:3001";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest microPostsRequest = new JsonArrayRequest(url, this, this);
        queue.add(microPostsRequest);

        this.authenticationHandler = new AuthenticationHandler(this);
    }

    @Override
    public void onResponse(JSONArray response) {
        try {
            List<MicroPost> microPosts = new ArrayList<>(response.length());
            for (int i = 0; i < response.length(); i++) {
                JSONObject item = response.getJSONObject(i);
                String id = item.getString("_id");
                String message = item.getString("title");
                microPosts.add(new MicroPost(id, message));
            }
            microPostsAdapter.setMicroPosts(microPosts);
        } catch (JSONException error) {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage(error.toString())
                    .show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        microPostsAdapter.setMicroPosts(new ArrayList<MicroPost>());
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(error.getMessage())
                .show();
    }

    public void writeNewMicroPost(View view) {
        if (authenticationHandler.hasValidCredentials()) {
            startActivity(new Intent(this, MicroPostFormActivity.class));
            return;
        }
        authenticationHandler.refreshCredentials(MicroPostFormActivity.class);
    }
}
