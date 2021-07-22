package com.example.myapplication.slidingup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 初级版
 *
 * @author yuechou.zhang
 * @since 2021/7/12
 */
public class SlideUpView extends FrameLayout {

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

    private MyViewDragHelper mDragHelper;

    private boolean overlayEnable;

    @IntDef({STATUS_COLLAPSED, STATUS_EXPAND, STATUS_DRAGGING})
    @Retention(RetentionPolicy.SOURCE)
    @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
    public @interface STATUS {
    }

    public static final int STATUS_COLLAPSED = 0;

    public static final int STATUS_EXPAND = 1;

    public static final int STATUS_DRAGGING = 2;

    private final Context context;

    private boolean mIsScrollableViewHandlingTouch = false;

    public SlideUpView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideUpView(Context context, AttributeSet attrs, int defStyleAttr) {
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

    boolean handleByChild = false;

    private float mPrevMotionY;
    private float mInitialMotionX;
    private float mInitialMotionY;


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        final int action = ev.getActionMasked();

        final float x = ev.getX();
        final float y = ev.getY();

        if (action == MotionEvent.ACTION_DOWN) {
            mInitialMotionX = x;
            mInitialMotionY = y;
            mIsScrollableViewHandlingTouch = false;
            mPrevMotionY = y;
        } else if (action == MotionEvent.ACTION_MOVE) {
            float dy = y - mPrevMotionY;
            mPrevMotionY = y;

            // If the scroll view isn't under the touch, pass the
            // event along to the dragView.
            if (!isViewUnder(mScrollableView, (int) mInitialMotionX, (int) mInitialMotionY)) {
                Log.i("zyc", "dispatchTouchEvent: not under view.");
                return super.dispatchTouchEvent(ev);
            }

            // Which direction (up or down) is the drag moving?
            if (dy >= 0) { // Collapsing
                Log.i("zyc", "dy > 0");
                // Is the child less than fully scrolled?
                // Then let the child handle it.
                if (getScrollableViewScrollPosition() > 0) {
                    Log.i("zyc", "dispatchTouchEvent: getScrollableViewScrollPosition > 0.");
                    mIsScrollableViewHandlingTouch = true;
                    return super.dispatchTouchEvent(ev);
                }

                // Was the child handling the touch previously?
                // Then we need to rejigger things so that the
                // drag panel gets a proper down event.
                //if (mIsScrollableViewHandlingTouch) {
                //    // Send an 'UP' event to the child.
                //    MotionEvent up = MotionEvent.obtain(ev);
                //    up.setAction(MotionEvent.ACTION_CANCEL);
                //    super.dispatchTouchEvent(up);
                //    up.recycle();
                //
                //    // Send a 'DOWN' event to the panel. (We'll cheat
                //    // and hijack this one)
                //    ev.setAction(MotionEvent.ACTION_DOWN);
                //}

                mIsScrollableViewHandlingTouch = false;
                return this.onTouchEvent(ev);
            } else if (dy < 0) { // Expanding
                Log.i("zyc", "dy < 0");
                // Is the panel less than fully expanded?
                // Then we'll handle the drag here.
                if (status == STATUS_EXPAND) {
                    mIsScrollableViewHandlingTouch = false;
                    return this.onTouchEvent(ev);
                }

                // Was the panel handling the touch previously?
                // Then we need to rejigger things so that the
                // child gets a proper down event.
                //if (!mIsScrollableViewHandlingTouch && mDragHelper.isDragging()) {
                //    mDragHelper.cancel();
                //    ev.setAction(MotionEvent.ACTION_DOWN);
                //}

                mIsScrollableViewHandlingTouch = true;
                Log.i("zyc", "dy < 0 : " + mIsScrollableViewHandlingTouch);
                return super.dispatchTouchEvent(ev);
            }
        } else if (action == MotionEvent.ACTION_UP && mIsScrollableViewHandlingTouch) {
            // If the scrollable view was handling the touch and we receive an up
            // we want to clear any previous dragging state so we don't intercept a touch stream accidentally
            mDragHelper.setDragState(MyViewDragHelper.STATE_IDLE);
        }
        return super.dispatchTouchEvent(ev);
    }

    private View mScrollableView;

    public void setScrollableView(View scrollableView) {
        mScrollableView = scrollableView;
    }

