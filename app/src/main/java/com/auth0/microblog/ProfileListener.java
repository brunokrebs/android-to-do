package com.auth0.microblog;

import android.content.Intent;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileListener implements MenuItem.OnMenuItemClickListener {
    private AppCompatActivity parent;

    public ProfileListener(AppCompatActivity parent) {
        this.parent = parent;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent intent = new Intent(parent, MicroPostFormActivity.class);
        parent.startActivity(intent);
        return true;
    }
}
