package com.aperpen.notesapp;

public class NoteModel {
    private String noteId;
    private String noteTitle;
    private String noteBody;
    private long noteDate;

    // create constructor to set the values for all the parameters of the each single view
    public NoteModel(String id, String title, String body, long date) {
        noteId = id;
        noteTitle = title;
        noteBody = body;
        noteDate = date;
    }

    public String getNoteId() {
        return noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public long getNoteDate() {
        return noteDate;
    }

    @Override
    public String toString() {
        return noteTitle;
    }
}
