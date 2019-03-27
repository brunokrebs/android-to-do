package com.auth0.microblog;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.provider.AuthCallback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONArray>, Response.ErrorListener {
    private MicroPostAdapter microPostsAdapter;

    private Auth0 auth0;

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

        // configuring Auth0
        auth0 = new Auth0(this);
        auth0.setOIDCConformant(true);
    }

    /**
     * Called when the user taps the Send button
     */
    public void sendMessage(View view) {
        startActivity(new Intent(this, MicroPostFormActivity.class));
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

    public void login(View view) {
        WebAuthProvider.init(auth0)
                .withScheme("to-do")
                .withAudience(String.format("https://%s/userinfo", getString(R.string.com_auth0_domain)))
                .start(MainActivity.this, new AuthCallback() {
                    @Override
                    public void onFailure(@NonNull final Dialog dialog) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(final AuthenticationException exception) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Error: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onSuccess(@NonNull final Credentials credentials) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, credentials.getAccessToken(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }
}
