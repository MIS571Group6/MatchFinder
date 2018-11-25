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

    public static String QUERY_NICKNAMECOUNT = "select Count(UserNickName) as 'Count' from UserInfo where UserNickName LIKE ";


    //WELCOME
    //NEEDS TO BE ADJUSTED: "NOW" INSTEAD OF '2018-11-06'
    public static String QUERY_USERMATCHES = "SELECT Matches.MatchesID as 'MatchesID', Sport.SportName as 'SportName', strftime('%H:%M:%S', Matches.MatchesPlannedDate) as 'Time' \n" +
            "FROM Matches \n" +
            "inner join sport on matches.sportid=sport.sportid \n" +
            "INNER JOIN USERMATCH on matches.matchesid = usermatch.matchesid \n" +
            "WHERE JulianDay(date(matches.matchesplanneddate))=Julianday(date('2018-11-06')) \n" +
            "AND usermatch.userID =";


    // DETAIL VIEW
    // query to find out the sportname for a certain matchesID
    public static String QUERY_OFFERDETAILS = "Select matches.matchesPlayerCount as 'PCount', strftime('%m-%d-%Y',matches.matchesCreationDate) as 'CreationDate', strftime('%m-%d-%Y',matches.matchesplanneddate) as 'PlannedDate', matches.matchesplannedplace as 'Location', sport.sportname as 'sportname', strftime('%H:%M:%S', Matches.MatchesPlannedDate) as 'PlannedTime' from matches inner join Sport on matches.sportid = Sport.sportid where matchesID LIKE";

    //query to find out the average skill level of a match (split to place a variable string in between)
    public static String QUERY_AVGSKILL_1 = "Select Round(avg(skill.skillgroup),0) as 'AverageSkill' From UserMatch Inner join Matches on UserMatch.MatchesID = Matches.MatchesID Inner join Skill on UserMatch.UserID = Skill.UserID Where UserMatch.MatchesID = ";
    public static String QUERY_AVGSKILL_2 = "and Skill.SportID = Matches.SportID Group by UserMatch.MatchesID";

    //query to find out all users that are participating at a specific match and their skills
    public static String QUERY_USERS = "Select UserMatch.UserID as 'UserID', UserInfo.UserNickName as 'NickName', Skill.Skillgroup as 'Skill' from UserMatch Inner Join UserInfo on UserMatch.UserID = UserInfo.UserID Inner Join Skill on UserMatch.UserID = Skill.UserID Inner Join Matches on UserMatch.MatchesID = Matches.MatchesID Where (Skill.SportID = Matches.SportID OR skill.sportID is Null) and UserMatch.MatchesID LIKE";

    //query to find out if current user is participating in a certain match --> COULD BE ORDERED BY SKILLGROUP DESC
    public static String QUERY_USERSKILLS = "Select Sport.SportName as 'SportName', Skill.SkillGroup as 'SkillGroup' from Sport left Join Skill on sport.SportID = Skill.SportID where Skill.UserID LIKE ";




    //PROFILES
    //query to find out Details for certain userID
    public static String QUERY_NICKNAME = "select UserNickname as 'NickName', UserMajor as 'Major', UserPhone as 'Phone', (strftime('%Y', 'now') - strftime('%Y', UserBirthDate)) - (strftime('%m-%d', 'now') < strftime('%m-%d', UserBirthDate)) as 'Age', UserGender as 'Gender' from UserInfo where UserID = ";



    //OFFERED MATCHES
    //query to find all offered matches that are not cancelled or already done
    public static String QUERY_MATCHES = "select Matches.MatchesID as 'MatchesID', Sport.Sportname as 'SportName', Matches.MatchesPlannedDate as 'PlannedDate', Matches.MatchesPlayerCount as 'PlayerCount' from Matches Inner Join Sport on Matches.SportID = Sport.SportID Where Matches.MatchesDone LIKE 'N';";



}
