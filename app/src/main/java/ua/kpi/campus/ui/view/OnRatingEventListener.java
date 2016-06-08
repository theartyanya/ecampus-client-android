package ua.kpi.campus.ui.view;

import ua.kpi.campus.model.Rating;

/**
 * Created by Administrator on 08.06.2016.
 */
public interface OnRatingEventListener {
    void onRatingBarChange(Rating item, float value);
}
