package com.kpi.campus.rx;

import android.util.Log;

import com.kpi.campus.Config;
import com.kpi.campus.api.service.BulletinService;
import com.kpi.campus.api.service.ServiceCreator;
import com.kpi.campus.model.pojo.Bulletin;
import com.kpi.campus.model.pojo.User;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 13.04.2016.
 */
public class BulletinRxLoader {

    public void addBulletin(Bulletin bulletin) {
        BulletinService service = ServiceCreator.createService
                (BulletinService.class);
        Observable<String> observable = service.postNewBulletin("bearer " +
                User.getInstance().token, bulletin);

        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNextAction, onErrorAction);
    }

    Action1<String> onNextAction = responseMsg -> {

    };

    Action1<Throwable> onErrorAction = e -> Log.e(Config.LOG, e.getMessage());
}
