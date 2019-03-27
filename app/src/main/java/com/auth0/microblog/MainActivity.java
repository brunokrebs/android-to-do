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

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONArray>, Response.ErrorListener {
    private MicroPostAdapter microPostsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set activity view
        setContentView(R.layout.activity_main);
        ListView microPostsListView = findViewById(R.id.micro_posts);
        microPostsListView.setAdapter(microPostsAdapter);

        // create the adapter
        microPostsAdapter = new MicroPostAdapter(this);


//        microPostsAdapter.setMicroPosts(list);

        // issue the request
//        String url = "http://10.0.2.2:3001";
//        RequestQueue queue = Volley.newRequestQueue(this);
//        JsonArrayRequest microPostsRequest = new JsonArrayRequest(url, this, this);
//        queue.add(microPostsRequest);
    }

    /**
     * Called when the user taps the Send button
     */
    public void sendMessage(View view) {
        startActivity(new Intent(this, MicroPostFormActivity.class));
    }

    @Override
    public void onResponse(JSONArray response) {
//        List<MicroPost> list = new ArrayList<>();
//        list.add(new MicroPost("emp1", "Brahma"));
//        list.add(new MicroPost("emp2", "Vishnu"));
//        list.add(new MicroPost("emp3", "Mahesh"));
//        microPostsAdapter.setMicroPosts(new ArrayList<MicroPost>());
//        new AlertDialog.Builder(this)
//                .setTitle("Message")
//                .setMessage("List loaded")
//                .show();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
//        microPostsAdapter.setMicroPosts(new ArrayList<MicroPost>());
//        new AlertDialog.Builder(this)
//                .setTitle("Error")
//                .setMessage(error.getMessage())
//                .show();
    }
}
