package com.example.myapplication.slidingup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.OverScroller;
import android.widget.ScrollView;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yuechou.zhang
 * @since 2021/7/14
 */
public class NewSlideUpView extends FrameLayout {
    /**
     * 主Content，首先展示的布局
     */
    private View mainContent;
    /**
     * 屏下布局
     */
    private View underView;

    private ImageView overlayView;

    /**
     * 屏幕宽度
     */
    private int screenWidth;

    /**
     * 屏幕高度，包括导航栏
     */
    private int screenHeight;

    @STATUS
    private int status;

    private int scrollStatus;

    private static final int BASE_SETTLE_DURATION = 256; // ms
    private static final int MAX_SETTLE_DURATION = 600; // ms

    private OverScroller overScroller;

    private float mPrevMotionY;

    private float mPrevMotionX;

    private float mInitialMotionX;

    private float mInitialMotionY;

    private boolean mIsScrollableViewHandlingTouch = false;

    private float offsetY;
    private float offsetX;
    private boolean slideUpEnable;

    @IntDef({STATUS_COLLAPSED, STATUS_EXPAND})
    @Retention(RetentionPolicy.SOURCE)
    @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
    public @interface STATUS {
    }

    //panel status
    public static final int STATUS_COLLAPSED = 0;
    public static final int STATUS_EXPAND = 1;
    public static final int STATUS_DRAGGING = 2;

    ///scroll status
    public static final int SCROLL_STATUS_IDLE = 0;
    public static final int SCROLL_STATUS_SETTLING = 1;

    private final Context context;

    private VelocityTracker mVelocityTracker;

    // Distance to travel before a drag may begin
    private int mTouchSlop;

    private float mMaxVelocity;

    private float mMinVelocity;

    public NewSlideUpView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewSlideUpView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mainContent = getChildAt(0);
        underView = getChildAt(1);

