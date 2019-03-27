package com.auth0.microblog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MicroPostAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<MicroPost> microPosts;

    public MicroPostAdapter(Context context) {
        microPosts = new ArrayList<>();
        microPosts.add(new MicroPost("emp1", "Brahma"));
        microPosts.add(new MicroPost("emp2", "Vishnu"));
        microPosts.add(new MicroPost("emp3", "Mahesh"));
        notifyDataSetChanged();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        MicroPost microPost = (MicroPost) getItem(position);
        if (view == null) {
            view = inflater.inflate(R.layout.micro_post_data, null);
        }

        TextView message = view.findViewById(R.id.micro_post_message);
        message.setText(microPost.getMessage());
        return view;
    }

    @Override
    public Object getItem(int position) {
        return microPosts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return microPosts.size();
    }

    void setMicroPosts(List<MicroPost> data) {
        microPosts.addAll(data);
        notifyDataSetChanged();
    }
}