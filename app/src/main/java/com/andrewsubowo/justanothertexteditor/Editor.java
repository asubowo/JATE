package com.andrewsubowo.justanothertexteditor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;


/**
 * Created by asubowo on 9/1/2017.
 */

public class Editor extends android.support.v7.widget.AppCompatEditText {


    private Rect rect; //rekt
    private Paint paint;

    public Editor(Context context) {
        super(context);
    }

    public Editor(Context context, AttributeSet attrs) {
        super(context, attrs);
        rect = new Rect();
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTypeface(Typeface.MONOSPACE);
        paint.setTextSize(20);

    }

    public Editor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void onDraw(Canvas canvas) {

        int startingPoint = getBaseline(); // Starting point for the canvas to start drawing each line
        int lineCount = getLineCount();

        for (int i = 0; i< lineCount; i++) {
            int trueline = i + 1;
            canvas.drawText(String.valueOf("" + trueline), rect.left, startingPoint, paint);
            startingPoint += getLineHeight(); // Add the height of the previous line to start drawing numbers on the next line
        }
        super.onDraw(canvas);
    }

    public int getCanvasWidth(Canvas canvas) {
        return canvas.getWidth();
    }

    public void initEditor() {
        setTypeface(Typeface.MONOSPACE);
        setFocusable(true);
    }
}