        if (!checkLayout()) {
            throw new IllegalArgumentException("layout is invalidated.");
        }
        underView.setTop(screenHeight);
        enableOverlay(true);
    }

    /**
     * @return 是否合规的SlideUpView布局
     */
    private boolean checkLayout() {
        int computeViewCount = 0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            if (view.getVisibility() == GONE) {
                continue;
            }
            computeViewCount++;
        }


        if (computeViewCount != 2) {
            return false;
        }

        if (mainContent.getLayoutParams().width != LayoutParams.MATCH_PARENT
            && mainContent.getLayoutParams().height != LayoutParams.MATCH_PARENT) {
            throw new IllegalArgumentException("main content LayoutParam's Height and width must be match_parent.");
        }

        return true;

    }

    private void init() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getDisplay().getRealMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        overlayView = new ImageView(getContext());
        overlayView.setBackgroundColor(Color.BLUE);
        overlayView.setAlpha(0.00f);
        overlayView.setVisibility(GONE);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        overlayView.setLayoutParams(params);

        final ViewConfiguration vc = ViewConfiguration.get(context);

        mTouchSlop = vc.getScaledTouchSlop();
        mMaxVelocity = vc.getScaledMaximumFlingVelocity();
        mMinVelocity = vc.getScaledMinimumFlingVelocity();
        overScroller = new OverScroller(context);
    }

    View touchScrollView;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        final int action = ev.getActionMasked();

        final float x = ev.getX();
        final float y = ev.getY();

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);

        if (action == MotionEvent.ACTION_DOWN) {

            mInitialMotionX = x;
            mInitialMotionY = y;
            mIsScrollableViewHandlingTouch = false;
            mPrevMotionY = y;
            overScroller.abortAnimation();
            //这里会复制touchScrollView
            getScrollViewByCoordinate((int) mInitialMotionX, (int) mInitialMotionY);
            Log.i("zyc", "当前触摸到的scrollView为:" + touchScrollView);

        } else if (action == MotionEvent.ACTION_MOVE) {
            offsetY = y - mPrevMotionY;
            offsetX = x - mPrevMotionX;
            mPrevMotionY = y;
            mPrevMotionX = x;

            if (touchScrollView == null) {
                Log.i("zyc", "没触碰到ScrollView");
                return super.dispatchTouchEvent(ev);
            }

            Log.i("zyc", "當前的scrollStatus為:" + scrollStatus);
            if (offsetY > 0) {
                if (canViewScrollDown(touchScrollView) && !isSettling()) {
                    Log.i("zyc", touchScrollView.getClass().getSimpleName() + "====>可以下滑");
                    mIsScrollableViewHandlingTouch = true;
                    return super.dispatchTouchEvent(ev);
                }

                mIsScrollableViewHandlingTouch = false;
            } else {
                if (canViewScrollUp(touchScrollView) && !isSettling()) {
                    Log.i("zyc", touchScrollView.getClass().getSimpleName() + "====>可以上滑");
                    mIsScrollableViewHandlingTouch = true;
                    return super.dispatchTouchEvent(ev);
                }

                mIsScrollableViewHandlingTouch = false;
            }

        } else if (action == MotionEvent.ACTION_UP) {
            scrollStatus = SCROLL_STATUS_IDLE;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mIsScrollableViewHandlingTouch) {
            return false;
        }
        if (ev.getActionMasked() == MotionEvent.ACTION_MOVE) {
            Log.i("zyc", "onInterceptTouchEvent====> true.");
            float offset = ev.getY() - mInitialMotionY;
            if (checkTouchSlop(offset)) {
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        if (action == MotionEvent.ACTION_MOVE) {
            int finalTop = Math.min(screenHeight, Math.max(0, underView.getTop() + (int) offsetY));
            underView.setTop(finalTop);
            status = STATUS_DRAGGING;
        } else if (action == MotionEvent.ACTION_UP) {
            mVelocityTracker.computeCurrentVelocity(1000, mMaxVelocity);
            final float yvel = clampMag(
                mVelocityTracker.getYVelocity(),
                mMinVelocity, mMaxVelocity);
            Log.i("zyc", "yvel:" + yvel);
            if (yvel > 2000) {
                smoothSlideViewTo(underView, 0, screenHeight);
            } else if (yvel < -2000) {
                smoothSlideViewTo(underView, 0, 0);
            } else if (underView.getTop() > (screenHeight / 2)) {
                slideToBottom();
            } else {
                slideToTop();
            }
            status = underView.getTop() == screenHeight ? STATUS_COLLAPSED :
                underView.getTop() == 0 ? STATUS_EXPAND : SlideUpView.STATUS_DRAGGING;
            cancel();
        }
        return true;
    }

    private void slideToTop() {
        if (underView == null) {
            return;
        }
        smoothSlideViewTo(underView, 0, 0);
    }

    private void slideToBottom() {
        if (underView == null) {
            return;
        }
        smoothSlideViewTo(underView, 0, screenHeight);
    }

    private boolean canViewScrollDown(View view) {
        if (view == null) {
            return false;
        }

        if (view instanceof ScrollView) {
            return view.getScrollY() > 0;
        } else if (view instanceof ListView && ((ListView) view).getChildCount() > 0) {
            ListView lv = ((ListView) view);
            if (lv.getAdapter() == null) {
                return false;
            }
            View firstChild = lv.getChildAt(0);
            // Approximate the scroll position based on the top child and the first visible item
            return lv.getFirstVisiblePosition() * firstChild.getHeight() - firstChild.getTop() > 0;
        } else if (view instanceof RecyclerView && ((RecyclerView) view).getChildCount() > 0) {
            RecyclerView rv = ((RecyclerView) view);
            RecyclerView.LayoutManager lm = rv.getLayoutManager();
            if (lm instanceof LinearLayoutManager) {
                //横向布置的不处理
                if (((LinearLayoutManager) lm).getOrientation() == LinearLayoutManager.HORIZONTAL) {
                    return false;
                }
            }
            if (rv.getAdapter() == null) {
                return false;
            }
            View firstChild = rv.getChildAt(0);
            // Approximate the scroll position based on the top child and the first visible item
            return rv.getChildLayoutPosition(firstChild) * lm.getDecoratedMeasuredHeight(firstChild) - lm.getDecoratedTop(firstChild) > 0;
        } else {
            return false;
        }
    }

    private boolean canViewScrollUp(View view) {
        if (view == null) {
            return false;
        }

        if (view instanceof ScrollView) {
            ScrollView sv = ((ScrollView) view);
            View child = sv.getChildAt(0);
            return (child.getBottom() - (sv.getHeight() + sv.getScrollY())) > 0;
        } else if (view instanceof ListView && ((ListView) view).getChildCount() > 0) {
            ListView lv = ((ListView) view);
            if (lv.getAdapter() == null) {
                return false;
            }
            View lastChild = lv.getChildAt(lv.getChildCount() - 1);
            // Approximate the scroll position based on the bottom child and the last visible item
            return (lv.getAdapter().getCount() - lv.getLastVisiblePosition() - 1) * lastChild.getHeight() + lastChild.getBottom() - lv.getBottom() > 0;

        } else if (view instanceof RecyclerView && ((RecyclerView) view).getChildCount() > 0) {
            RecyclerView rv = ((RecyclerView) view);
            RecyclerView.LayoutManager lm = rv.getLayoutManager();
            if (lm instanceof LinearLayoutManager) {
                //横向布置的不处理
                if (((LinearLayoutManager) lm).getOrientation() == LinearLayoutManager.HORIZONTAL) {
                    return false;
                }
            }

            if (rv.getAdapter() == null) {
                return false;
            }


            int lastPosition = rv.getAdapter().getItemCount() - 1;

            if (lm instanceof LinearLayoutManager) {
                Log.i("zyc", "最后一个完全可见的ItemPosition为：" + ((LinearLayoutManager) lm).findLastCompletelyVisibleItemPosition());
                return lastPosition != ((LinearLayoutManager) lm).findLastCompletelyVisibleItemPosition();
            }
            if (lm instanceof StaggeredGridLayoutManager) {
                int spanCount = ((StaggeredGridLayoutManager) lm).getSpanCount();
                int[] into = new int[spanCount];
                ((StaggeredGridLayoutManager) lm).findLastVisibleItemPositions(into);
                Log.i("zyc", "最后一个完全可见的ItemPosition为：" + Arrays.toString(into));
                for (int position : into) {
                    if (position == lastPosition) {
                        return false;
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    private boolean checkTouchSlop(float dy) {
        return Math.abs(dy) > mTouchSlop && Math.abs(offsetY) > Math.abs(offsetX);
    }

    public void cancel() {

        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
        mIsScrollableViewHandlingTouch = false;
    }

    private void findAllScrollView(ViewGroup viewGroup, List<ViewGroup> scrollViewInMainList) {
        int childCount = viewGroup.getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof ViewGroup) {
                findAllScrollView((ViewGroup) child, scrollViewInMainList);
                if (child instanceof ScrollView
                    || child instanceof RecyclerView
                    || child instanceof ListView) {
                    scrollViewInMainList.add((ViewGroup) child);
                }
            }
        }
    }

    private void getScrollViewByCoordinate(int x, int y) {
        touchScrollView = null;
        findView(this, x, y);
    }

    // TODO: 2021/7/15 未完成
    private void findView(ViewGroup viewGroup, int x, int y) {
        int childCount = viewGroup.getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof ViewGroup && child.getVisibility() == VISIBLE) {
                findView((ViewGroup) child, x, y);
                if (touchScrollView != null) {
                    return;
                }
                if (child instanceof ScrollView
                    || child instanceof RecyclerView
                    || child instanceof ListView) {
                    touchScrollView = isViewUnder(child, x, y) ? child : null;
                }
            }
        }
    }

    public void setStatus(@STATUS int status) {
        if (status == STATUS_EXPAND) {
            this.status = STATUS_EXPAND;
            smoothSlideViewTo(underView, 0, 0);
        } else if (status == STATUS_COLLAPSED) {
            this.status = STATUS_COLLAPSED;
            smoothSlideViewTo(underView, 0, screenHeight);
        }
    }

    public void enabledSlideUp(boolean slideUpEnable) {
        this.slideUpEnable = slideUpEnable;
    }

    public int getStatus() {
        return status;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.i("zyc", "onLayout: :");
        //layout main view
        final LayoutParams mainLp = (LayoutParams) mainContent.getLayoutParams();

        final int parentLeft = getPaddingStart();
        final int parentRight = right - left - getPaddingEnd();
        final int parentTop = getPaddingTop();
        final int parentBottom = bottom - top - getPaddingBottom();

        final int mainWidth = mainContent.getMeasuredWidth();
        final int mainHeight = mainContent.getMeasuredHeight();

        int childLeft;
        int childTop;

        childLeft = parentLeft + (parentRight - parentLeft - mainWidth) / 2 +
            mainLp.leftMargin - mainLp.rightMargin;

        childTop = parentTop + (parentBottom - parentTop - mainHeight) / 2 +
            mainLp.topMargin - mainLp.bottomMargin;

        mainContent.layout(childLeft, childTop, childLeft + mainWidth, childTop + mainHeight);

        //layout under view
        final int underWidth = underView.getMeasuredWidth();
        final int underHeight = underView.getMeasuredHeight();

        final LayoutParams underLp = (LayoutParams) underView.getLayoutParams();

        int underLeft;

        final int gravity = underLp.gravity;
        switch (gravity) {
            case Gravity.CENTER_HORIZONTAL:
                underLeft = parentLeft + (parentRight - parentLeft - underWidth) / 2 +
                    underLp.leftMargin - underLp.rightMargin;
                break;
            case Gravity.END:
                underLeft = parentRight - underWidth - underLp.rightMargin;
                break;
            case Gravity.START:
            default:
                underLeft = parentLeft + underLp.leftMargin;
        }
        underView.layout(underLeft, underView.getTop(), underLeft + underWidth, underView.getTop() + underHeight);

        if (getOverlayView() != null && getOverlayView().getVisibility() != GONE) {
            getOverlayView().layout(0, 0, screenWidth, screenHeight);
        } else if (getOverlayView() != null) {
            getOverlayView().layout(0, 0, 0, 0);
        }
    }

    public boolean isExpand() {
        return underView.getTop() == 0;
    }

    public boolean isCollapsed() {
        return underView.getTop() == screenHeight;
    }

    public boolean smoothSlideViewTo(@NonNull View child, int finalLeft, int finalTop) {
        final int startTop = child.getTop();
        final int dy = finalTop - startTop;

        if (dy == 0) {
            overScroller.abortAnimation();
            return false;
        }


        final int duration = computeSettleDuration(dy, 0);
        overScroller.startScroll(0, startTop, 0, dy, duration);
        scrollStatus = SCROLL_STATUS_SETTLING;
        Log.i("zyc", "设置scrollStatus为：" + scrollStatus);
        invalidate();

        return true;
    }

    private int computeSettleDuration(int dy, int yVel) {
        yVel = clampMag(yVel, (int) mMinVelocity, (int) mMaxVelocity);

        int yDuration = computeAxisDuration(dy, yVel);

        return (int) yDuration;
    }

    private int computeAxisDuration(int delta, int velocity) {
        if (delta == 0) {
            return 0;
        }

        final int width = NewSlideUpView.this.getWidth();
        final int halfWidth = width / 2;
        final float distanceRatio = Math.min(1f, (float) Math.abs(delta) / width);
        final float distance = halfWidth + halfWidth
            * distanceInfluenceForSnapDuration(distanceRatio);

        int duration;
        velocity = Math.abs(velocity);
        if (velocity > 0) {
            duration = 4 * Math.round(1000 * Math.abs(distance / velocity));
        } else {
            final float range = (float) Math.abs(delta);
            duration = (int) ((range + 1) * BASE_SETTLE_DURATION);
        }
        return Math.min(duration, MAX_SETTLE_DURATION);
    }

    private float distanceInfluenceForSnapDuration(float f) {
        f -= 0.5f; // center the values about 0.
        f *= 0.3f * (float) Math.PI / 2.0f;
        return (float) Math.sin(f);
    }

    private int clampMag(int value, int absMin, int absMax) {
        final int absValue = Math.abs(value);
        if (absValue < absMin) {
            return 0;
        }
        if (absValue > absMax) {
            return value > 0 ? absMax : -absMax;
        }
        return value;
    }

    private float clampMag(float value, float absMin, float absMax) {
        final float absValue = Math.abs(value);
        if (absValue < absMin) {
            return 0;
        }
        if (absValue > absMax) {
            return value > 0 ? absMax : -absMax;
        }
        return value;
    }

    public void enableOverlay(boolean overlayEnable) {
        //this.overlayEnable = overlayEnable;
        //if (overlayEnable) {
        //    addView(getOverlayView(), 1);
        //} else {
        //    removeView(getOverlayView());
        //}
    }

    protected View getOverlayView() {
        return overlayView;
    }

    private boolean isViewUnder(View view, int x, int y) {
        if (view == null) {
            return false;
        }
        int[] viewLocation = new int[2];
        view.getLocationOnScreen(viewLocation);
        int[] parentLocation = new int[2];
        this.getLocationOnScreen(parentLocation);
        int screenX = parentLocation[0] + x;
        int screenY = parentLocation[1] + y;
        RectF location = getViewLocationByRecurse(view);
        return screenX >= location.left && screenX < location.right &&
            screenY >= location.top && screenY < location.bottom;
    }

    private RectF getViewLocationByRecurse(View view) {
        int left = view.getLeft();
        int top = view.getTop();
        ViewParent parent = view.getParent();
        while (parent instanceof ViewGroup) {
            left += ((ViewGroup) parent).getLeft();
            top += ((ViewGroup) parent).getTop();
            parent = parent.getParent();
        }
        return new RectF(left, top, left + view.getWidth(), top + view.getHeight());
    }

    public int getScrollStatus() {
        return scrollStatus;
    }

    private boolean isSettling() {
        return scrollStatus == SCROLL_STATUS_SETTLING;
    }

    @Override
    public void computeScroll() {
        if (overScroller.computeScrollOffset()) {
            int currY = overScroller.getCurrY();
            Log.i("zyc", "我正在执行滑动，目标Y：" + overScroller.getFinalY() + ", 当前Y：" + currY);
            underView.setTop(currY);
            if (overScroller.getFinalY() == currY) {
                scrollStatus = SCROLL_STATUS_IDLE;
                status = underView.getTop() == 0 ? STATUS_EXPAND : STATUS_COLLAPSED;
            }
            invalidate();
            return;
        }
        super.computeScroll();
    }
}
