package com.andrewsubowo.justanothertexteditor;

import android.content.DialogInterface;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextWatcher;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

import com.andrewsubowo.justanothertexteditor.io.SaveFile;

import java.io.File;


public class EditorActivity extends AppCompatActivity {

    ScrollView scrollView;
    Editor editor;
    Boolean isEdited, savedRecently;
    Toolbar toolbar;
    SaveFile saveFile;
    String filename;
    EditorActivity editorActivity;
    File path, directory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        toolbar = (Toolbar) findViewById(R.id.toolbareditor);
        setSupportActionBar(toolbar);

        editorActivity = this;
        isEdited = false;
        savedRecently = true;

        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        directory = new File(path, "JATE");

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
                savedRecently = false;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                isEdited = true;
                savedRecently = false;
            }
        });
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

            // Detect if any changes were made
            if (isEdited && !savedRecently) {
                Log.d("DEBUG", "Displaying confirmation dialog to user.");
                AlertDialog.Builder confirmQuitDialogBuilder = new AlertDialog.Builder(this);
                confirmQuitDialogBuilder.setTitle("Quit without saving?")
                        .setMessage("Changes were made to the file. Data will be lost if you do not save.")
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Confirm the user the save action
                                saveAction();
                            }
                        })
                        .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // quit the activity without saving any text as user confirmed
                                finish();
                            }
                        })
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Don't do anything here, just return to the editing activity.
                            }
                        });
                AlertDialog dialog = confirmQuitDialogBuilder.create();
                dialog.show();
            } else {
                // No changes were detected in the editor, so quit anyway.
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
            saveAction();
        }
        return true;
    }


    public void saveAction() {

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

                File file = new File(directory, filename);

                if (file.exists()) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(editorActivity);
                    dialogBuilder.setTitle("Overwrite file?")
                            .setMessage("Are you sure you want to overwrite " + filename + "?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    saveFile = new SaveFile(editorActivity, editor, filename);
                                    saveFile.execute();

                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ScrollView view = (ScrollView) editorActivity.findViewById(R.id.scrollView);
                                    Snackbar.make(view, "Save cancelled", Snackbar.LENGTH_LONG)
                                            .show();
                                }
                            });

                    AlertDialog dialog = dialogBuilder.create();
                    dialog.show();
                }
                else {
                    // If the file doesn't already exist, we can just go ahead and save it
                    saveFile = new SaveFile(editorActivity, editor, filename);
                    saveFile.execute();
                }
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

    /**
     * If the AsyncTask sucessfully saved the file, tell this activity accordingly.
     * @param status - The status returned by the asynctask
     */
    public void setSaveStatus(boolean status) {
        savedRecently = status;
    }

    /**
     * Set the title of this activity to the file name the user gave
     * @param title - The title to name the activity.
     */
    public void setNewTitle(String title) {
        toolbar.setTitle(title);
    }

    public void setSubtitle(String subtitle) {
        toolbar.setSubtitle(subtitle);
    }
}