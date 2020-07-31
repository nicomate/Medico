package com.example.medico.model;

public class NoteModel {
    private String author, note_title, note_data;

    public NoteModel(String author, String note_title, String note_data) {
        this.author = author;
        this.note_title = note_title;
        this.note_data = note_data;
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

}

