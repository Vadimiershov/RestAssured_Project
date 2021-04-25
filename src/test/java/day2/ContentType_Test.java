package day2;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import test_util.SpartanNoAuthBaseTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@DisplayName("Spartan ContentType Summary")
public class ContentType_Test extends SpartanTest_PathVariableQueryParam{

    @DisplayName("GET /hello")
    @Test
    public void testHelloContentType(){

        when()
                .get("/hello").
        then()
                .contentType(ContentType.TEXT)
                .body(is("Hello from Sparta"))
        ;

    }

    @DisplayName("GET /spartans in json")
    @Test
    public void testSpartansJsonContentType(){

        given()
                .accept(ContentType.JSON). //asking json result from the server
        when()
                .get("/spartans").
        then()
                .contentType( ContentType.JSON ) // verifying the response body is json
                ;

    }

    @DisplayName("GET /")
    @Test
    public void testSpartansXmlContentType(){

        given()
                .accept(ContentType.XML). //asking json result from the server
                when()
                .get("/spartans").
                then()
                .contentType( ContentType.XML ) // verifying the response body is json
        ;

    }


}
