package com.kpi.campus.ui.presenter;

import com.kpi.campus.ui.activity.MainNotAuthActivity;

import dagger.Module;

/**
 * Dagger module created to provide UI dependencies like presenters.
 *
 * Created by Administrator on 28.01.2016.
 */
@Module(complete = false,
injects = {
        MainNotAuthActivity.class
})
public final class UIModule {
}
