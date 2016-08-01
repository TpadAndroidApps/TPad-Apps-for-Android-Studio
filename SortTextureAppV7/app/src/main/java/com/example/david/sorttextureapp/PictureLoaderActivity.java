package com.example.david.sorttextureapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.tpadlib.TPad;
import com.example.tpadlib.TPadImpl;
import com.example.tpadlib.views.FrictionMapView;

import java.util.ArrayList;

public class PictureLoaderActivity extends AppCompatActivity {
    private static final int INITIAL_HIDE_DELAY = 2000;
    private View mDecorView;

    // TPad object defined in TPadLib
    private FrictionMapView mFrictionMapView;
    private static TPad mTpad;

    int[] textureImages = {R.drawable.image1,R.drawable.image2,R.drawable.image3,R.drawable.image4,R.drawable.image5,R.drawable.image6,R.drawable.image7,R.drawable.image8,R.drawable.image9,R.drawable.image10};
    int[] receivedOrder = new int[10];
    int receiveCurrentImage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_loader);

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.frictionView);

        // Data passed from ListArrangeActivity
        receiveCurrentImage = getIntent().getIntExtra("currentImage",0);
        //System.out.println("current "+receiveCurrentImage);
        ArrayList<String> data = getIntent().getStringArrayListExtra("passToFullActivity");
        for (int i = 0; i < data.size(); i++) {
            try {
                receivedOrder[i] = Integer.parseInt(data.get(i));
                //System.out.println("order " + receivedOrder[i]);
            } catch (NumberFormatException nfe) {};
        }

        mTpad = new TPadImpl(this);
        mFrictionMapView = (FrictionMapView) findViewById(R.id.frictionView);
        mFrictionMapView.setTpad(mTpad);

        // Reset the bitmap of the FrictionMapView with current user click image
        Bitmap mImage = BitmapFactory.decodeResource(getResources(),textureImages[receivedOrder[receiveCurrentImage]-1]);
        if (mImage != null) {
            Log.d("TAG","mImage loaded");
            mFrictionMapView.setDataBitmap(mImage);
            mImage.recycle();
        }
        else {Log.d("TAG","mImage loaded FAIL");}

        mDecorView = getWindow().getDecorView();
        mDecorView.setOnSystemUiVisibilityChangeListener(
                new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int flags) {
                        boolean visible = (flags & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0;
                        controlsView.animate()
                                .alpha(visible ? 1 : 0)
                                .translationY(visible ? 0 : controlsView.getHeight());
                    }
                });
        contentView.setClickable(true);
        final GestureDetector clickDetector = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        boolean visible = (mDecorView.getSystemUiVisibility()
                                & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0;
                        if (visible) {
                            hideSystemUI();
                        } else {
                            showSystemUI();
                        }
                        return true;
                    }
                });
        contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return clickDetector.onTouchEvent(motionEvent);
            }
        });

        showSystemUI();
        nextBtnClick();
        prevBtnClick();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        // When the window loses focus (e.g. the action overflow is shown),
        // cancel any pending hide action. When the window gains focus,
        // hide the system UI.
        if (hasFocus) {
            delayedHide(INITIAL_HIDE_DELAY);
        } else {
            mHideHandler.removeMessages(0);
        }
    }

    private void hideSystemUI() {
        mDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    private void showSystemUI() {
        mDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    private final Handler mHideHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            hideSystemUI();
        }
    };

    private void delayedHide(int delayMillis) {
        mHideHandler.removeMessages(0);
        mHideHandler.sendEmptyMessageDelayed(0, delayMillis);
    }

    @Override
    protected void onDestroy() {
        mTpad.disconnectTPad();
        super.onDestroy();
    }

    public void nextBtnClick() {
        Button nextBtn = (Button) findViewById(R.id.button3);
        nextBtn.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        receiveCurrentImage++;
                        if(receiveCurrentImage < receivedOrder.length) {
                            Bitmap mImage = BitmapFactory.decodeResource(getResources(),textureImages[receivedOrder[receiveCurrentImage]-1]);
                            mFrictionMapView.setDataBitmap(mImage);
                            mImage.recycle();
                        }
                        else {
                            receiveCurrentImage = 0;
                            Bitmap mImage = BitmapFactory.decodeResource(getResources(),textureImages[receivedOrder[receiveCurrentImage]-1]);
                            mFrictionMapView.setDataBitmap(mImage);
                            mImage.recycle();

                        }
                    }
                }
        );
    }

    public void prevBtnClick() {
        Button prevBtn = (Button) findViewById(R.id.button1);
        prevBtn.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        receiveCurrentImage--;
                        if (receiveCurrentImage < 0) {
                            receiveCurrentImage = receivedOrder.length-1;
                            Bitmap mImage = BitmapFactory.decodeResource(getResources(),textureImages[receivedOrder[receiveCurrentImage]-1]);
                            mFrictionMapView.setDataBitmap(mImage);
                            mImage.recycle();
                        }
                        else {
                            Bitmap mImage = BitmapFactory.decodeResource(getResources(),textureImages[receivedOrder[receiveCurrentImage]-1]);
                            mFrictionMapView.setDataBitmap(mImage);
                            mImage.recycle();
                        }
                    }
                }
        );
    }

    public void ShowGrid(View view) {
        super.onBackPressed();
    }

}
