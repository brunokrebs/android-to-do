package com.auth0.microblog;

import android.os.Bundle;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<MicroPost>> {
    private MicroPostAdapter microPostsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        microPostsAdapter = new MicroPostAdapter(this, new ArrayList<MicroPost>());
        ListView microPostsListView = findViewById(R.id.micro_posts);
        microPostsListView.setAdapter(microPostsAdapter);
        getSupportLoaderManager().initLoader(1, null, this).forceLoad();
    }

    @Override
    public Loader<List<MicroPost>> onCreateLoader(int id, Bundle args) {
        return new MicroPostLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<MicroPost>> loader, List<MicroPost> data) {
        microPostsAdapter.setMicroPosts(data);
    }

    @Override
    public void onLoaderReset(Loader<List<MicroPost>> loader) {
        microPostsAdapter.setMicroPosts(new ArrayList<MicroPost>());
    }
}
