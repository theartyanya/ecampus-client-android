package com.kpi.campus.rx;

import android.util.Log;

import com.kpi.campus.Config;
import com.kpi.campus.api.service.BulletinService;
import com.kpi.campus.api.service.ServiceCreator;
import com.kpi.campus.model.pojo.Bulletin;
import com.kpi.campus.model.pojo.User;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 24.03.2016.
 */
public class BulletinResponseManager {

    public Observable<List<Bulletin>> getResponse(int lastId, int limit) {
        BulletinService service = ServiceCreator.createService(BulletinService.class);
        Observable<List<Bulletin>> observable = getRequest(service, limit, lastId);

        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Bulletin>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if(e != null)
                            Log.e(Config.LOG, e.getMessage());
                    }

                    @Override
                    public void onNext(List<Bulletin> bulletins) {
                        Log.d(Config.LOG, bulletins.size() + " loaded successful");
                    }
                });
        return observable;
    }

    protected Observable<List<Bulletin>> getRequest(BulletinService service, int limit, int lastId) {
        return service.getBulletins("bearer " + User.getInstance().token, limit, lastId);
    }
}
