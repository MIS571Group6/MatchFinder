package com.matchfinder.mis571.matchfinder;

import android.app.Application;

public class Globals extends Application {

    //Global variable
    private String UserName;

    //Restrict the constructor from being instantiated
    private Globals(){}

    public void setUserName(String d){
        this.UserName=d;
    }

    public String getUserName(){
        return this.UserName;
    }

    public static synchronized Globals getInstance(){
        if(instance==null){
            instance=new Globals();
        }
        return instance;
    }
}


//FOR GETTING OR SETTING THIS VARIABLE

//Globals g = Globals.getInstance();
//g.setData(100);
//
//....
//int data=g.getData();