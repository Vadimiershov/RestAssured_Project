package SelfPractice_Library;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import test_util.LibraryAuthBaseTest;
import test_util.SpartanNoAuthBaseTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetDashboardStatus extends LibraryAuthBaseTest {

    @DisplayName("Get Dashboard Status")
    @Test
    public void getDashboardStatus(){

        int users =
                given()
                    .log().all()
                    .header("x-library-token",LibrarianToken)
                    .contentType(ContentType.URLENC).
                when()
                    .get("/dashboard_stats").
                then()
                    .log().body()
                    .statusCode(200).
                extract()
                    .jsonPath().getInt("users")
                ;

    int book_count =
            given()
                    .log().all()
                    .header("x-library-token",LibrarianToken)
                    .contentType(ContentType.URLENC).
                    when()
                    .get("/dashboard_stats").
                    then()
                    .log().body()
                    .statusCode(200).
                    extract()
                    .jsonPath().getInt("book_count")
            ;

    Response response = given()
            .log().all()
            .header("x-library-token",LibrarianToken)
            .contentType(ContentType.URLENC).
                    when()
            .get("/dashboard_stats")
            ;
    String borrowed_books = response.path("borrowed_books");

        System.out.println("users = " + users);
        System.out.println("book_count = " + book_count);
        System.out.println("borrowed_books = " + borrowed_books);


    }

}
