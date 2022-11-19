package com.aperpen.notesapp;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddNoteActivity extends AppCompatActivity {
    TextInputEditText titleInput;
    EditText bodyInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        setTitle(R.string.title_add_note);

        titleInput = findViewById(R.id.etTitle);
        bodyInput = findViewById(R.id.etBody);

        FloatingActionButton fab = findViewById(R.id.fabSaveNote);
        fab.setOnClickListener(v -> saveNote());
    }

    protected void saveNote() {
        String title = titleInput.getText().toString();
        String body = bodyInput.getText().toString();

        if (title.isEmpty() || body.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_empty_fields),
                    Toast.LENGTH_LONG).show();
        } else {
                String result;
                if (NotesService.createNote(this, title, body)) {
                    result = getString(R.string.note_save_success);
                } else {
                    result = getString(R.string.error_save_note);
                }

                Toast.makeText(getBaseContext(), result, Toast.LENGTH_SHORT).show();
                finish();
        }
    }
}