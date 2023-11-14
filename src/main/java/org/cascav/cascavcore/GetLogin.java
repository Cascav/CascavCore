package org.cascav.cascavcore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GetLogin
{
    public static String[] GetLoginInfo() {
        String[] login = new String[3];
        // Get the InputStream for the DBLogin.txt file from the resources folder
        try (InputStream inputStream = GetLogin.class.getClassLoader().getResourceAsStream("Logins/DBLogin.txt")) {
            if (inputStream == null) {
                System.err.println("File not found!");
                return null;
            }

            // Create a BufferedReader to read the file
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                // Read each line until the end of the file
                while ((line = reader.readLine()) != null) {
                    // Check if the line contains the 'username:' entry
                    if (line.startsWith("username:")) {
                        // Extract and return the username value
                        login[0] =  line.substring("username:".length()).trim();
                    }
                    if (line.startsWith("password:")) {
                        // Extract and return the username value
                        login[1] =  line.substring("password:".length()).trim();
                    }
                    if (line.startsWith("dburl:")) {
                        // Extract and return the username value
                        login[2] =  line.substring("dburl:".length()).trim();
                    }
                }
                if(login[0] == null || login[1] == null || login[2] == null) return null;
                return login;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return null if the 'username:' entry is not found
        return null;
    }
}
