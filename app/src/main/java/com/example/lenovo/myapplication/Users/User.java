package com.example.lenovo.myapplication.Users;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {

    public User(String userName, String userPass){
        super();
        super.setUsername(userName);
        super.setPassword(userPass);
//        super.setMobilePhoneNumber(phone);
    }
}
