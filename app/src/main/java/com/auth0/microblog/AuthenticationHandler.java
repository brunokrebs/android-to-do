package com.auth0.microblog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.authentication.storage.CredentialsManagerException;
import com.auth0.android.authentication.storage.SecureCredentialsManager;
import com.auth0.android.authentication.storage.SharedPreferencesStorage;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.provider.AuthCallback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class AuthenticationHandler implements AuthCallback, BaseCallback<Credentials, CredentialsManagerException> {
    private Auth0 auth0;
    private Activity originalActivity;
    private Class nextActivity;
    private SecureCredentialsManager credentialsManager;

    AuthenticationHandler(Activity originalActivity) {
        this.originalActivity = originalActivity;

        // configuring Auth0
        auth0 = new Auth0(originalActivity);
        auth0.setOIDCConformant(true);
        AuthenticationAPIClient client = new AuthenticationAPIClient(auth0);
        credentialsManager = new SecureCredentialsManager(originalActivity, client, new SharedPreferencesStorage(originalActivity));
    }

    void startAuthenticationProcess() {
        WebAuthProvider.init(auth0)
                .withScheme("to-do")
                .withScope("openid profile email offline_access")
                .withAudience(String.format("https://%s/userinfo", originalActivity.getString(R.string.com_auth0_domain)))
                .start(originalActivity, this);
    }

    void refreshCredentials(Class nextActivity) {
        this.nextActivity = nextActivity;
        credentialsManager.getCredentials(this);
    }

    boolean hasValidCredentials() {
        return this.credentialsManager.hasValidCredentials();
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
        credentialsManager.saveCredentials(credentials);
        originalActivity.startActivity(new Intent(originalActivity, nextActivity));
    }

    @Override
    public void onFailure(CredentialsManagerException error) {
        startAuthenticationProcess();
    }
}
