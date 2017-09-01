package com.andrewsubowo.justanothertexteditor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.EditText;

public class EditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
    }


    public class Editor extends EditText {

        private Rect rect; //rekt
        private Paint paint;

        public Editor(Context context, AttributeSet attrs) {
            super(context, attrs);
            rect = new Rect();
            paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLACK);
            paint.setTextSize(20);
        }

        public void onDraw(Canvas canvas) {

            int startingPoint = getBaseline(); // Starting point for the canvas to start drawing each line
            int lineCount = getLineCount();

            for (int i = 0; i< lineCount; i++) {
                canvas.drawText(String.valueOf("" + i), rect.left, startingPoint, paint);
                startingPoint += getLineHeight(); // Add the height of the previous line to start drawing numbers on the next line
            }
            super.onDraw(canvas);
        }

        public void initEditor() {
            setFocusable(true);
        }
    }
}
