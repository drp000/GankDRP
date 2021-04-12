package com.drp.fuli.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

/**
 * @author durui
 * @date 2021/3/11
 * @description
 */
public class PullBackLayout extends FrameLayout {

    private ViewDragHelper dragHelper;
    private PullCallBack pullCallBack;
    private int minimumFlingVelocity;

    public PullBackLayout(Context context) {
        this(context, null);
    }

    public PullBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dragHelper = ViewDragHelper.create(this, 1f / 8f, new DragCallBack());
        minimumFlingVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
    }

    public void setPullCallBack(PullCallBack callBack) {
        this.pullCallBack = callBack;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public class DragCallBack extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return 0;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return Math.max(0, top);
        }

        @Override
        public int getViewHorizontalDragRange(@NonNull View child) {
            return 0;
        }


        @Override
        public int getViewVerticalDragRange(@NonNull View child) {
            return getHeight();
        }

        @Override
        public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
            if (pullCallBack != null) {
                pullCallBack.onPullStart();
            }
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (pullCallBack != null) {
                pullCallBack.onPull((float) top / (float) getHeight());
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int slop = yvel > minimumFlingVelocity ? getHeight() / 6 : getHeight() / 3;
            if (releasedChild.getTop() > slop) {
                if (pullCallBack != null) {
                    pullCallBack.onPullCompleted();
                }
            } else {
                dragHelper.settleCapturedViewAt(0, 0);
                invalidate();
            }
        }
    }

    public interface PullCallBack {

        void onPullStart();

        void onPull(float progress);

        void onPullCompleted();
    }
}
