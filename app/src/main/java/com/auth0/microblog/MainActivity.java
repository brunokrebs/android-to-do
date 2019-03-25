package com.auth0.microblog;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    public static final String NEW_MESSAGE = "com.auth0.microblog.NEW_MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Called when the user taps the Send button
     */
    public void sendMessage(View view) {
        String url = "http://10.0.2.2:3001";
        final Context context = this;
        RequestQueue queue = Volley.newRequestQueue(context);
        EditText editText = findViewById(R.id.editText);
        String message = editText.getText().toString();

        try {
            JSONObject jo = new JSONObject();
            jo.put("message", message);

            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jo,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            startActivity(new Intent(context, DisplayMessageActivity.class));
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            new AlertDialog.Builder(context)
                                    .setTitle("Error")
                                    .setMessage(error.getMessage())
                                    .show();
                        }
                    }
            );

            // Add the request to the RequestQueue.
            queue.add(postRequest);
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
    }
}
