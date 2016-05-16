package ua.kpi.campus.di;

import ua.kpi.campus.ui.activity.AddBulletinActivity;
import ua.kpi.campus.ui.activity.BulletinBoardActivity;
import ua.kpi.campus.ui.activity.BulletinBoardModeratorActivity;
import ua.kpi.campus.ui.activity.BulletinContentActivity;
import ua.kpi.campus.ui.activity.EditBulletinActivity;
import ua.kpi.campus.ui.activity.LoginActivity;
import ua.kpi.campus.ui.activity.MainActivity;
import ua.kpi.campus.ui.activity.MainNotAuthActivity;

import dagger.Module;

/**
 * Dagger module created to provide UI dependencies like presenters.
 * <p>
 * Created by Administrator on 28.01.2016.
 */
@Module(complete = false,
        injects = {
                MainNotAuthActivity.class,
                LoginActivity.class,
                MainActivity.class,
                BulletinBoardActivity.class,
                BulletinContentActivity.class,
                AddBulletinActivity.class,
                BulletinBoardModeratorActivity.class,
                EditBulletinActivity.class
        })
public final class UIModule {
}
