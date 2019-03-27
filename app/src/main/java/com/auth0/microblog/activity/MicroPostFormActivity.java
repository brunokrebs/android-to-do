package com.auth0.microblog.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.auth0.microblog.R;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AlertDialog;

public class MicroPostFormActivity extends AuthAwareActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_micro_post_form);
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
            jo.put("title", message);

            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jo,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            startActivity(new Intent(context, MainActivity.class));
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
