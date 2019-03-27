package com.auth0.microblog;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class AuthAwareActivity extends AppCompatActivity {
    protected AuthenticationHandler authenticationHandler;
    protected Menu menu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.authenticationHandler = new AuthenticationHandler(this);

    }

    protected void refreshMenu() {
        MenuItem firstOption = menu.findItem(R.id.first_action);
        MenuItem secondOption = menu.findItem(R.id.second_action);

        if (!authenticationHandler.hasValidCredentials()) {
            // reconfiguring first button
            firstOption.setTitle(R.string.login);
            firstOption.setOnMenuItemClickListener(new LoginListener(authenticationHandler));

            // hiding second button
            secondOption.setVisible(false);
        } else {
            // reconfiguring first button
            firstOption.setTitle(R.string.profile);
            firstOption.setOnMenuItemClickListener(new ProfileListener(this));

            // reconfiguring second button
            secondOption.setTitle(R.string.logout);
            secondOption.setOnMenuItemClickListener(new LogoutListener(authenticationHandler));
            secondOption.setVisible(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        this.menu = menu;
        refreshMenu();

        return true;
    }
}
