package com.kpi.campus.model;

/**
 * Created by Administrator on 10.02.2016.
 */
public class LoginData {

    String username;

    String password;

    String grant_type;



    public LoginData(String username, String password) {

        this.username = username;

        this.password = password;

        grant_type = "password";

    }
}
