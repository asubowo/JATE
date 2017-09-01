package com.andrewsubowo.justanothertexteditor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import us.feras.mdv.MarkdownView;

/**
 * @Author Andrew Subowo
 * <p>
 * This activity utilizes the MarkdownView library created by falnatsheh@github
 */

public class MarkdownViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        MarkdownView markdownView = new MarkdownView(this);
        setContentView(markdownView);
        markdownView.loadMarkdown("## Hello Markdown");

    }
}
