package com.kpi.campus.helper.matcher;

import android.view.View;

import org.hamcrest.Matcher;

/**
 * Created by Administrator on 03.03.2016.
 */
public class EspressoTestsMatchers {

    public static Matcher<View> withDrawable(final int resourceId) {
        return new DrawableMatcher(resourceId);
    }

    public static Matcher<View> noDrawable() {
        return new DrawableMatcher(-1);
    }
}
