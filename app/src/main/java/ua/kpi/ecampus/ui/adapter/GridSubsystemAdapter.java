package ua.kpi.ecampus.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.kpi.ecampus.model.Subsystem;
import ua.kpi.ecampus.util.CollectionValidator;

/**
 * GridSubsystemAdapter manages Subsystem data model and adapts it to
 * GridView, which is in MainActivity.
 * <p>
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
            view = mInflater.inflate(ua.kpi.ecampus.R.layout.gridview_item_subsystem,
                    viewGroup, false);

            // initialize the view holder
            viewHolder = new GridViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (GridViewHolder) view.getTag();
        }

        // update the item view
        Subsystem item = mData.get(i);
        viewHolder.viewIcon.setImageDrawable(ContextCompat.getDrawable
                (mContext, item.getIconId()));
        viewHolder.viewTitle.setText(item.getName());

        return view;
    }

    static class GridViewHolder {
        @BindView(ua.kpi.ecampus.R.id.image_view_subsystem_logo)
        ImageView viewIcon;
        @BindView(ua.kpi.ecampus.R.id.text_view_subsystem_name)
        TextView viewTitle;

        GridViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}

