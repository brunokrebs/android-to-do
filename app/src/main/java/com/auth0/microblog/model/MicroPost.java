package com.auth0.microblog.model;

public class MicroPost {
    private final String id;
    private final String message;
    private final UserProfile userProfile;

    public MicroPost(String _id, String message, UserProfile userProfile) {
        this.id = _id;
        this.message = message;
        this.userProfile = userProfile;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }
}
