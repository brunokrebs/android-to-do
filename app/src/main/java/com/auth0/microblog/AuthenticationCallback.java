package com.auth0.microblog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;

import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.provider.AuthCallback;
import com.auth0.android.result.Credentials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class AuthenticationCallback implements AuthCallback {
    private Activity originalActivity;
    private Class nextActivity;

    AuthenticationCallback(Activity originalActivity, Class nextActivity) {
        this.originalActivity = originalActivity;
        this.nextActivity = nextActivity;
    }

    @Override
    public void onFailure(@NonNull final Dialog dialog) {
        originalActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        });
    }

    @Override
    public void onFailure(AuthenticationException exception) {
        new AlertDialog.Builder(originalActivity)
                .setTitle("Authentication Error")
                .setMessage(exception.getMessage())
                .show();
    }

    @Override
    public void onSuccess(@NonNull Credentials credentials) {
        originalActivity.startActivity(new Intent(originalActivity, nextActivity));
    }
}
