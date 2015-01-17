package ua.kpi.campus.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ua.kpi.campus.R;
import ua.kpi.campus.model.TeacherItem;
import ua.kpi.campus.provider.ScheduleProvider;

/**
 * Created by dmitry on 17.01.15.
 */
public class TeacherAdapter implements ListAdapter {
    private static final String LOG_TAG = "TeacherAdapter";
    private final Context mContext;

    int mContentTopClearance = 0;
    Resources system = Resources.getSystem();
    ScheduleProvider scheduleProvider;
    ArrayList<TeacherItem> mItems;
    
    public TeacherAdapter(Context mContext){
        this.mContext=mContext;
        scheduleProvider = new ScheduleProvider(mContext);
        mItems = scheduleProvider.getTeachersFromDatabase();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return position >= 0 && position < mItems.size() ? mItems.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Resources res = mContext.getResources();
        boolean isSeparator=false;
        TextView separator = null;
        int itemViewType = getItemViewType(position);
        int layoutResId = R.layout.item_teacher;

        if (position < 0 || position >= mItems.size()) {
            Log.e(LOG_TAG, "Invalid view position passed to MyTeacherAdapter: " + position);
            return view;
        }

        Log.d("teachersFragment","in getView");

        final TeacherItem item = mItems.get(position);
        TeacherItem nextItem = (position < mItems.size() - 1) ? mItems.get(position + 1) : null;


        view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(layoutResId, parent, false);
        ((TextView)view.findViewById(R.id.teacher_name)).setText(item.getTeacherName());


        return view;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return mItems.isEmpty();
    }
}
