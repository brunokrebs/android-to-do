package com.auth0.microblog;

public class MicroPost {
    private final String id;
    private final String message;

    public MicroPost(String _id, String message) {
        this.id = _id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
