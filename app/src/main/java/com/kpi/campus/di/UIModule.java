package com.kpi.campus.di;

import com.kpi.campus.ui.activity.LoginActivity;
import com.kpi.campus.ui.activity.MainActivity;
import com.kpi.campus.ui.activity.MainNotAuthActivity;

import dagger.Module;

/**
 * Dagger module created to provide UI dependencies like presenters.
 *
 * Created by Administrator on 28.01.2016.
 */
@Module(complete = false,
injects = {
        MainNotAuthActivity.class, LoginActivity.class, MainActivity.class
})
public final class UIModule {
}
