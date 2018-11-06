package com.matchfinder.mis571.matchfinder.constant;

import java.lang.reflect.Method;

public class Globals {
    private static Globals instance;


    //Global variable
    private String UserNickName;
    private Integer UserID;




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



    //Method to set the UserID
    public void setUserID(Integer d) {this.UserID=d;}

    //Method to get the UserID
    public Integer getUserID()  {return this.UserID;}




    public static synchronized Globals getInstance(){
        if(instance==null){
            instance=new Globals();
        }
        return instance;
    }



}





//FOR GETTING OR SETTING THESE VARIABLES

//Globals g = Globals.getInstance();
//g.setUserNickName(............);
//
//....
//int data=g.getUserNickName();
