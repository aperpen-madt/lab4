package com.aperpen.notesapp;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public class NotesService {
    static void fetchNotesList(Context context, List<NoteModel> dest) {
        File notesDir = context.getFilesDir();
        dest.clear();
        for (File note : Objects.requireNonNull(notesDir.listFiles())) {
            StringBuilder noteString = new StringBuilder();
            try {
                FileInputStream fis = context.openFileInput(note.getName());
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isr);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    noteString.append(line);
                }
                bufferedReader.close();
            } catch (IOException err) {
                // we should not reach here since we are iterating the dir
                // if it's another unknown error with IO we just skip the note this time
                continue;
            }

            try {
                JSONObject noteObject = new JSONObject(noteString.toString());
                NoteModel nv = new NoteModel(
                        note.getName(),
                        noteObject.getString(Constants.NOTE_TITLE_KEY),
                        noteObject.getString(Constants.NOTE_BODY_KEY),
                        noteObject.getLong(Constants.NOTE_DATE_KEY)
                );

                dest.add(nv);
            } catch (JSONException err) {
                // we should never reach here since app is only saving valid JSON
            }
        }
    }

    static boolean deleteNote(Context context, NoteModel note) {
        File dir = context.getFilesDir();
        File file = new File(dir, note.getNoteId());
        return file.delete();
    }

    static boolean createNote(Context context,String title, String body) {
        try {

            long date = System.currentTimeMillis();
        String fileName = date + ".json";

        JSONObject jsonNote = new JSONObject();
        jsonNote.put(Constants.NOTE_TITLE_KEY, title);
        jsonNote.put(Constants.NOTE_BODY_KEY, body);
        jsonNote.put(Constants.NOTE_DATE_KEY, date);

        FileOutputStream fNote = context.openFileOutput(fileName, MODE_PRIVATE);
        fNote.write(jsonNote.toString().getBytes(StandardCharsets.UTF_8));
        fNote.close();

        return true;

        } catch (Exception e) {
            Log.e("NotesService", "Error saving note", e);
            return false;
        }

    }
}
