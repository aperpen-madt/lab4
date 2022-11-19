package com.aperpen.notesapp;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class NoteViewAdapter extends ArrayAdapter<NoteModel> {

    public NoteViewAdapter(Context context, ArrayList<NoteModel> notesList) {
        super(context, R.layout.list_entry_note, notesList);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View noteView = view;

        // If view is not recyclable create one
        if (noteView == null) {
            noteView = LayoutInflater.from(getContext()).inflate(R.layout.list_entry_note, parent,
                    false);
        }

        NoteModel currentNote = getItem(position);
        TextView noteTitle = noteView.findViewById(R.id.note_title);
        TextView noteBody = noteView.findViewById(R.id.note_body);
        TextView noteDate = noteView.findViewById(R.id.note_date);

        SimpleDateFormat dateFormat = new SimpleDateFormat("H:m dd/MM/yyyy", Locale.getDefault());
        String date = dateFormat.format(currentNote.getNoteDate());

        noteTitle.setText(currentNote.getNoteTitle());
        noteBody.setText(currentNote.getNoteBody());
        noteDate.setText(date);

        return noteView;
    }
}
