package ua.kpi.ecampus.ui.view;

import android.view.View;

/**
 * Interface for listening recycler view list events.
 *
 * Created by Administrator on 05.02.2016.
 */
public interface OnItemClickListener {

    void onItemClicked(View view, int position, Object item);

}
