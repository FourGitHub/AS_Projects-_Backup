package com.learn.four.gesturesample;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_1)
    Button btn1;
    private static final String TAG = "MainActivity";
    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
//            @Override
//            public boolean onSingleTapConfirmed(MotionEvent e) {
//                Log.i(TAG, "--->> onSingleTapConfirmed");
//                return super.onSingleTapConfirmed(e);
//            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                tv.append("GestureListener - DoubleTap\n");
                Log.i(TAG, "--->> onDoubleTap");
                return super.onDoubleTap(e);
            }
//
//            @Override
//            public boolean onDoubleTapEvent(MotionEvent e) {
//
//                switch (e.getAction()) {
//                    case 0:
//                        Log.i(TAG, "--->> ACTION_DOWN");
//                        break;
//                    case 1:
//                        Log.i(TAG, "--->> ACTION_UP");
//                        break;
//                    case 2:
//                        Log.i(TAG, "--->> ACTION_MOVE");
//                        break;
//                }
//
//                return true;
//            }
//
//            @Override
//            public boolean onSingleTapUp(MotionEvent e) {
//                Log.i(TAG, "--->> onSingleTapUp");
//                return super.onSingleTapUp(e);
//            }
//
//            @Override
//            public void onLongPress(MotionEvent e) {
//                super.onLongPress(e);
//                Log.i(TAG, "--->> onLongPress");
//            }
        };
        GestureDetector gestureDetector = new GestureDetector(this, simpleOnGestureListener);
        btn1.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));

    }

    @OnClick(R.id.btn_1)
    public void onViewClicked() {
        tv.append("onClickListener\n");
    }
}
