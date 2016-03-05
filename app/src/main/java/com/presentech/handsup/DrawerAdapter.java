package com.presentech.handsup;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Luke on 28/02/2016.
 * Adapter for icons and text for Drawer
 */
public class DrawerAdapter extends BaseAdapter{


    private Context context;
    private ArrayList<DrawerItem> DrawerItems;

    public DrawerAdapter(Context context, ArrayList<DrawerItem> navDrawerItems){
        this.context = context;
        this.DrawerItems = navDrawerItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_item_layout, null);
        }
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView textTitle = (TextView) convertView.findViewById(R.id.title);

        imgIcon.setImageResource(DrawerItems.get(position).getImageResourceID());
        textTitle.setText(DrawerItems.get(position).getString());
        return convertView;
    }

    //Override Getters
    @Override
    public int getCount() {
        return DrawerItems.size();
    }
    @Override
    public Object getItem(int position) {
        return DrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
