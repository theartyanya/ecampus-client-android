package com.kpi.campus.rx;

import android.util.Log;

import com.kpi.campus.Config;
import com.kpi.campus.api.service.ServiceCreator;
import com.kpi.campus.api.service.UserService;
import com.kpi.campus.model.pojo.User;
import com.kpi.campus.ui.Preference;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Perform requests of the UserService class.
 *
 * Created by Administrator on 21.03.2016.
 */
public class UserRxLoader {

    private Preference mPreference;

    public  UserRxLoader(Preference preference) {
        mPreference = preference;
    }

    /**
     * Implements the request to the api function.
     */
    public void apiCall() {
        UserService service = ServiceCreator.createService(UserService.class);
        Observable<User> observable = service.getUser("bearer " + User.getInstance().token);

        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNextAction, onErrorAction);
    }

    Action1<User> onNextAction = new Action1<User>() {

        @Override
        public void call(User userInfo) {
            User user = User.getInstance();
            user.id = userInfo.id;
            user.name = userInfo.name;
            user.position = userInfo.position;
            user.subdivision = userInfo.subdivision;
            user.descendantSubdivisions = userInfo.descendantSubdivisions;
            user.isBulletinBoardModerator = userInfo.isBulletinBoardModerator;

            mPreference.saveUserInfo(user);

            Log.d(Config.LOG, "Successful download of information about the User ".concat(user.name));
        }
    };

    Action1<Throwable> onErrorAction = e -> Log.e(Config.LOG, e.getMessage());
}
