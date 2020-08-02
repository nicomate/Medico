package com.example.medico.model;

public class ForumPost {
    private String postId, postedBy, postQuestion, postBody;

    public ForumPost(){

    }

    public ForumPost(String postId, String postedBy, String postQuestion, String postBody) {
        this.postId = postId;
        this.postedBy = postedBy;
        this.postQuestion = postQuestion;
        this.postBody = postBody;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getPostQuestion() {
        return postQuestion;
    }

    public void setPostQuestion(String postQuestion) {
        this.postQuestion = postQuestion;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }
}