    private boolean isViewUnder(View view, int x, int y) {
        if (view == null) return false;
        int[] viewLocation = new int[2];
        view.getLocationOnScreen(viewLocation);
        int[] parentLocation = new int[2];
        this.getLocationOnScreen(parentLocation);
        int screenX = parentLocation[0] + x;
        int screenY = parentLocation[1] + y;
        return screenX >= viewLocation[0] && screenX < viewLocation[0] + view.getWidth() &&
            screenY >= viewLocation[1] && screenY < viewLocation[1] + view.getHeight();
    }

    protected int getScrollableViewScrollPosition() {
        if (mScrollableView == null) return 0;
        if (mScrollableView instanceof ScrollView) {
            return mScrollableView.getScrollY();
        } else if (mScrollableView instanceof ListView && ((ListView) mScrollableView).getChildCount() > 0) {
            ListView lv = ((ListView) mScrollableView);
            if (lv.getAdapter() == null) return 0;
            View firstChild = lv.getChildAt(0);
            // Approximate the scroll position based on the top child and the first visible item
            return lv.getFirstVisiblePosition() * firstChild.getHeight() - firstChild.getTop();
        } else if (mScrollableView instanceof RecyclerView && ((RecyclerView) mScrollableView).getChildCount() > 0) {
            RecyclerView rv = ((RecyclerView) mScrollableView);
            RecyclerView.LayoutManager lm = rv.getLayoutManager();
            if (rv.getAdapter() == null) return 0;
            View firstChild = rv.getChildAt(0);
            // Approximate the scroll position based on the top child and the first visible item
            return rv.getChildLayoutPosition(firstChild) * lm.getDecoratedMeasuredHeight(firstChild) - lm.getDecoratedTop(firstChild);
        } else {
            return 0;
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (mIsScrollableViewHandlingTouch) {
            mDragHelper.cancel();
            Log.i("zyc", "dragHelper is cancel .");
            return false;
        }

        Log.i("zyc", "onInterceptTouchEvent: outside:" + event.getActionMasked());
        if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
            Log.i("zyc", "onInterceptTouchEvent: move:");
            float offset = event.getY() - mInitialMotionY;
            if (checkTouchSlop(offset)) {
                return true;
            }
        }
        //return super.onInterceptTouchEvent(event);
        return mDragHelper.shouldInterceptTouchEvent(event);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            mDragHelper.processTouchEvent(event);
        } catch (IllegalArgumentException e) {

        }
        Log.i("zyc", "mDragHelper.isDragging():" + mDragHelper.isDragging());
        Log.i("zyc", "event.getActionMasked():" + event.getActionMasked());
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

