package test_util;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class HR_ORDS__API_BaseTest {

    //http://75.101.202.237:1000/ords/hr/api/regions
    //base_uri = 75.101.202.237:1000
    //base_path = ords/hr/api
    // resources = /regions

    @BeforeAll
    public static void init(){
        baseURI = ConfigurationReader.getProperty("hr.ords.baseuri");
        basePath = "ords/hr/api";

        String url = ConfigurationReader.getProperty("hr.database.url");
        String username = ConfigurationReader.getProperty("hr.database.username");
        String password = ConfigurationReader.getProperty("hr.database.password");
        DB_Utility.createConnection(url,username,password);

    }



    @AfterAll
    public static void cleanup(){
        RestAssured.reset();
        DB_Utility.destroy();
//        reset();
    }



}
