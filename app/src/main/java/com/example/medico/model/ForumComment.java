package com.example.medico.model;

public class ForumComment {
    private String commentid, commentBy, commentText;

    public ForumComment() {

    }

    public ForumComment(String commentid, String commentBy, String commentText) {
        this.commentid = commentid;
        this.commentBy = commentBy;
        this.commentText = commentText;
    }

    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    public String getCommentBy() {
        return commentBy;
    }

    public void setCommentBy(String commentBy) {
        this.commentBy = commentBy;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
