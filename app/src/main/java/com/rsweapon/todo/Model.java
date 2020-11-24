package com.rsweapon.todo;

public class Model {
    private String post,title;

    public Model() {
    }

    public Model(String post, String title) {
        this.post = post;
        this.title = title;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
