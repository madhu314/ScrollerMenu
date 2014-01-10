Scroller Menu is an implementation of [snapseed](https://play.google.com/store/apps/details?id=com.niksoftware.snapseed) style menu chooser
1. Scroll vertically to choose a menu option
2. Scroll left/right to decrement/increment selected menu option value

###Usage
Wrap your view below the scroller menu in a frame layout. In the below example, I am placing scroller menu on top of my image view
`````
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:custom="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.appmogli.widget.scrollermenu.sampleapp.ImageHolderFragment"
    android:orientation="vertical">

    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:background="@android:color/black">

        <ImageView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:src="@drawable/colosseum"
            android:adjustViewBounds="true" />

        <com.appmogli.widget.scrollermenu.ScrollerMenu
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            custom:scrollerMenuItems="@array/menu_items"
            android:id="@+id/fragment_image_holder_scroller_menu">

        </com.appmogli.widget.scrollermenu.ScrollerMenu>


    </FrameLayout>

    <View
        android:layout_width="match_parent"
        android:layout_height="96dp"
        android:background="@android:color/holo_blue_light"></View>


</LinearLayout>
`````
Note that scrollerMenuItems is a mandatory attribute that refers to a string array resource form which menu items are populated.

###Callbacks
ScrollerMenu provides two callbacks
`````java
public void onMenuItemSelected(String[] menuItems, int index);
public void onMenuItemProgressed(String[] menuItems, int index, int progressedBy);
`````
you can listen to these callbacks by registering
`````java
yourScrollerMenuInstance.setMenuListener(..)
`````

###Customization
1. scrollerMenuItemHeight: Customize height of the menu item
2. scrollerMenuItemWidth: Customize width of the menu item
3. scrollerMenuItemTextColor: Menu item text color
4. scrollerMenuItemDefaultBackground: Default background to be used for non selected menu item
5. scrollerMenuItemSelectedBackground: Background for a selected menu item
6. scrollerMenuPanelBackground: Background for the panel that holds set of menu items


Check out included sample for a detailed code level understanding
