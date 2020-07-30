package com.example.medico.model;

public class NoteModel {
    private String id;
    private String author;
    private String note_title;
    private String note_data;
    private String created_at;

    public NoteModel(String id, String author, String note_title, String note_data, String created_at) {
        this.id = id;
        this.author = author;
        this.note_title = note_title;
        this.note_data = note_data;
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNote_title() {
        return note_title;
    }

    public void setNote_title(String note_title) {
        this.note_title = note_title;
    }

    public String getNote_data() {
        return note_data;
    }

    public void setNote_data(String note_data) {
        this.note_data = note_data;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
