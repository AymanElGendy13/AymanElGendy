package Utils;


import org.apache.commons.io.FileUtils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class utils {

    private static Properties properties;

    // Static block to load properties only once
    static {
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.properties"))
        {
            properties = new Properties();
            properties.load(fileInputStream);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Could not load config.properties file");
        }
    }

    /**
     * Reads a property from the config.properties file
     * @param key The key to lookup
     * @return The property value
     */
    public static String getProperty(String key)
    {
        return properties.getProperty(key);
    }


    /**
     * Parse login data from config file into a 2D array for DataProvider
     */
    public static Object[][] getLoginData()
    {
        String loginDataString = getProperty("loginData");
        String[] users = loginDataString.split(",");

        Object[][] loginData = new Object[users.length][2];
        for (int i = 0; i < users.length; i++)
        {
            String[] userPass = users[i].split(":");
            loginData[i][0] = userPass[0];  // username
            loginData[i][1] = userPass[1];  // password
        }

        return loginData;
    }

    public class CSVUtils {

        public static Object[][] getLoginDataFromCSV(String filePath) {
            List<Object[]> data = new ArrayList<>();
            String line;

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    data.add(new Object[]{values[0], values[1]});
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return data.toArray(new Object[0][]);
        }
    }


}