package test_util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {

    // In this class we will implement the repeated steps of reading
    // from config.properties file

    //#1 - Create the object of Prop
    private static Properties properties = new Properties();

    static {
        //#2 Get the path and open the file
        try {
            FileInputStream file = new FileInputStream("configuration.properties");


            //#3 - Load the opened file into properties object
            properties.load(file);

            // closing the file into properties object
            file.close();


        }catch (FileNotFoundException e){

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //#4 - Use the object to read from the configuration.properties file

    public static String getProperty (String keyWord){
        return properties.getProperty(keyWord);



    }

}




