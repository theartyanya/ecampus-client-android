package ua.kpi.ecampus.di;

import ua.kpi.ecampus.ui.activity.AddBulletinActivity;
import ua.kpi.ecampus.ui.activity.BulletinBoardActivity;
import ua.kpi.ecampus.ui.activity.BulletinBoardModeratorActivity;
import ua.kpi.ecampus.ui.activity.BulletinContentActivity;
import ua.kpi.ecampus.ui.activity.EditBulletinActivity;
import ua.kpi.ecampus.ui.activity.LoginActivity;
import ua.kpi.ecampus.ui.activity.MainActivity;
import ua.kpi.ecampus.ui.activity.MainNotAuthActivity;

import dagger.Module;
import ua.kpi.ecampus.ui.activity.RateTeacherActivity;
import ua.kpi.ecampus.ui.activity.VotingStudentActivity;

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
                EditBulletinActivity.class,
                VotingStudentActivity.class,
                RateTeacherActivity.class

        })
public final class UIModule {
}
