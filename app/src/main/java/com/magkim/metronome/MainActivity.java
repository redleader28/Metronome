package com.magkim.metronome;

import android.gesture.Gesture;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private int MAX_BEATS = 144;
    private int MIN_BEATS = 0;

    private int BLUE = 144;
    private int RED = 144;

    private int MAX_SCROLL  = 20;
    private int MIN_SCROLL  = -20;

    private RelativeLayout getLayout () {
        return (RelativeLayout) findViewById (R.id.activity_main);
    }

    private float clipScroll (float val) {
        if (val > MAX_SCROLL)
            return MAX_SCROLL;
        if (val < MIN_SCROLL)
            return MIN_SCROLL;
        return val;
    }

    private int clipColor (float val) {
        if (val > 255)
            return 255;
        if (val < 0)
            return 0;
        return (int)val;
    }

    private GestureDetectorCompat mDetector;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState_) {
        super.onCreate(savedInstanceState_);
        setContentView(R.layout.activity_main);

        mDetector = new GestureDetectorCompat(this, this);

        getLayout ().setBackgroundColor(Color.argb(255, RED, 0, BLUE));
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {

        final float scaledValue = clipScroll (distanceY);

        getLayout ().setBackgroundColor(Color.argb(255, RED + clipColor(RED + scaledValue), 0,
                BLUE + clipColor(BLUE + scaledValue)));

        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Be sure to call the superclass implementation
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
    }

    @Override
    public void onShowPress(MotionEvent event) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        return true;
    }
}
