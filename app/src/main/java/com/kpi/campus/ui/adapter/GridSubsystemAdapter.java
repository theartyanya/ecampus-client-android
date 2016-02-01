package com.kpi.campus.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kpi.campus.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 01.02.2016.
 */
public class GridSubsystemAdapter extends BaseAdapter
{
    private List<Item> items = new ArrayList<Item>();
    private LayoutInflater inflater;

    public GridSubsystemAdapter(Context context)
    {
        inflater = LayoutInflater.from(context);

        items.add(new Item("Image 1", R.drawable.sample_0));
        items.add(new Item("Image 2", R.drawable.sample_1));
        items.add(new Item("Image 3", R.drawable.sample_2));
        items.add(new Item("Image 4", R.drawable.sample_3));
        items.add(new Item("Image 5", R.drawable.sample_4));
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i)
    {
        return items.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return items.get(i).drawableId;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        View v = view;
        ImageView picture;
        TextView name;

        if(v == null)
        {
            v = inflater.inflate(R.layout.gridview_item_subsystem, viewGroup, false);
            v.setTag(R.id.image_view_subsystem_logo, v.findViewById(R.id.image_view_subsystem_logo));
            v.setTag(R.id.text_view_subsystem_name, v.findViewById(R.id.text_view_subsystem_name));
        }

        picture = (ImageView)v.getTag(R.id.image_view_subsystem_logo);
        name = (TextView)v.getTag(R.id.text_view_subsystem_name);

        Item item = (Item)getItem(i);

        picture.setImageResource(item.drawableId);
        name.setText(item.name);

        return v;
    }

    private class Item
    {
        final String name;
        final int drawableId;

        Item(String name, int drawableId)
        {
            this.name = name;
            this.drawableId = drawableId;
        }
    }
}

