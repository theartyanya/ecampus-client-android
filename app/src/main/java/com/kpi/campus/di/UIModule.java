package com.kpi.campus.di;

import com.kpi.campus.ui.activity.BulletinBoardActivity;
import com.kpi.campus.ui.activity.BulletinBoardModeratorActivity;
import com.kpi.campus.ui.activity.BulletinContentActivity;
import com.kpi.campus.ui.activity.LoginActivity;
import com.kpi.campus.ui.activity.MainActivity;
import com.kpi.campus.ui.activity.MainNotAuthActivity;
import com.kpi.campus.ui.activity.NewBulletinActivity;
import com.kpi.campus.ui.activity.SplashScreenActivity;

import dagger.Module;

/**
 * Dagger module created to provide UI dependencies like presenters.
 *
 * Created by Administrator on 28.01.2016.
 */
@Module(complete = false,
injects = {
        SplashScreenActivity.class,
        MainNotAuthActivity.class,
        LoginActivity.class,
        MainActivity.class,
        BulletinBoardActivity.class,
        BulletinContentActivity.class,
        NewBulletinActivity.class,
        BulletinBoardModeratorActivity.class
})
public final class UIModule {
}
