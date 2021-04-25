package day5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class CsvParameterizedTest {

    // Test first number + second number = third number
    // 1,3, 4
    // 2,3 5

    @ParameterizedTest
    @CsvSource({
            "1 ,3 ,4",
            "2 ,3 ,5",
            "8 ,7 ,15"
    })
    public  void testAddition(int num1, int num2, int expectedResult){

        System.out.println("num1 = " + num1);
        System.out.println("num2 = " + num2);
        System.out.println("expectedResult = " + expectedResult);
        assertThat(num1+num2,equalTo(expectedResult));

    }

    //Write a parameterized test for this request
    //GET api.zippopotam.us/us/{{state}}/{{city}}

    @ParameterizedTest
    @CsvSource({
            "NY, New York",
            "CO, Denver",
            "VA, Fairfax",
            "VA, Arlington",
            "MA, Boston",
            "NY, New York",
            "MD, Annapolis"
    })
    public void testStateCityToZIpEndpoint(String stateArg, String cityArg){

        System.out.println("stateArg = " + stateArg);
        System.out.println("cityArg = " + cityArg);
        int placeCount =
        given()
                .baseUri("https://api.zippopotam.us")
                .pathParam("state",stateArg)
                .pathParam("city",cityArg)
                .log().uri().
        when()
                .get("/us/{state}/{city}").
        then()
                .log().all()
                .statusCode(200)
        .extract()
            .jsonPath()
                .getList("places")
                .size()
        ;
        System.out.println("placeCount = " + placeCount);

    }
}
