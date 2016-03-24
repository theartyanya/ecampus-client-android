package com.kpi.campus.rx;

import android.util.Log;

import com.kpi.campus.Config;
import com.kpi.campus.api.service.BulletinService;
import com.kpi.campus.api.service.ServiceCreator;
import com.kpi.campus.model.Bulletin;
import com.kpi.campus.model.User;
import com.kpi.campus.ui.presenter.BulletinBoardPresenter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 24.03.2016.
 */
public class BulletinResponseManager {

    private final int SUP_ID = 500;

    private final static int MAX_LIMIT = 1000;
    private static final long FAKE_RESPONSE_TIME_IN_MS = 200;
    private final static int MAX_FAKE_ERROR_COUNT = 2;
    private final static int OFFSET_WHEN_FAKE_ERROR = 200;

    private static volatile BulletinResponseManager client;

    private int fakeErrorCount = 0;

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

    public Observable<List<Bulletin>> getEmulateResponse(int lastId, int limit) {
        BulletinService service = ServiceCreator.createService(BulletinService.class);
        Observable<List<Bulletin>> observable = service.getBulletins("bearer " + User.getInstance().token);

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

    private List<Bulletin> getFakeItemList(int lastId, int limit) {
        List<Bulletin> list = new ArrayList<>();

        if(lastId == -1) {
            // load all from start
            lastId = 0;
        }

        int concreteValue = lastId + limit;
        // Generate List of Items
        for (int i = lastId; i < concreteValue; i++) {
            int id = i + SUP_ID;
            String itemStr = String.valueOf(id);
            list.add(new Bulletin(itemStr, itemStr, "author", "24.03.2016"));
        }
        return list;
    }

}
