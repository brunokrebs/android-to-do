package com.auth0.microblog.util;

import android.view.MenuItem;

import com.auth0.microblog.identity.AuthenticationHandler;

public class LogoutListener implements MenuItem.OnMenuItemClickListener {
    private AuthenticationHandler authenticationHandler;

    public LogoutListener(AuthenticationHandler authenticationHandler) {
        this.authenticationHandler = authenticationHandler;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        authenticationHandler.logout();
        return true;
    }
}
