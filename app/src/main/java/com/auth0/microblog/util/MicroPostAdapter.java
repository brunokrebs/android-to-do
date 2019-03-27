package com.auth0.microblog.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.auth0.microblog.R;
import com.auth0.microblog.model.MicroPost;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MicroPostAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<MicroPost> microPosts = new ArrayList<>();

    public MicroPostAdapter(Context context) {
        this.context = context;
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

        ImageView profilePicture = view.findViewById(R.id.profile_picture);
        Glide.with(context).load("https://s.gravatar.com/avatar/d1ad971613b01e221226aa2f4700267a?s=480&r=pg&d=https%3A%2F%2Fcdn.auth0.com%2Favatars%2Fsk.png").into(profilePicture);

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
        microPosts = data;
        notifyDataSetChanged();
    }
}