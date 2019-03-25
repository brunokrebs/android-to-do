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
    private List<MicroPost> microPosts = new ArrayList<>();

    public MicroPostAdapter(Context context, List<MicroPost> microPosts) {
        this.microPosts = microPosts;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        MicroPost microPost = (MicroPost) getItem(position);
        if (view == null) {
            view = inflater.inflate(R.layout.micro_post_data, null);
        }

        TextView id = view.findViewById(R.id.micro_post_id);
        id.setText(microPost.getId());

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

    public void setMicroPosts(List<MicroPost> data) {
        microPosts.addAll(data);
        notifyDataSetChanged();
    }
}