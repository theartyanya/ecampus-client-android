package ua.kpi.ecampus.api.service;

import ua.kpi.ecampus.model.pojo.User;

import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;

/**
 * Contains list of APIs related to User.
 * <p>
 * Created by Administrator on 17.03.2016.
 */
public interface UserService {

    @GET("/account/info")
    Observable<User> getUser(@Header("Authorization") String authorization);
}
