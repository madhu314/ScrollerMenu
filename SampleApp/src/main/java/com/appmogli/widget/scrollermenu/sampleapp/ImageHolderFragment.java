package com.appmogli.widget.scrollermenu.sampleapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appmogli.widget.scrollermenu.ScrollerMenu;

/**
 * Created by Madhu on 1/10/14.
 */
public class ImageHolderFragment extends Fragment implements ScrollerMenu.ScrollerMenuListener {
    private static final String KEY_BUNDLE_PROGRESS = "KEY_BUNDLE_PROGRESS";
    private static final String KEY_BUNDLE_SELECTED_MENU_INDEX = "KEY_BUNDLE_SELECTED_MENU_INDEX";
    private static final int PROGRESS_MAX = 100;
    private static final int PROGRESS_MIN = -100;

    private TextView selectedMenu;
    private TextView progressText;
    private int progress;
    private ScrollerMenu scrollerMenu;
    private String[] menuItems;

    public ImageHolderFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image_holder, container, false);
        selectedMenu = (TextView) rootView.findViewById(R.id.fragment_image_holder_selected_menu);
        progressText = (TextView) rootView.findViewById(R.id.fragment_image_holder_progress);
        scrollerMenu = (ScrollerMenu) rootView.findViewById(R.id.fragment_image_holder_scroller_menu);
        menuItems = getResources().getStringArray(R.array.menu_items);
        int menuItemIndex = 0;
        if (savedInstanceState != null) {
            progress = savedInstanceState.getInt(KEY_BUNDLE_PROGRESS);
            menuItemIndex = savedInstanceState.getInt(KEY_BUNDLE_SELECTED_MENU_INDEX);
        }
        scrollerMenu.setSelectedMenuItem(menuItemIndex);
        progressText.setText(String.valueOf(progress));
        selectedMenu.setText(menuItems[menuItemIndex]);
        scrollerMenu.setMenuListener(this);
        return rootView;
    }

    @Override
    public void onMenuItemSelected(String[] menuItems, int index) {
        selectedMenu.setText(menuItems[index]);
        progress = 0;
        progressText.setText(String.valueOf(progress));
    }

    @Override
    public void onMenuItemProgressed(String[] menuItems, int index, int progressedBy) {
        selectedMenu.setText(menuItems[index]);
        progress += progressedBy;
        if (progress < PROGRESS_MIN) {
            progress = PROGRESS_MIN;
        }

        if (progress > PROGRESS_MAX) {
            progress = PROGRESS_MAX;
        }
        progressText.setText(String.valueOf(progress));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_BUNDLE_PROGRESS, progress);
        outState.putInt(KEY_BUNDLE_SELECTED_MENU_INDEX, scrollerMenu.getSelectedMenuItem());
        super.onSaveInstanceState(outState);
    }
}
