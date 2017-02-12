package com.magkim.metronome;

import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements GestureDetector.OnGestureListener {

    private Timer mTimer;
    private ToneGenerator mToneGenerator = new ToneGenerator(AudioManager.STREAM_ALARM, 50);
    private Boolean mTimerStarted = false;

    private float BEATS     = 60;         // Default
    private float MAX_BEATS = 208;
    private float MIN_BEATS = 40;
    private float BEAT_COLOR_SCALE  = 255 / MAX_BEATS;
    private float MAX_SCROLL = 20;

    private void setBackground ()
    {
        final int coefficient = Math.round(BEATS * BEAT_COLOR_SCALE);

        final int blue = 255 - coefficient;
        final int red = coefficient;

        ((TextView) findViewById(R.id.bpm)).setText(String.valueOf((int)BEATS));
        mLayout.setBackgroundColor(Color.argb(255, red, 0, blue));
    }

    private GestureDetectorCompat mDetector;
    private RelativeLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState_) {
        super.onCreate(savedInstanceState_);

        setContentView(R.layout.activity_main);

        mDetector = new GestureDetectorCompat(this, this);
        mLayout = (RelativeLayout) findViewById (R.id.activity_main);

        setBackground ();
    }

    @Override
    public boolean onScroll(
            MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        if (distanceY > MAX_SCROLL)
            distanceY = MAX_SCROLL;
        else if (distanceY < -MAX_SCROLL)
            distanceY = -MAX_SCROLL;

        BEATS += distanceY;

        if (BEATS < MIN_BEATS)
            BEATS = MIN_BEATS;
        else if (BEATS > MAX_BEATS)
            BEATS = MAX_BEATS;

        setBackground();
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

    public void handleButtonClicked(View view) {
        Button button = (Button)findViewById(R.id.button);

        if (mTimerStarted) {
            mTimer.cancel();
            button.setText("Start");
        }
        else {
            mTimer = new Timer();
            mTimer.scheduleAtFixedRate(new TimerTask() {
                                           @Override
                                           public void run() {
                                               mToneGenerator.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 100);
                                           }
                                       }, 1000, (int)(BEATS) * 100);
            button.setText("Stop");
        }

        mTimerStarted = ! mTimerStarted;
    }
}
