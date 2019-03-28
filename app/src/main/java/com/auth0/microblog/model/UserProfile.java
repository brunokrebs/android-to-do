package com.auth0.microblog.model;

public class UserProfile {
    private String id;
    private String name;
    private String nickname;
    private String pictureURL;
    private String email;

    public UserProfile(String id, String name, String nickname, String pictureURL, String email) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.pictureURL = pictureURL;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public String getEmail() {
        return email;
    }
}
