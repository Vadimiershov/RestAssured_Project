package day8;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Driver;
import test_util.LibraryAuthBaseTest;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class FormulaOneAPI_Test {

    @BeforeAll
    public static void init(){

        baseURI = "http://ergast.com";
        basePath = "/api/f1";

    }

    @DisplayName("GET /drivers.json and save result to json")
    @Test
    public void testDrivers(){

        JsonPath jp = get("/drivers.json").jsonPath();
        Driver d1 = jp.getObject("MRData.DriverTable.Drivers[0]",Driver.class);
        System.out.println("d1 = " + d1);

        List<Driver> allDrivers = jp.getList("MRData.DriverTable.Drivers", Driver.class);

        //Print the name of all American drivers in this list

        for (Driver driver : allDrivers) {
            if(driver.getNationality().equals("American")){
                System.out.println("driver.getGivenName() = " + driver.getGivenName());
            }
        }

    }

    @AfterAll
    public static void cleanup(){

        reset();

    }

}
