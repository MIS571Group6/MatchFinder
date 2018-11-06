package com.matchfinder.mis571.matchfinder;

import android.app.Application;

public class Globals {
    private static Globals instance;


    //Global variable
    private String UserNickName;

    //Restrict the constructor from being instantiated
    private Globals(){}


    //Method to set the UserNickName
    public void setUserNickName(String d){
        this.UserNickName=d;
    }

    //Method to get the UserNickName
    public String getUserNickName(){
        return this.UserNickName;
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
//g.setUserNickName(............);
//
//....
//int data=g.getUserNickName();
