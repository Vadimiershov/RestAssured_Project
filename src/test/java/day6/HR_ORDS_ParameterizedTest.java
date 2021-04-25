package day6;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import test_util.HR_ORDS__API_BaseTest;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HR_ORDS_ParameterizedTest extends HR_ORDS__API_BaseTest {

    @Test
    public void testCountries(){

        get("/countries/AR").prettyPeek();

    }

    @ParameterizedTest
    @ValueSource(strings = {"AR","AU","US1"})
    public void testSingleCountryWithValues(String countryIDArg){

        //GET /countries/{country_id}
        given()
                .log().uri()
                .pathParam("country_id",countryIDArg).
        when()
                .get("/countries/{country_id}").
        then()
                .log().body()
                .statusCode(200)
                .body("count",is(1))
                ;
    }

    @ParameterizedTest
    @CsvSource({
            "AR, Argentina",
            "US, United States of America",
            "UK, United Kingdom"
    })
    public void testSingleCountryWithCSVSource(String countryIdArg, String countryNameArg){

    //SEND request to GET /countries/{country_id}
    //Expect country name match the corresponding country id
        given()
                .log().uri()
                .pathParam("country_id",countryIdArg).
        when()
                .get("/countries/{country_id}").
        then()
                .body("items[0].country_name",is (countryNameArg))
                ;
    }

    @ParameterizedTest
    @MethodSource("getManyCountryIds")
    public void testCountryMethodSource(String countryIdArg){

        given()
                .log().uri()
                .pathParam("country_id",countryIdArg).
                when()
                .get("/countries/{country_id}").
                then()
                .body("count",is(1))
        ;

    }

    //write a static method that return list of country ids
    public static List<String> getManyCountryIds(){

        //List<String>countryNameList = Arrays.asList("AR","BE","US");
        //SEND REQUEST TO GET /countries and save the country_id as List<String>

        List<String> countryNameList =
                get("/countries")
                        .jsonPath().getList("items.country_id",String.class);

        return countryNameList;
    }

}
















