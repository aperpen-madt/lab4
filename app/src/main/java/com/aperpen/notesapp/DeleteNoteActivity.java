package com.aperpen.notesapp;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DeleteNoteActivity extends AppCompatActivity {
    ArrayList<NoteModel> notesList = new ArrayList<>();
    ArrayAdapter<NoteModel> adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);
        setTitle(R.string.title_delete_notes);

        FloatingActionButton fab = findViewById(R.id.fab_delete_notes);
        fab.setOnClickListener(v -> deleteSelected());

        listView = findViewById(R.id.lvDeleteNotes);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice,
                notesList);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    protected void deleteSelected() {
        SparseBooleanArray selected = listView.getCheckedItemPositions();
        int deletedCount = 0;
        if (selected != null) {
            for (int i = 0; i < selected.size(); i++) {
                if (selected.valueAt(i)) {
                    NoteModel note = (NoteModel) listView.getItemAtPosition(i);
                    if (NotesService.deleteNote(this, note)) {
                        deletedCount++;
                    }
                }
            }
        }

        if (deletedCount > 0) {
            Toast.makeText(
                    this,
                    String.format(getString(R.string.deleted_notes), deletedCount),
                    Toast.LENGTH_LONG
            ).show();
        }
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        NotesService.fetchNotesList(this, notesList);

        adapter.notifyDataSetChanged();
    }

}