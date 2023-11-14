package org.cascav.cascavcore;

import org.cascav.cascavcore.Player.PlayerProperties;
import org.redisson.api.RMap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

public class Database
{
    //clone the database into a redis instance
    public static void ClonePlayerPropertiesDatabase()
    {
        try {
            String[] login = org.cascav.cascavcore.GetLogin.GetLoginInfo();
            if(login == null) return;

            // Connect to SQL database
            Connection sqlConnection = DriverManager.getConnection(login[0], login[1], login[2]);

            // Create a statement for SQL queries
            Statement statement = sqlConnection.createStatement();

            // Execute an SQL query to fetch data (replace with your query)
            String sqlQuery = "SELECT uuid, playerprops FROM playerproperties";
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            // Store map
            RMap<UUID, PlayerProperties> map = Redis.GetAllPlayerProperties();

            // Iterate over database and store elements in map
            while (resultSet.next())
            {
                UUID id = resultSet.getObject("uuid", UUID.class);
                PlayerProperties props = resultSet.getObject("playerprops", PlayerProperties.class);

                map.put(id, props);
            }
            // Update redis map
            Redis.UpdatePlayerProperties(map);

            // Closing connections
            resultSet.close();
            statement.close();
            sqlConnection.close();
        }
        catch (Exception e)
        {
            e.printStackTrace(); //print "fuck"
        }
    }

    public static void ClonePunishmentLogDatabase()
    {
        try {
            String[] login = org.cascav.cascavcore.GetLogin.GetLoginInfo();
            if(login == null) return;

            // Connect to SQL database
            Connection sqlConnection = DriverManager.getConnection(login[0], login[1], login[2]);

            // Create a statement for SQL queries
            Statement statement = sqlConnection.createStatement();

            // Execute an SQL query to fetch data (replace with your query)
            String sqlQuery = "SELECT uuid, playerprops FROM playerproperties";
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            // Store map
            RMap<UUID, ArrayList<PlayerProperties.Punishment>> map = Redis.GetAllPunishments();

            // Iterate over database and store elements in map
            while (resultSet.next())
            {
                UUID id = resultSet.getObject("uuid", UUID.class);
                ArrayList<PlayerProperties.Punishment> puns = resultSet.getObject("playerprops", ArrayList.class);

                map.put(id, puns);
            }
            // Update redis map
            Redis.UpdateAllPunishments(map);

            // Closing connections
            resultSet.close();
            statement.close();
            sqlConnection.close();
        }
        catch (Exception e)
        {
            e.printStackTrace(); //print "fuck"
        }
    }
}
