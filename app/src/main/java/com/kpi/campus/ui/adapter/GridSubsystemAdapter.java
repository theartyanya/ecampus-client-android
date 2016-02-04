package com.kpi.campus.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kpi.campus.R;
import com.kpi.campus.model.Subsystem;
import com.kpi.campus.util.CollectionValidator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 01.02.2016.
 */
public class GridSubsystemAdapter extends BaseAdapter {
    private List<Subsystem> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;

    public GridSubsystemAdapter(Context context, List<Subsystem> data) {
        mContext = context;
        mInflater = LayoutInflater.from(context);

        CollectionValidator.validateOnNull(mData);
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mData.get(i).getIconId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        GridViewHolder viewHolder;

        if (view == null) {
            // inflate the GridView item layout
            view = mInflater.inflate(R.layout.gridview_item_subsystem, viewGroup, false);

            // initialize the view holder
            viewHolder = new GridViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (GridViewHolder) view.getTag();
        }

        // update the item view
        Subsystem item = mData.get(i);
        viewHolder.viewIcon.setImageDrawable(ContextCompat.getDrawable(mContext, item.getIconId()));
        viewHolder.viewTitle.setText(item.getName());

        return view;

//        View v = view;
//        ImageView picture;
//        TextView name;
//
//        if(v == null)
//        {
//            v = mInflater.inflate(R.layout.gridview_item_subsystem, viewGroup, false);
//            v.setTag(R.id.image_view_subsystem_logo, v.findViewById(R.id.image_view_subsystem_logo));
//            v.setTag(R.id.text_view_subsystem_name, v.findViewById(R.id.text_view_subsystem_name));
//        }
//
//        picture = (ImageView)v.getTag(R.id.image_view_subsystem_logo);
//        name = (TextView)v.getTag(R.id.text_view_subsystem_name);
//
//        Subsystem item = (Subsystem)getItem(i);
//
//        picture.setImageDrawable(ContextCompat.getDrawable(mContext, item.getIconId()));
//        name.setText(item.getName());
//
//        return v;
    }

    protected static class GridViewHolder {
        @Bind(R.id.image_view_subsystem_logo)
        ImageView viewIcon;
        @Bind(R.id.text_view_subsystem_name)
        TextView viewTitle;

        public GridViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}

