package com.andrewsubowo.justanothertexteditor;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextWatcher;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.andrewsubowo.justanothertexteditor.io.SaveFile;


public class EditorActivity extends AppCompatActivity {

    Editor editor;
    MainActivity mainActivity;
    Boolean isEdited;
    Toolbar toolbar;
    SaveFile saveFile;
    String filename, extension, textfilename;
    EditorActivity editorActivity;

    private int saveConfirmation = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        toolbar = (Toolbar) findViewById(R.id.toolbareditor);
        setSupportActionBar(toolbar);

        editorActivity = this;

        this.setTitle(R.string.title_editor_activity_default);
        editor = (Editor) findViewById(R.id.editor);
        editor.initEditor();

        editor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isEdited = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                isEdited = true;
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

            // Detect if the editor is not empty and if any changes were made
            if (!editor.isEmpty() && !isEdited) {
                
            } else {

            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save_action) {

            AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();

            final View dialogView = inflater.inflate(R.layout.savefile_dialog, null);
            saveDialog.setView(dialogView);

            saveDialog.setTitle("Save As");
            saveDialog.setMessage("Enter a filename with it's extension. Default is \".txt\"");
            saveDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    EditText filenameEditText = dialogView.findViewById(R.id.filename);
                    filename = filenameEditText.getText().toString();


                    // If there isn't any filename
                    if (!filename.contains(".")) {
                        extension = ".txt";
                        textfilename = filename;
                    } else {
                        int extensionStart = filename.lastIndexOf(".");
                        extension = filename.substring(extensionStart);
                        textfilename = filename.substring(0, extensionStart);
                    }

                    saveFile = new SaveFile(editorActivity, editor, textfilename, extension);
                    saveFile.execute();
                }
            });

            saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // nothing to do here yet
                }
            });

            AlertDialog saveConfirmation = saveDialog.create();
            saveConfirmation.show();

        }
        return true;
    }
}