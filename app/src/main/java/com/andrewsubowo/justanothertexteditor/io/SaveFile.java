package com.andrewsubowo.justanothertexteditor.io;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.Snackbar;
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

/**
 * Created by asubowo on 9/2/2017.
 */

public class SaveFile extends AsyncTask<String, Void, Void> {

    private EditorActivity editorActivity;
    private Editor editor;

    /**
     * Constructor class for the SaveFile class
     *
     * @param editorActivity - Pass in the EditorActivity to this class
     * @param editor         - Pass in the Editor class
     */
    public SaveFile(EditorActivity editorActivity, Editor editor) {
        this.editorActivity = editorActivity;
        this.editor = editor;
        System.out.println(editorActivity.getApplicationContext().getFilesDir().toURI().toString());
    }


    @Override
    protected Void doInBackground(String... strings) {

        //String filename = strings[0];
       // String extension = strings[1];

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File directory = new File(path, "JATE");

        if (!directory.exists()) {
            directory.mkdir();
        }

        System.out.println(directory.toURI().toString());


        return null;
    }


    protected Void onPostExecute(Void... result) {

        ScrollView view = (ScrollView) editorActivity.findViewById(R.id.scrollView);

        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        return null;
    }
}
