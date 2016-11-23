package com.example.mrh.colorchangewithdragging;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by MR.H on 2016/11/23 0023.
 */

public class Circle extends View{
    private static final String TAG = "Circle";
    private Context context;

    private int mMeasuredWidth;
    private int mMeasuredHeight;
    private Paint mPaint;
    private float mRadius;
    private boolean hasStatusBarHeight = false;
    private int mStatusBarHeight;
    private int mX;
    private int mY;
    private boolean hasChange = false;

    public Circle (Context context) {
        this(context, null);
    }

    public Circle (Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Circle (Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Circle);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++){
            int index = typedArray.getIndex(i);
            if (index == R.styleable.Circle_radius_){
                mRadius = typedArray.getDimension(i, 60);
            }
        }
        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMeasuredWidth = getMeasuredWidth();
        mMeasuredHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mMeasuredWidth/2, mMeasuredHeight/2, mRadius, mPaint);
    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {

        //获取状态栏高度,屏幕宽高
        if (!hasStatusBarHeight){
            hasStatusBarHeight = true;
            try {
                Class<?> clazz = Class.forName("com.android.internal.R$dimen");
                Object object = clazz.newInstance();
                int height = Integer.parseInt(clazz.getField("status_bar_height")
                        .get(object).toString());
                mStatusBarHeight = getResources().getDimensionPixelSize(height);
                Log.d(TAG, "height: "+ mStatusBarHeight);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Activity activity = (Activity) this.context;
            Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
            Point point = new Point();
            defaultDisplay.getSize(point);
            mX = point.x;
            mY = point.y;
            Log.d(TAG, "x: "+ mX);
            Log.d(TAG, "y: "+ mY);
        }

        int x1 = mMeasuredWidth/2;
        int y1 = mMeasuredHeight/2;
        switch (event.getAction()){
        case MotionEvent.ACTION_DOWN:
//            x1 = (int) event.getX();
//            y1 = (int) event.getY();
            break;
        case MotionEvent.ACTION_MOVE:
            int x = (int) event.getRawX();
            int y = (int) event.getRawY();

            int dx = x - x1;
            int dy = y - y1;
            if (dx <= 0 || dx >= mX-mMeasuredWidth || dy <= mStatusBarHeight || dy >=
                    mY-mMeasuredHeight){
                if (dx <= 0){
                    dx = 0;
                    setRandomColor();
                } else if (dx >= mX-mMeasuredWidth){
                    dx = mX-mMeasuredWidth;
                    setRandomColor();
                }

                if (dy <= mStatusBarHeight){
                    dy = mStatusBarHeight;
                    setRandomColor();
                } else if (dy >= mY-mMeasuredHeight){
                    dy = mY-mMeasuredHeight;
                    setRandomColor();
                }
            }else {
                hasChange = false;
            }


            layout(dx, dy-mStatusBarHeight, dx+mMeasuredWidth,
                    dy+mMeasuredHeight-mStatusBarHeight);
            break;
        case MotionEvent.ACTION_UP:

            break;
        }
        return true;
    }

    private void setRandomColor () {
        if (!hasChange){
            hasChange = true;
            double random;
            int[] color = new int[3];
            for (int i = 0; i < 3; i++){
                random = Math.random();
                color[i] = (int) (random * 230);
            }
            mPaint.setARGB(200, color[0],color[1], color[2]);
            invalidate();
        }


    }
}
