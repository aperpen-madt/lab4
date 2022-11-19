package com.aperpen.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<NoteModel> notesList = new ArrayList<>();
    NoteViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab_add_note);
        fab.setOnClickListener(v -> openAddNoteActivity());

        ListView lvNotes = findViewById(R.id.lvNotes);
        adapter = new NoteViewAdapter(this, notesList);
        lvNotes.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        NotesService.fetchNotesList(this, notesList);

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_add_note) {
            openAddNoteActivity();
            return true;
        } else if (itemId == R.id.menu_delete_notes) {
            openDeleteNoteActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void openAddNoteActivity() {
        Intent i = new Intent(this, AddNoteActivity.class);
        startActivity(i);
    }

    protected void openDeleteNoteActivity() {
        Intent i = new Intent(this, DeleteNoteActivity.class);
        startActivity(i);
    }
}