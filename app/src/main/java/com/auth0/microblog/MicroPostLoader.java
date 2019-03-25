package com.auth0.microblog;

import android.content.Context;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

public class MicroPostLoader extends AsyncTaskLoader<List<MicroPost>> {
    public MicroPostLoader(Context context) {
        super(context);
    }

    @Override
    public List<MicroPost> loadInBackground() {
        List<MicroPost> list = new ArrayList<MicroPost>();
        list.add(new MicroPost("emp1", "Brahma"));
        list.add(new MicroPost("emp2", "Vishnu"));
        list.add(new MicroPost("emp3", "Mahesh"));
        return list;
    }
}