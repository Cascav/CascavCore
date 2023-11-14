package org.cascav.cascavcore;


public class Main
{
    public static void Initialize()
    {
        Redis.Initialize();

        Database.ClonePlayerPropertiesDatabase();
        Database.ClonePunishmentLogDatabase();

        //froge
    }

    //write the entry point of my program
    public static void main(String[] args)
    {
        String[] login = GetLogin.GetLoginInfo();
        System.out.println(login != null ? login[0] + " " + login[1] + " " + login[2] : "No login info found");
    }
}
