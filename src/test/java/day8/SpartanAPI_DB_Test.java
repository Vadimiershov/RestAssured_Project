package day8;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.SpartanPOJOwithID;
import test_util.ConfigurationReader;
import test_util.DB_Utility;
import test_util.SpartanNoAuthBaseTest;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Map;

public class SpartanAPI_DB_Test extends SpartanNoAuthBaseTest {

    @Test
    public void testDB_Connection() {

        DB_Utility.runQuery("SELECT * FROM SPARTANS");
        DB_Utility.displayAllData();

    }

    @DisplayName("Test GET /spartans/{id} match DB Data")
    @Test
    public void testGetSingleSpartanResponseMatchDB_Result() {

        //expected data --DB query result
        //actual data   --api response json
        int spartanIdToCheck = 106;

        DB_Utility.runQuery("SELECT * FROM SPARTANS WHERE SPARTAN_ID = " + spartanIdToCheck);
        DB_Utility.displayAllData();

        Map<String, String> firstRowMap = DB_Utility.getRowMap(1);
        System.out.println("firstRowMap = " + firstRowMap);

        //send api request
        given()
                .pathParam("id", spartanIdToCheck)
                .log().uri().
                when()
                .get("/spartans/{id}").
                then()
                .log().all()
                .statusCode(200)
                .body("id", is(spartanIdToCheck))
                .body("name", is(firstRowMap.get("NAME")))
                .body("gender", is(firstRowMap.get("GENDER")))
                .body("phone", is(Integer.parseInt(firstRowMap.get("PHONE"))))
        ;
    }

    @DisplayName("Test GET /spartans/{id} match with POJO")
    @Test
    public void testGetSingleSpartanResponsePOJOMatchDB_Result() {

        int spartanIdToCheck = 106;

        DB_Utility.runQuery("SELECT * FROM SPARTANS WHERE SPARTAN_ID="   + spartanIdToCheck);
        Map<String,String> expectedResultMap = DB_Utility.getRowMap(1);

        SpartanPOJOwithID actualResultInPojo = given()
                .pathParam("id",spartanIdToCheck).
                when()
                .get("/spartans/{id}")
                .as(SpartanPOJOwithID.class);
        System.out.println("actualResultInPojo = " + actualResultInPojo);

        assertThat(actualResultInPojo.getId(), is (spartanIdToCheck));
        assertThat(actualResultInPojo.getName(),is(expectedResultMap.get("NAME")));
        assertThat(actualResultInPojo.getPhone(),is(Long.parseLong(expectedResultMap.get("PHONE"))));

    }


    @DisplayName("Test GET /spartans/{id} match with POJO2")
    @Test
    public void testGetSingleSpartanResponsePOJOMatchDB_Result2() {



        DB_Utility.runQuery("SELECT * FROM SPARTANS");
        Map<String,String> expectedResultMap = DB_Utility.getRowMap(1);
//get the ID from this map and save it into below variable
        int spartanIdToCheck = Integer.parseInt(expectedResultMap.get("SPARTAN_ID"));

                SpartanPOJOwithID actualResultInPojo = given()
                .pathParam("id",spartanIdToCheck).
                        when()
                .get("/spartans/{id}")
                .as(SpartanPOJOwithID.class);
        System.out.println("actualResultInPojo = " + actualResultInPojo);

        assertThat(actualResultInPojo.getId(), is (spartanIdToCheck));
        assertThat(actualResultInPojo.getName(),is(expectedResultMap.get("NAME")));
        assertThat(actualResultInPojo.getGender(),is(expectedResultMap.get("GENDER")));
        assertThat(actualResultInPojo.getPhone(),is(Long.parseLong(expectedResultMap.get("PHONE"))));

    }

//AS HOMEWORK



}
