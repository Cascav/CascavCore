package org.cascav.cascavcore;


public class Main
{
    public static void Initialize()
    {
        Redis.Initialize();

        Database.ClonePlayerPropertiesDatabase();
        Database.ClonePunishmentLogDatabase();

    }
}
