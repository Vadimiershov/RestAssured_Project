package day5;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.xml.ws.Response;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParameterizedTestInJunit5 {

    @ParameterizedTest
    @ValueSource( ints = {1,2,3,4,5,6})

    public void testPrintMultipleIteration( int num){

        assertTrue(num > 0) ;

    }

    @ParameterizedTest
    @ValueSource(strings = {"Mustafa","Diana","Erjon","Akbar"})
    public void testName(String name){

        //assert that all the names has more than 5 characters
        assertTrue(name.length()>6);
    }

    //SEND GET REQUEST To http://api.zippopotam.us/us/{zipcode}
    //with these zipcodes 220303,22031,22032,22033,22034,22035
    //check the status code 200
    @ParameterizedTest
    @ValueSource(shorts = {22030,22031,22032,22033,22034,22035})
    public void testZipCodes(short zip){

        given()
                .baseUri("http://api.zippopotam.us")
                .log().all()
                .pathParam("zipcode", zip ).
        when()
                .get("/us/{zipcode}").
        then()
                .statusCode(200)
                .log().all()
                ;

    }

    @DisplayName("Find discount")
    @Test
    public void testGetDiscount(){
String bearerToken = "sk_test_4eC39HqLyjWDarjtT1zdp7dc";


//int totalCoupon =
        given()
                .baseUri("https://api.stripe.com")
                .log().all().
                given()
                .headers(
                        "Authorization",
                        "Bearer " + bearerToken,
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON).

        when()
                .get("/v1/coupons")
                .then()
                .statusCode(200)
                .log().body()
                .body("data.amount_off",hasItem(25))
        //.extract().jsonPath().getList("data.amount_off").size()
                ;


    }


}
