package com.matchfinder.mis571.matchfinder.constant;


/**
 * SQL commands
 * Including select/delete/update/insert
 */


public abstract class SQLCommand
{
    //LOGIN
    //query to find password orf certain nicknames
    public static String QUERY_NAME = "select UserPassword from UserInfo where UserNickName LIKE ";
    //query to find UserID of certain nickname
    public static String QUERY_USERID ="select UserID from UserInfo where UserNickName LIKE";


    //SIGNUP
    //Populating the database with new user
    public static String NEW_USER = "insert into UserInfo(UserFirstName, UserLastName, UserNickName, UserGender, UserBirthDate, UserMajor, UserPhone, UserPassword, UserSecQuest) values(?,?,?,?,?,?,?,?,?)";

    public static String QUERY_NICKNAME = "select Count(UserNickName) as 'Count' from UserInfo where UserNickName LIKE ";
}
