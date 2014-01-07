package com.appmogli.widget.scrollermenu;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by Madhu on 1/7/14.
 */
public class ScrollerMenuScrollView extends ScrollView {
    private static final String TAG = ScrollerMenuScrollView.class.getSimpleName();
    private int itemHeight;
    private TextView[] menuItems;
    private TextView selectedText;
    private int scrollHeight;

    public ScrollerMenuScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ScrollerMenuScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollerMenuScrollView(Context context) {
        super(context);
    }

    public void setMenuPanel(TextView[] menuItems, float height, TextView tv, int scrollHeight) {
        this.itemHeight = (int) height;
        this.menuItems = menuItems;
        this.selectedText = tv;
        this.scrollHeight = scrollHeight;
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, Math.min(scrollY, this.scrollHeight), clampedX, clampedY);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        int childToActivate = 0;
        if (t > 0) {
            childToActivate = t / itemHeight;
        }
        this.selectedText.setText(menuItems[childToActivate].getText());
        for (int i = 0; i < menuItems.length; i++) {
            menuItems[i].setVisibility(i == childToActivate ? View.INVISIBLE : View.VISIBLE);
        }
    }
}
