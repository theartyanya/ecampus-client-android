package ua.kpi.ecampus.util.pagination;

import rx.Observable;

/**
 * Created by Administrator on 21.03.2016.
 */
public interface PagingListener<T> {
    Observable<T> onNextPage(int lastId);
}
