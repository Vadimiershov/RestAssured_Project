package day1;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.restassured.response.Response;
import test_util.SpartanNoAuthBaseTest;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


@DisplayName("Spartan App Get Request")
    public class SpartanNoAuthTest extends SpartanNoAuthBaseTest {

    @Test
    public void sayHello(){
        get("/hello").prettyPeek();
    }

    @Test
    public void getAllSpartans(){

            get("/hello").prettyPeek();

            get("/spartans").prettyPeek();
        }

    }



