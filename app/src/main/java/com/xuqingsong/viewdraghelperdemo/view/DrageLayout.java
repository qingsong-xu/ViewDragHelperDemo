package com.xuqingsong.viewdraghelperdemo.view;

import com.xuqingsong.viewdraghelperdemo.R;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 自定义使用ViewDrageHelper:
 * 1、使用create方法获取ViewDrageHelper实例对象
 * 2、需要此view中的子view执行什么动作，在传入的CallBack方法中实现
 */

public class DrageLayout extends LinearLayout {

    private ViewDragHelper mViewDragHelper;

    private View mDrageView1, mDrageView2;

    public DrageLayout(Context context) {
        this(context, null, 0);
    }

    public DrageLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * 第一个参数是指ViewDrageHelper所依赖的父view容器，ViewDrageHelper不是作用于这个父view，而是作用于这个父view中的子view，
         * 第二个参数 0-1，值越大，拖动时约灵敏
         * 第三个参数Callback，需要被拖动的子view的行为，在此回调的方法中定义实现
         */
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new MyViewDrageCallback());
    }

    /**
     * 填充完成时调用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDrageView1 = this.findViewById(R.id.drage_view1);
        mDrageView2 = this.findViewById(R.id.drage_view2);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int motionEvent = MotionEventCompat.getActionMasked(ev);
        if (motionEvent == MotionEvent.ACTION_CANCEL || motionEvent == MotionEvent.ACTION_UP) {
            mViewDragHelper.cancel();
            return false;
        }
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    /**
     * 这里的返回值必须为true，否则事件会向上抛，最终用activity的onTouchEvent进行处理。达不到想要的效果。
     * 写到这里值得提醒的是：view位置移动了，必须调用invalidate()重绘，否则view是不会有动的效果的
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    /*
    Process a touch event received by the parent view.
    This method will dispatch callback events as needed before returning.
    The parent view's onTouchEvent implementation should call this.
     */
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    public enum Direction {
        Veritcal, Horizontal, Youtube
    }

    class MyViewDrageCallback extends ViewDragHelper.Callback {

        /**
         * 返回true，代表运行被拖拽，false怎禁止拖动
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        /**
         * 控制水平拖动
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //实现了mDrageView1被水平拖动，下面这段代码其实是限制mDrageView1能移动的范围，不让其超出边界。
            // 返回值newHorizontalDistance就是此时该view的x水平位置坐标，注意，不要忘了在onViewPositionChanged方法中调用invalidate（）方法重绘
            if (child.equals(mDrageView1)) {
                int leftBound = getLeft();
                int rightBound = getRight() - mDrageView1.getWidth();
                int newHorizontalDistance = Math
                        .min(Math.max(leftBound, left), rightBound);

                return newHorizontalDistance;
            }
            return super.clampViewPositionHorizontal(child, left, dx);
        }

        /**
         * 控制垂直拖动
         */
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            //原理参考clampViewPositionHorizontal方法。
            if (child.equals(mDrageView2)) {
                int topBound = getTop();
                int bottomBound = getBottom() - mDrageView2.getHeight();
                int newVerticalDistance = Math
                        .min(Math.max(topBound, top), bottomBound);

                return newVerticalDistance;
            }
            return super.clampViewPositionVertical(child, top, dy);
        }

        /**
         * 拖动过程中view位置改变
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            //位置坐标改变了，就必须重绘view
            invalidate();
        }

        /**
         * view拖动时，状态的改变
         */
        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }
    }
}
