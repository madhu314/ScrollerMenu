package com.appmogli.widget.scrollermenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Arrays;


/**
 * Created by Madhu on 1/5/14.
 */
public class ScrollerMenu extends RelativeLayout {
    private static final int MENU_ITEM_WIDTH_IN_DP = 120;
    private static final int MENU_ITEM_HEIGHT_IN_DP = 36;
    private static final int MENU_ITEM_DIVIDER_MARGIN_IN_DP = 2;
    private static final int MENU_ITEM_TEXT_COLOR = Color.WHITE;

    private Drawable menuItemDefaultBackground;
    private Drawable menuItemSelectedBackground;
    private int menuItemTextColor;
    private float menuItemWidth;
    private float menuItemHeight;
    private String[] menuItems;
    private ScrollerMenuScrollView scrollView;
    private LinearLayout menuPanel;

    public ScrollerMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ScrollerMenu,
                0, 0);

        try {
            menuItemDefaultBackground = a.getDrawable(R.styleable.ScrollerMenu_scrollerMenuItemDefaultBackground);
            if(menuItemDefaultBackground == null) {
                menuItemDefaultBackground = context.getResources().getDrawable(R.drawable.scroller_menu_item_default_state);
            }
            menuItemSelectedBackground = a.getDrawable(R.styleable.ScrollerMenu_scrollerMenuItemSelectedBackground);
            if(menuItemSelectedBackground == null) {
                menuItemSelectedBackground = context.getResources().getDrawable(R.drawable.scroller_menu_item_activated_state);
            }
            menuItemWidth = a.getDimension(R.styleable.ScrollerMenu_scrollerMenuItemWidth, MENU_ITEM_WIDTH_IN_DP);
            menuItemHeight = a.getDimension(R.styleable.ScrollerMenu_scrollerMenuItemHeight, MENU_ITEM_HEIGHT_IN_DP);
            menuItemTextColor = a.getColor(R.styleable.ScrollerMenu_scrollerMenuItemTextColor, MENU_ITEM_TEXT_COLOR);
            CharSequence[] menuArray = (CharSequence[]) a.getTextArray(R.styleable.ScrollerMenu_scrollerMenuItems);
            if (menuArray == null || menuArray.length < 2) {
                throw new RuntimeException("Inflate failed, there should be at least 2 menu items");
            }
            menuItems = new String[menuArray.length];
            int i=0;
            for(CharSequence ch: menuArray){
                menuItems[i++] = ch.toString();
            }

        } finally {
            a.recycle();
        }



    }

    public ScrollerMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ScrollerMenu(Context context) {
        super(context);
        menuItemDefaultBackground = context.getResources().getDrawable(R.drawable.scroller_menu_item_default_state);
        menuItemWidth = MENU_ITEM_WIDTH_IN_DP;
        menuItemHeight = MENU_ITEM_HEIGHT_IN_DP;
        menuItemTextColor = MENU_ITEM_TEXT_COLOR;
    }

    public void setMenuItems(String[] items) {
        this.menuItems = Arrays.copyOf(items, items.length);
        requestLayout();
        invalidate();
    }

    public void setMenuItems(int arrayResource) {
        this.menuItems = getResources().getStringArray(arrayResource);
        requestLayout();
        invalidate();
    }

    public void setMenuItemBackgroundDrawableResource(int drawableResource) {
        this.menuItemDefaultBackground = getContext().getResources().getDrawable(drawableResource);
        invalidate();
    }

    public void setMenuItemHeight(int dimesionResource) {
        this.menuItemHeight = getResources().getDimension(dimesionResource);
        requestLayout();
        invalidate();
    }

    public void setMenuItemWidth(int dimesionResource) {
        this.menuItemWidth = getResources().getDimension(dimesionResource);
        requestLayout();
        invalidate();
    }


    public void setMenuItemTextColor(int colorResource) {
        this.menuItemTextColor = getResources().getColor(colorResource);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return scrollView.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int viewWidth = w;
        int viewHeight = h;

        Context context = getContext();
        scrollView = new ScrollerMenuScrollView(context);
        LinearLayout completeScrollPanel = new LinearLayout(context);
        menuPanel = new LinearLayout(context);

        float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, menuItemWidth, getResources().getDisplayMetrics());
        float height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, menuItemHeight, getResources().getDisplayMetrics());

        float menuItemDivider = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MENU_ITEM_DIVIDER_MARGIN_IN_DP, getResources().getDisplayMetrics());
        float totalMenuItemDividerHeight = (menuItems.length - 1) * menuItemDivider;

        float menuPanelHeight = totalMenuItemDividerHeight + menuItems.length * height;
        float menuPanelWidth = width;

        LinearLayout.LayoutParams menuPanelLayoutParams = new LinearLayout.LayoutParams(Math.round(menuPanelWidth), Math.round(menuPanelHeight));
        menuPanel.setBackgroundColor(Color.parseColor("#0A000000"));
        menuPanel.setOrientation(LinearLayout.VERTICAL);
        TextView[] menuTvs = new TextView[menuItems.length];
        for (int i = 0; i < menuItems.length; i++) {
            TextView tv = new TextView(context);
            menuTvs[i] = tv;
            LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(Math.round(menuPanelWidth), Math.round(height));
            tv.setBackgroundDrawable(menuItemDefaultBackground);
            tv.setText(menuItems[i]);
            tv.setGravity(Gravity.CENTER);
            tv.setSingleLine(true);
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setLayoutParams(tvParams);
            tv.setTextColor(Color.WHITE);
            menuPanel.addView(tv);
            //now add margin
            if (i != (menuItems.length - 1)) {
                View view = new View(context);
                LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(Math.round(menuPanelWidth), Math.round(menuItemDivider));
                view.setLayoutParams(viewParams);
                menuPanel.addView(view);

            }
        }
        menuPanel.setLayoutParams(menuPanelLayoutParams);

        float menuPanelAboveDividerHeight = (menuItems.length - 1) * menuItemDivider;
        float totalMenuPanelAboveHeight = (menuItems.length - 1) * height + menuPanelAboveDividerHeight;

        View menuPanelAboveView = new View(context);
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(Math.round(menuPanelWidth), Math.round(totalMenuPanelAboveHeight));
        menuPanelAboveView.setLayoutParams(viewParams);

        View menuPanelBelowView = new View(context);
        viewParams = new LinearLayout.LayoutParams(Math.round(menuPanelWidth), Math.round(totalMenuPanelAboveHeight));
        menuPanelBelowView.setLayoutParams(viewParams);

        ScrollView.LayoutParams completeScrollPanelParams = new ScrollView.LayoutParams(Math.round(menuPanelWidth), 2 * Math.round(totalMenuPanelAboveHeight) + Math.round(menuPanelHeight));
        completeScrollPanel.setLayoutParams(completeScrollPanelParams);
        completeScrollPanel.setOrientation(LinearLayout.VERTICAL);
        completeScrollPanel.addView(menuPanelAboveView);
        completeScrollPanel.addView(menuPanel);
        completeScrollPanel.addView(menuPanelBelowView);

        RelativeLayout.LayoutParams scrollParams = new LayoutParams(Math.round(menuPanelWidth), Math.round(totalMenuPanelAboveHeight) + Math.round(menuPanelHeight));
        scrollParams.topMargin = Math.round(viewHeight/2f) - Math.round(totalMenuPanelAboveHeight);
        scrollParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        scrollView.setLayoutParams(scrollParams);

        scrollView.addView(completeScrollPanel);
        scrollView.setOverScrollMode(ScrollView.OVER_SCROLL_NEVER);
        scrollView.setHorizontalScrollBarEnabled(false);
        scrollView.setVerticalScrollBarEnabled(false);


        addView(scrollView);

        //lets create selected item
        TextView tv = new TextView(context);
        LayoutParams tvParams = new LayoutParams(Math.round(menuPanelWidth), Math.round(height));
        tvParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        tvParams.topMargin = Math.round(viewHeight/2f);
        tv.setBackgroundDrawable(menuItemSelectedBackground);
        tv.setText(menuItems[0]);
        tv.setGravity(Gravity.CENTER);
        tv.setSingleLine(true);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setLayoutParams(tvParams);
        tv.setTextColor(Color.WHITE);
        menuTvs[0].setVisibility(View.INVISIBLE);
        addView(tv);
        scrollView.setMenuPanel(menuTvs, height, tv, Math.round(totalMenuPanelAboveHeight));

        //add this properly now

    }
}
