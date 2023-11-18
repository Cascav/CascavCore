package org.cascav.cascavcore;


public class CascavCore
{
    public static void main(String[] args)
    {
        Initialize();
    }

    public static void Initialize()
    {
        Redis.Initialize();

        Database.ClonePlayerPropertiesDatabase();
        Database.ClonePunishmentLogDatabase();

        //froge
    }
}
