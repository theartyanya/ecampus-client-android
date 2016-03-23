package com.kpi.campus.rx;

import android.util.Log;

import com.kpi.campus.Config;
import com.kpi.campus.api.service.BulletinService;
import com.kpi.campus.api.service.ServiceCreator;
import com.kpi.campus.model.BulletinBoard;
import com.kpi.campus.model.User;
import com.kpi.campus.model.dao.IDataAccessObject;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 21.03.2016.
 */
public class BulletinRxLoader extends BaseRxLoader {

    public BulletinRxLoader(IDataAccessObject obj) {
        dataAccessObject = obj;
    }

    @Override
    public void apiCall() {
        BulletinService service = ServiceCreator.createService(BulletinService.class);
        Observable<List<BulletinBoard>> observable = service.getBulletins("bearer " + User.getInstance().token);

        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNextAction, onErrorAction);
    }

    Action1<List<BulletinBoard>> onNextAction = new Action1<List<BulletinBoard>>() {

        @Override
        public void call(List<BulletinBoard> bulletins) {
            //dataAccessObject.setData(bulletins);

            Log.d(Config.LOG, "Load successful");
        }
    };

    Action1<Throwable> onErrorAction = new Action1<Throwable>() {

        @Override
        public void call(Throwable e) {
            Log.e(Config.LOG, e.getMessage());
        }
    };

}
