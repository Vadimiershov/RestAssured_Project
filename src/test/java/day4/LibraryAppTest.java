package day4;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.List;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@DisplayName("Library App Simple Test")
public class LibraryAppTest extends  LibraryAppAuthorizeRequestTest{

    public static int usersCount;

    @DisplayName("Test POST/ login")
    @Test
    public void testLogin(){

        //librarian11@library
        //shpUFwRF

        given()
                .log().all()
                .contentType(ContentType.URLENC)
                .formParam("email","librarian11@library")
                .formParam("password","shpUFwRF").
        when()
                .post("/login").
        then()
                .statusCode(200)
                .log().all()
                .body("token",is( not( blankOrNullString()) ))
        ;
    }

    @DisplayName("Test POST /decode")
    @Test
    public void testGetTokenDecodeToken() {

        String username = "librarian11@library";
        String password = "shpUFwRF";
        String myToken =
                given()
                        .log().all()
                        .contentType(ContentType.URLENC)
                        .formParam("email", username)
                        .formParam("password", password).
                        when()
                        .post("/login").
                        then()
                        .log().all()
                        .statusCode(200)
                        .extract().path("token");

        given()
                .log().all()
                .contentType(ContentType.URLENC)
                .formParam("token", myToken).
        when()
                .post("/decode").
        then()
                .statusCode(200)
                .log().all()
                .body("email",is(username))
                ;
    }

    @DisplayName("GET /dashboard Get dashboard stats")
    @Test
    public void testDashboardNumbers(){

        given()
                .header("x-library-token",LibrarianToken).
        when()
                .get("/dashboard_stats").
        then()
                .log().all()
                .statusCode(200)
//                .body("book_count", is ())
//                .body("borrowed_books", is ("79"))
                  .body("users", is (usersCount))
        ;
        //assertion thru JsonPath body validation
        JsonPath jp =
        given()
                .header("x-library-token",LibrarianToken).
        when()
                .get("/dashboard_stats").
        then()
                .statusCode(200).
        extract()
                .jsonPath();

        int book_count = jp.getInt("book_count");
        int borrowed_books = jp.getInt("borrowed_books");
        int users = jp.getInt("users");

        assertThat(jp.getInt("book_count"),is (book_count));
        assertThat(jp.getInt("borrowed_books"),is (borrowed_books));
        assertThat(jp.getInt("users"),is (usersCount));

    }

    @DisplayName("GET /get_all_users")
    @Test
    public void getAllUsers(){

        List<String> allUsersID =
                given()
                    .header("x-library-token",LibrarianToken).
                when()
                    .get("/get_all_users").
                then()
                    .statusCode(200)
                    .log().body().
                extract()
                    .jsonPath()
                    .getList("id")
                ;

        int usersCount = allUsersID.size();
    }



}
