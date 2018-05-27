package com.view.mark.marksildingmenu.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.view.mark.marksildingmenu.R;
import com.view.mark.marksildingmenu.SlideUtils;

/**
 * 自定义的侧滑菜单
 * Created by mark on 2018/3/20.
 */

public class MarkSlideMenu extends HorizontalScrollView {

    private LinearLayout mWapper;
    private ViewGroup mLeftMenu;
    private ViewGroup mContent;
    private ViewGroup mRightMenu;
    private int mScreenWidth;

    private int mLeftMenuWidth = 0;
    private int mRightMenuWidth = 0;
    private int isWidth = 0;
    // 左边的menu距离dp
    private int mMenuRightPadding = 50;
    private int mMenuLeftPadding = 50;
    private boolean once;//防止界面多次绘制

    private boolean isOpen, isLeft, isRight;

    //传入对应的boolean
    private boolean isRightMenu, isLeftMenu;
    private Context mContext;

    private float downX;    //按下时 的X坐标

    public MarkSlideMenu(Context context) {
        this(context, null);
    }

    public MarkSlideMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarkSlideMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SlidingMenu, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.SlidingMenu_rightPadding:
                    mMenuRightPadding = a.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP, 50, context
                                            .getResources().getDisplayMetrics()));
                    break;
                case R.styleable.SlidingMenu_leftPadding:
                    mMenuLeftPadding = a.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP, 50, context
                                            .getResources().getDisplayMetrics()));
                    break;
                case R.styleable.SlidingMenu_isLeftMenu:
                    isLeftMenu = a.getBoolean(attr,
                            false);
                    break;
                case R.styleable.SlidingMenu_isRightMenu:
                    isRightMenu = a.getBoolean(attr,
                            false);
                    break;
            }
        }
        a.recycle();

        //获取屏幕的宽度
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;
    }

    public int getmMenuRightPadding() {
        return mMenuRightPadding;
    }

    public void setmMenuRightPadding(int mMenuRightPadding) {
        this.mMenuRightPadding = SlideUtils.dip2px(mContext, mMenuRightPadding);
    }

    public int getmMenuLeftPadding() {
        return mMenuLeftPadding;
    }

    public void setmMenuLeftPadding(int mMenuLeftPadding) {
        this.mMenuLeftPadding = SlideUtils.dip2px(mContext, mMenuLeftPadding);
    }

    public boolean isRightMenu() {
        return isRightMenu;
    }

    public void setRightMenu(boolean rightMenu) {
        isRightMenu = rightMenu;
    }

    public boolean isLeftMenu() {
        return isLeftMenu;
    }

    public void setLeftMenu(boolean leftMenu) {
        isLeftMenu = leftMenu;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!once) {
            mWapper = (LinearLayout) getChildAt(0);
            Log.e("Mark", "mContent.getChildAt-----" + mScreenWidth);
            if (mWapper.getChildCount() == 2) {
                if (isLeftMenu && !isRightMenu) {
                    mLeftMenu = (ViewGroup) mWapper.getChildAt(0);
                    mContent = (ViewGroup) mWapper.getChildAt(1);
                    mLeftMenuWidth = mLeftMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
                    isWidth = mLeftMenuWidth;
                    mContent.getLayoutParams().width = mScreenWidth;
                    Log.e("Mark", "mContent.getLayoutParams().width-----" + mScreenWidth);
                    once = true;
                }
                if (isRightMenu && !isLeftMenu) {
                    mRightMenu = (ViewGroup) mWapper.getChildAt(1);
                    mContent = (ViewGroup) mWapper.getChildAt(0);
                    mRightMenuWidth = mRightMenu.getLayoutParams().width
                            = mScreenWidth - mMenuRightPadding;
                    isWidth = mRightMenuWidth;
                    mContent.getLayoutParams().width = mScreenWidth;
                    Log.e("Mark", "mContent.getLayoutParams().width-----" + mScreenWidth);
                    once = true;
                }
            }

            if (mWapper.getChildCount() == 3) {
                if (isLeftMenu && isRightMenu) {
                    mLeftMenu = (ViewGroup) mWapper.getChildAt(0);
                    mContent = (ViewGroup) mWapper.getChildAt(1);
                    mRightMenu = (ViewGroup) mWapper.getChildAt(2);
                    mLeftMenuWidth = mLeftMenu.getLayoutParams().width
                            = mScreenWidth - mMenuRightPadding;
                    mRightMenuWidth = mRightMenu.getLayoutParams().width
                            = mScreenWidth - mMenuRightPadding;
                    isWidth = mLeftMenuWidth;
                    mContent.getLayoutParams().width = mScreenWidth;
                    Log.e("Mark", "mContent.getLayoutParams().width-----" + mScreenWidth);
                    once = true;
                }
            }

            if (mWapper.getChildCount() == 1) {
                Log.e("警告⚠", "你应该添加menu的布局");
            }


        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(mLeftMenuWidth, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //在触发时回去到起始坐标
        float x = ev.getX();
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //将按下时的坐标存储
                downX = x;
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                // 判断左右菜单
                //获取到距离差
                float scrollX = x - downX;
                if (isOpen) {
                    if (isLeft) {
                        if (scrollX < -(isWidth / 2)) {
                            closeMenu();
                        } else {
//                            Log.e("isRight", isLeft + "");
                            this.smoothScrollTo(0, 0);
                        }
                    }

                    if (isRight) {
                        if (scrollX > isWidth / 2) {
                            closeMenu();
                        } else {
//                            Log.e("isLeft", isRight + "");
                            this.smoothScrollTo(mLeftMenuWidth + mScreenWidth, 0);
                        }
                    }
                } else {
                    if (scrollX >= isWidth / 2) {
                        openLeftMenu();
//                        Toast.makeText(mContext, "左侧", Toast.LENGTH_SHORT).show();
                    } else if (scrollX <= -(mLeftMenuWidth / 2)) {
                        openRightMenu();
//                        Toast.makeText(mContext, "右侧", Toast.LENGTH_SHORT).show();
                    } else {
                        closeMenu();
                    }
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 打开左边的菜单
     */
    public void openLeftMenu() {
        if (isOpen)
            return;
        this.smoothScrollTo(0, 0);
        isOpen = true;
        isLeft = true;
    }

    /**
     * 打开右边菜单
     */
    public void openRightMenu() {
        if (isOpen)
            return;
        this.smoothScrollTo(mLeftMenuWidth + mScreenWidth, 0);
        isOpen = true;
        isRight = true;
    }

    /**
     * 关闭所有的菜单
     */
    public void closeMenu() {
//        if (!isOpen)
//            return;
        this.smoothScrollTo(mLeftMenuWidth, 0);
        isOpen = false;
        isLeft = false;
        isRight = false;
    }

    /**
     * 打开左边的menu
     */
    public void toggleLeft() {
        if (isOpen) {
            closeMenu();
        } else {
            openLeftMenu();
        }
    }

    /**
     * 打开右边的menu
     */
    public void toggleRight() {
        if (isOpen) {
            closeMenu();
        } else {
            openRightMenu();
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = l * 1.0f / mLeftMenuWidth; // 1 ~ 0


        float rightScale = 0.7f + 0.3f * scale;
        float leftScale = 1.0f - scale * 0.3f;
        float leftAlpha = 0.6f + 0.4f * (1 - scale);

//        // ?????????????????TranslationX
//        ViewHelper.setTranslationX(mMenu, mMenuWidth * scale * 0.8f);
//
//        ViewHelper.setScaleX(mMenu, leftScale);
//        ViewHelper.setScaleY(mMenu, leftScale);
//        ViewHelper.setAlpha(mMenu, leftAlpha);
//        // ????content????????????
//        ViewHelper.setPivotX(mContent, 0);
//        ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);
//        ViewHelper.setScaleX(mContent, rightScale);
//        ViewHelper.setScaleY(mContent, rightScale);

    }

}