        mDragHelper = MyViewDragHelper.create(this, 1.0f, new MyViewDragHelper.Callback() {

            private int preStatus;

            @Override
            public int clampViewPositionVertical(@NotNull View child, int top, int dy) {
                if (child == mainContent) {
                    status = STATUS_DRAGGING;
                    int curTop = underView.getTop();
                    int offset = curTop + dy < 0 ? -curTop : curTop + dy > screenHeight ? screenHeight - curTop : dy;
                    ViewCompat.offsetTopAndBottom(underView, offset);
                    if (underView.getTop() == screenHeight) {
                        getOverlayView().setVisibility(GONE);
                    } else if (underView.getTop() < screenHeight) {
                        if (getOverlayView().getVisibility() != VISIBLE) {
                            getOverlayView().setVisibility(VISIBLE);
                        }
                        getOverlayView().setAlpha(1 - (((float) underView.getTop()) / screenHeight));
                    }

                    if (scrollListener != null) {
                        scrollListener.onScroll(underView, offset, underView.getTop());
                    }
                    return 0;
                }

                if (child == underView) {
                    return Math.min(screenHeight, Math.max(0, top));
                }

                return 0;
            }

            @Override
            public boolean tryCaptureView(@NonNull @NotNull View child, int pointerId) {

                Log.d("zyc", "tryCaptureView: child :" + child +
                    " pointerId: " + pointerId);
                if (child != getOverlayView()) {
                    getOverlayView().setVisibility(VISIBLE);
                }
                preStatus = status;
                return child == mainContent || child == underView;
            }

            @Override
            public void onViewReleased(@NotNull View releasedChild, float xvel, float yvel) {
                Log.d("zyc", "onViewReleased: " + yvel);
                Log.i("zyc", "releasedChild:" + releasedChild);
                //手指下甩为正，上甩为负
                if (yvel > 2000) {
                    mDragHelper.smoothSlideViewTo(underView, 0, screenHeight);
                    invalidate();
                    return;
                }

                if (yvel < -2000) {
                    mDragHelper.smoothSlideViewTo(underView, 0, 0);
                    invalidate();
                    return;
                }

                switch (preStatus) {
                    case STATUS_EXPAND:
                        if (underView.getTop() > (screenHeight / 5)) {
                            slideToBottom();
                        } else {
                            slideToTop();
                        }
                        break;
                    case STATUS_COLLAPSED:
                    case STATUS_DRAGGING:
                        if (underView.getTop() < (screenHeight * 4 / 5)) {
                            slideToTop();
                        } else {
                            slideToBottom();
                        }
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onViewPositionChanged(@NotNull View changedView, int left, int top, int dx, int dy) {
                if (changedView == underView) {
                    status = STATUS_DRAGGING;
                    if (underView.getTop() == screenHeight) {
                        getOverlayView().setVisibility(GONE);
                    }
                    getOverlayView().setAlpha(1 - (((float) underView.getTop()) / screenHeight));
                    if (scrollListener != null) {
                        scrollListener.onScroll(underView, dy, underView.getTop());
                    }
                }
            }

            @Override
            public int getViewVerticalDragRange(@NonNull @NotNull View child) {
                return 1;
            }

            @Override
            public void onEdgeTouched(int edgeFlags, int pointerId) {
                super.onEdgeTouched(edgeFlags, pointerId);
                Log.i("zyc", "edgeFlags:" + edgeFlags);
            }
        });
    }

    private boolean checkTouchSlop(float dy) {
        final ViewConfiguration vc = ViewConfiguration.get(context);
        int mTouchSlop = vc.getScaledTouchSlop();
        return Math.abs(dy) > mTouchSlop;
    }

    private void slideToTop() {
        if (underView == null || mDragHelper == null) {
            return;
        }
        mDragHelper.smoothSlideViewTo(underView, 0, 0);
        invalidate();
    }

    private void slideToBottom() {
        if (underView == null || mDragHelper == null) {
            return;
        }
        mDragHelper.smoothSlideViewTo(underView, 0, screenHeight);
        invalidate();

    }

    public static Bitmap createBitmapSafely(int width, int height, Bitmap.Config config, int retryCount) {
        try {
            return Bitmap.createBitmap(width, height, config);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            if (retryCount > 0) {
                System.gc();
                return createBitmapSafely(width, height, config, retryCount - 1);
            }
            return null;
        }
    }

    public static Bitmap createBitmapFromView(View view) {
        view.clearFocus();
        Bitmap bitmap = createBitmapSafely(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888, 1);
        if (bitmap != null) {
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
            canvas.setBitmap(null);
        }
        return bitmap;
    }

    Bitmap overlay;

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(false)) {
            getOverlayView().setAlpha(1 - (((float) underView.getTop()) / screenHeight));
            invalidate();
            return;
        }
        status = underView.getTop() == 0 ? STATUS_EXPAND :
            underView.getTop() == screenHeight ? STATUS_COLLAPSED : STATUS_DRAGGING;
        Log.i("zyc", "computeScroll: current Status :" + status);
        if (status == STATUS_COLLAPSED) {
            if (getOverlayView() != null) {
                getOverlayView().setVisibility(GONE);
            }
        }
    }

    public int getStatus() {
        return status;
    }

    protected View getOverlayView() {
        return overlayView;
    }

    public void enableOverlay(boolean overlayEnable) {
        this.overlayEnable = overlayEnable;
        if (overlayEnable) {
            addView(getOverlayView(), 1);
        } else {
            removeView(getOverlayView());
        }
    }


    public interface OnViewScrollListener {

        /**
         * 滑动监听
         *
         * @param scrollView 正正在滑动的View
         * @param dy 偏移量dy
         * @param top 目前位置
         */
        void onScroll(View scrollView, int dy, int top);

    }

    OnViewScrollListener scrollListener;

    public void setScrollListener(OnViewScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    private int pix2dp(int pixel) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return pixel * DisplayMetrics.DENSITY_MEDIUM / DisplayMetrics.DENSITY_DEVICE_STABLE;
        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            context.getDisplay().getRealMetrics(displayMetrics);
            return pixel * DisplayMetrics.DENSITY_MEDIUM / displayMetrics.densityDpi;
        }
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

    private static final String TAG = "SlideUpView";

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


}
