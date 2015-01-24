package ua.kpi.campus.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ua.kpi.campus.R;
import ua.kpi.campus.model.ScheduleItem;
import ua.kpi.campus.ui.ScheduleItemDetail;

/**
 * Created by doroshartyom on 12.01.2015.
 */
public class ScheduleAdapter implements ListAdapter, AbsListView.RecyclerListener {

    private static final String LOG_TAG = "ScheduleAdapter";

    private final Context mContext;
    

    int mContentTopClearance = 0;

    ArrayList<ScheduleItem> mItems = new ArrayList<ScheduleItem>();
    ArrayList<DataSetObserver> mObservers = new ArrayList<DataSetObserver>();

    int mDefaultLessonColor;
    private String[] days = new String[6];
    public ScheduleAdapter(Context mContext) {
        this.mContext = mContext;
        days= new String[] {
                mContext.getString(R.string.monday),
                mContext.getString(R.string.tuesday),
                mContext.getString(R.string.wednesday),
                mContext.getString(R.string.thursday),
                mContext.getString(R.string.friday),
                mContext.getString(R.string.saturday)
        };
        mDefaultLessonColor = mContext.getResources().getColor(R.color.primary);
    }
    Resources system = Resources.getSystem();



    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        if (!mObservers.contains(observer)) {
            mObservers.add(observer);
        }
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (mObservers.contains(observer)) {
            mObservers.remove(observer);
        }
    }

    public void setContentTopClearance(int padding) {
        mContentTopClearance = padding;
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
    public View getView(final int position, View view, ViewGroup parent) {
        Resources res = mContext.getResources();
        TextView startTimeView = null;
        TextView endTimeView = null;
        TextView separator = null;
        boolean isSeparator = false;

        int itemViewType = getItemViewType(position);
        int layoutResId = R.layout.schedule_item;



        if (position < 0 || position >= mItems.size()) {
            Log.e(LOG_TAG, "Invalid view position passed to MyScheduleAdapter: " + position);
            return view;
        }

        final ScheduleItem item = mItems.get(position);
        ScheduleItem nextItem = (position < mItems.size() - 1) ? mItems.get(position + 1) : null;

        if (item.isDevider()) {
            layoutResId = R.layout.schedule_devider;
            isSeparator = true;
        }

        view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(layoutResId, parent, false);

        if (isSeparator) {

            view.setOnClickListener(null);
            view.setOnLongClickListener(null);
            view.setLongClickable(false);

            separator = (TextView) view.findViewById(R.id.separator_text);
            separator.setText(days[item.getDayNumber()-1]);
        } else {
            startTimeView = (TextView) view.findViewById(R.id.start_time);
            endTimeView = (TextView) view.findViewById(R.id.end_time);
            TextView slotTitleView = (TextView) view.findViewById(R.id.slot_title);
            TextView slotSubtitleView = (TextView) view.findViewById(R.id.slot_subtitle);
            TextView place = (TextView) view.findViewById(R.id.slot_room);



            slotTitleView.setText(item.getLessonName());
            slotSubtitleView.setText(item.getTeacherName());
            place.setText(item.getLessonRoom());

            startTimeView.setText(item.getTimeStart());
            endTimeView.setText(item.getTimeEnd());
            View.OnClickListener onClickListener = new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(mContext.getApplicationContext(),ScheduleItemDetail.class);
                    intent.putExtra("scheduleItem",item);
                    mContext.startActivity(intent);
                }
            };
            view.findViewById(R.id.color_view).setOnClickListener(onClickListener);
        }
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

    @Override
    public void onMovedToScrapHeap(View view) {

    }

    public void updateItems(ArrayList<ScheduleItem> items) {
        mItems.clear();
        if (items != null) {
            for (ScheduleItem item : items) {
                Log.d(LOG_TAG, "Adding schedule item");
                mItems.add(item);
            }
        }
        notifyObservers();
    }

    private void notifyObservers() {
        for (DataSetObserver observer : mObservers) {
            observer.onChanged();
        }
    }
}
