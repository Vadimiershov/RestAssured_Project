package day5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CSVFileSourceParameterizedTest {
    @ParameterizedTest
    @CsvFileSource (resources = "/state_city.csv", numLinesToSkip = 1)
    public void testStateCityToZipEndpointWithCSVFile(String stateArg, String cityArg, int zipArg){

        System.out.println("stateArg = " + stateArg);
        System.out.println("cityArg = " + cityArg);
        System.out.println("zipArg = " + zipArg);

        given()
                .baseUri("https://api.zippopotam.us")
                .pathParam("state",stateArg)
                .pathParam("city",cityArg)
                .log().uri().
        when()
                .get("/us/{state}/{city}").
                then()
                .statusCode(200)
                .body("places",hasSize(zipArg))
                ;
    }




}
