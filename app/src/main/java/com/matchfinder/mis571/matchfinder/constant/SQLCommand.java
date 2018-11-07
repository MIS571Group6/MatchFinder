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


    //Welcome
    //NEEDS TO BE ADJUSTED: "NOW" INSTEAD OF '2018-11-06'
    public static String QUERY_USERMATCHES = "SELECT Matches.MatchesID, Sport.SportName, strftime('%H:%M:%S', Matches.MatchesPlannedDate) as 'Time' \n" +
            "FROM Matches \n" +
            "inner join sport on matches.sportid=sport.sportid \n" +
            "INNER JOIN USERMATCH on matches.matchesid = usermatch.matchesid \n" +
            "WHERE JulianDay(date(matches.matchesplanneddate))=Julianday(date('2018-11-06')) \n" +
            "AND usermatch.userID =";



}
