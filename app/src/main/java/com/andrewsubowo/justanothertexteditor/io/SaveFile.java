package com.andrewsubowo.justanothertexteditor.io;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.widget.ScrollView;

import com.andrewsubowo.justanothertexteditor.Editor;
import com.andrewsubowo.justanothertexteditor.EditorActivity;
import com.andrewsubowo.justanothertexteditor.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by asubowo on 9/2/2017.
 */

public class SaveFile extends AsyncTask<Void, Void, Boolean> {

    private EditorActivity editorActivity;
    private Editor editor;
    private String filen;
    private File path, directory, file;
    private boolean quit;


    /**
     * Constructor class for the SaveFile class
     *
     * @param editorActivity - Pass in the EditorActivity to this class
     * @param editor         - Pass in the Editor class
     * @param filename       - Pass in the filename
     *
     */
    public SaveFile(EditorActivity editorActivity, Editor editor, String filename) {
        this.editorActivity = editorActivity;
        this.editor = editor;
        this.filen = filename;
        this.quit = quit;
        System.out.println(editorActivity.getApplicationContext().getFilesDir().toURI().toString());

        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        directory = new File(path, "JATE");
        file = new File(directory, filen);
    }

    // Keeping this in case for the future
    protected void onPreExecute() {
        ScrollView view = (ScrollView) editorActivity.findViewById(R.id.scrollView);
        Snackbar.make(view, "Saving file...", Snackbar.LENGTH_LONG)
                .show();


        // If the directory to save the file doesn't exist, create it
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        try {
            if (!isCancelled()) {
                FileOutputStream fos = new FileOutputStream(file);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
                outputStreamWriter.write(editor.getWrittenText());
                outputStreamWriter.flush();
                outputStreamWriter.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        ScrollView view = (ScrollView) editorActivity.findViewById(R.id.scrollView);
        // If we're able to save the file
        if (result) {
            Snackbar.make(view, filen + " has been saved", Snackbar.LENGTH_LONG)
                    .show();
            editorActivity.setSaveStatus(true);
            editorActivity.setNewTitle(filen);
            Date date = Calendar.getInstance().getTime();
            DateFormat df = new SimpleDateFormat("hh:mm:ss");

            editorActivity.setSubtitle("Saved " + df.format(date));
        } else {
            Snackbar.make(view, "The file was unable to be saved", Snackbar.LENGTH_LONG)
                    .show();
            editorActivity.setSaveStatus(false);
        }


    }
}

