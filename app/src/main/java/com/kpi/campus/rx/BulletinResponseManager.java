package com.kpi.campus.rx;

import android.util.Log;

import com.kpi.campus.Config;
import com.kpi.campus.api.service.BulletinService;
import com.kpi.campus.api.service.ServiceCreator;
import com.kpi.campus.model.pojo.Bulletin;
import com.kpi.campus.model.pojo.User;
import com.kpi.campus.ui.presenter.BulletinBoardPresenter;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 24.03.2016.
 */
public class BulletinResponseManager {

    private static final long RESPONSE_TIME_IN_MS = 200;
    private final static int MAX_ERROR_COUNT = 2;
    private final static int OFFSET_WHEN_ERROR = 200;

    private static volatile BulletinResponseManager client;

    public static BulletinResponseManager getInstance() {
        if (client == null) {
            synchronized (BulletinResponseManager.class) {
                if (client == null) {
                    client = new BulletinResponseManager();
                }
            }
        }
        return client;
    }

    public Observable<List<Bulletin>> getResponse(int lastId, int limit) {
        BulletinService service = ServiceCreator.createService(BulletinService.class);
        Observable<List<Bulletin>> observable = service.getBulletins("bearer " + User.getInstance().token, limit, lastId);

        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Bulletin>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(Config.LOG, e.getMessage());
                    }

                    @Override
                    public void onNext(List<Bulletin> bulletins) {
                        BulletinBoardPresenter.IS_LOADING = false;

                        Log.d(Config.LOG, bulletins.size() + " loaded successful");
                    }
                });
        return observable;
    }
}
