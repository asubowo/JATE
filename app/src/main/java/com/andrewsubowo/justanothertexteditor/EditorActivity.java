package com.andrewsubowo.justanothertexteditor;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextWatcher;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.andrewsubowo.justanothertexteditor.io.SaveFile;


public class EditorActivity extends AppCompatActivity {

    Editor editor;
    MainActivity mainActivity;
    Boolean isEdited;
    Toolbar toolbar;
    SaveFile saveFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        toolbar = (Toolbar) findViewById(R.id.toolbareditor);
        setSupportActionBar(toolbar);


        this.setTitle(R.string.title_editor_activity_default);
        editor = (Editor) findViewById(R.id.editor);
        editor.initEditor();


        saveFile = new SaveFile(this, editor);


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
                Context context = getApplicationContext();
                CharSequence text = "Hello toast!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
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
            saveFile.execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
