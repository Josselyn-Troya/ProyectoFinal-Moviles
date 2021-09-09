package com.example.socialmedia.models;

public class User {

    private String id;
    private String email;
    private String username;
    private String imageProfile;
    private String imageCover;
    private long timestamp;

    public User(){

    }

    public User(String id, String email, String username, String password, long timestamp, String imageProfile, String imageCover) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.timestamp= timestamp;
        this.imageProfile = imageProfile;
        this.imageCover = imageCover;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }

    public String getImageCover() {
        return imageCover;
    }

    public void setImageCover(String imageCover) {
        this.imageCover = imageCover;
    }
}
