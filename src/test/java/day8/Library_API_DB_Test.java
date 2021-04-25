package day8;
import org.junit.jupiter.api.Test;
import test_util.DB_Utility;
import test_util.LibraryAuthBaseTest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static test_util.DB_Utility.*;


public class Library_API_DB_Test extends LibraryAuthBaseTest {

    //set up db connection in base test

    @Test
    public void testDashboardStatsNumbers(){

        runQuery("SELECT COUNT (*) FROM books");

        System.out.println(getFirstRowFirstColumn());
        String expectedBookCount = getFirstRowFirstColumn();

        runQuery("SELECT COUNT (*) FROM user");
        String expectedUserCount = getFirstRowFirstColumn();

        runQuery("SELECT COUNT (*) FROM book_borrow WHERE returned_date IS NULL");
        String expectedBorrowedBookCount = getFirstRowFirstColumn();

        given().header("X-LIBRARY-TOKEN",LibrarianToken).
        when()
                .get("/dashboard_status").
        then()
                .log().body()
                .statusCode(200)
                .body("book_count",is(expectedBookCount))
                .body("users",is(expectedUserCount))
                .body("borrowed_books",is(expectedBorrowedBookCount))
                ;


    }


}
