package com.kpi.campus.rx;

import com.kpi.campus.api.service.BulletinService;
import com.kpi.campus.model.pojo.Bulletin;
import com.kpi.campus.model.pojo.User;

import java.util.List;

import rx.Observable;

/**
 * This class responsible for producing responses for the
 * BulletinBoardModerator.
 * <p>
 * Created by Administrator on 28.03.2016.
 */
public class BulletinModeratorResponseManager extends BulletinResponseManager {

    @Override
    protected Observable<List<Bulletin>> getRequest(BulletinService service,
                                                    int limit, int lastId) {
        return service.getModeratorBulletins("bearer " + User.getInstance()
                .token, limit, lastId);
    }
}
