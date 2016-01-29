package com.kpi.campus.model;

import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 28.01.2016.
 */
public class SubsystemManager {

    private static SubsystemManager mInstance;

    public static SubsystemManager getInstance() {
        if (mInstance == null) {
            mInstance = new SubsystemManager();
        }
        return mInstance;
    }

    public List<Subsystem> getSubsystems(String[] nameArray) {
        List<Subsystem> list = new ArrayList<>(nameArray.length);
        for (int i = 0; i < nameArray.length; i++) {
            Subsystem subsystem = new Subsystem(nameArray[i]);
            list.add(subsystem);
        }
        return list;
    }
}
