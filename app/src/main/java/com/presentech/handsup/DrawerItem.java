package com.presentech.handsup;

/**
 * Created by Luke on 28/02/2016.
 * Drawer item Class
 */
public class DrawerItem {
    private int imageResourceID;
    private String itemString;

    public DrawerItem() {
    }

    public DrawerItem(int imageResourceID, String itemString) {
        this.imageResourceID = imageResourceID;
        this.itemString = itemString;
    }

    public String getString() {
        return this.itemString;
    }

    //Create Getters and Setters
    public int getImageResourceID() {
        return imageResourceID;
    }

    public void setString(String string) {
        this.itemString = string;
    }

    public void setIcon(int icon) {
        this.imageResourceID = icon;
    }
}
