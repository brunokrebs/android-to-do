package com.auth0.microblog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.auth0.microblog.model.MicroPost;
import com.auth0.microblog.model.UserProfile;
import com.auth0.microblog.util.MicroPostAdapter;
import com.auth0.microblog.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;

public class MainActivity extends AuthAwareActivity implements Response.Listener<JSONArray>, Response.ErrorListener {
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
    }

    @Override
    public void onResponse(JSONArray response) {
        try {
            List<MicroPost> microPosts = new ArrayList<>(response.length());
            for (int i = 0; i < response.length(); i++) {
                JSONObject item = response.getJSONObject(i);
                String id = item.getString("_id");
                String message = item.getString("message");

                JSONObject profile = item.getJSONObject("user");
                UserProfile userProfile = new UserProfile(
                        profile.getString("user_id"),
                        profile.getString("name"),
                        profile.getString("nickname"),
                        profile.getString("picture"),
                        profile.getString("email")
                );

                microPosts.add(new MicroPost(id, message, userProfile));
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
        }
    }
}
