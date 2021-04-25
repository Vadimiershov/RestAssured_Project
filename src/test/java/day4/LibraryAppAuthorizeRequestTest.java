package day4;


import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import test_util.LibraryAuthBaseTest;
import static org.hamcrest.Matchers.*;

import java.util.*;

public class LibraryAppAuthorizeRequestTest extends LibraryAuthBaseTest {


    @DisplayName("GET / get_user_by_id/{user_id}")
    @Test
    public void testOneUser(){

        given()
                .log().all()
                .header("x-library-token", LibrarianToken)
                .pathParam("user_ID",1).
        when()
                .get("get_user_by_id/{user_ID}").
        then()
                .log().all()
                .statusCode(200)
                ;
    }

    @DisplayName("GET / /get_all_users")
    @Test
    public void testGetAllUsers(){

        List<String> allUsers =
        given()
                .header("x-library-token", LibrarianToken).
        when()
                .get("get_all_users").
        then()
                .statusCode(200)
        .extract()
                .jsonPath()
                .getList("name",String.class)
                ;

        assertThat(allUsers,hasSize(487));
        //finding unique names into the set
        Set<String> uniqueNames = new HashSet<>(allUsers);

        System.out.println("Total unique names  : "+uniqueNames.size());
    }

    @DisplayName("POST //add_book")
    @Test
    public void testAddOneBook(){

        Map<String,Object> myBookMap = getRandomBook();

        int bookID =
        given()
                .log().all()
                .header("x-library-token", LibrarianToken)
                .contentType(ContentType.URLENC)
                //using formParams with s we can pass multiple param in one shot
                .params( myBookMap).
        //.body(myBookMap). will work with JSON content type
        when()
                .post("/add_book").
        then()
                .log().all()
                .statusCode(200).
        extract()
                .jsonPath().getInt("book_id");

        given()
                .header("x-library-token", LibrarianToken)
                .log().uri()
                .pathParam("book_id",bookID).
        when()
                .get("/get_book_by_id/{book_id}").
        then()
                .log().body()
                .statusCode(200)
                .body("id", is(bookID+""))
                .body("name",is(myBookMap.get("name")))
                .body("isbn",is(myBookMap.get("isbn")))
                ;
        System.out.println(myBookMap.get("isbn"));

    }

}
