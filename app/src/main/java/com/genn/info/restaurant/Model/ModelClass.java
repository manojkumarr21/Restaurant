package com.genn.info.restaurant.Model;

import android.content.Context;


import com.genn.info.restaurant.Connection.ConnectionClass;

public class ModelClass {


    static String user;
    static String pass;
    public static String id="GENN";
    Context context;

    ConnectionClass connectionClass;


    public ModelClass( String user, String pass) {

        this.user = user;
        this.pass = pass;
    }

    public ModelClass(Context context){

        this.context=context;

    }



    public static String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public static String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}

