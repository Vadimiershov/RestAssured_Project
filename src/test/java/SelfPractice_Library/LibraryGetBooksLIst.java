package SelfPractice_Library;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.BorrowedBook;
import pojo.BorrowedBooksBYUser;
import test_util.LibraryAuthBaseTest;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class LibraryGetBooksLIst extends LibraryAuthBaseTest {

    @DisplayName("GET //get_book_list_for_borrowing")
    @Test
    public void getBookListForBorrowing(){

    List<BorrowedBook> listOfBooks =
            given()
                    .header("x-library-token",LibrarianToken).
            when()
                    .get("/get_book_list_for_borrowing")
                    .jsonPath().getList("",BorrowedBook.class);

//    for (BorrowedBook each: listOfBooks) {
//        if(each.getDisabled().equals("1"))
//            System.out.println("each = " + each);
//    }

        listOfBooks.forEach( System.out::println);
}

    @DisplayName("GET /get_borrowed_books_by_user/{user_id}")
    @Test
    public void getBorrowedBookByUserID(){

        given()
                .log().all()
                .header("x-library-token",LibrarianToken)
                .pathParam("user_id",2090).
        when()
                .get("/get_borrowed_books_by_user/{user_id}").
        then()
                .log().body()
                .statusCode(200);

        List<BorrowedBooksBYUser> listBooksByUserID =
                given()
                    .header("x-library-token",LibrarianToken)
                    .pathParam("user_id",2090).
                when()
                    .get("/get_borrowed_books_by_user/{user_id}").
                then()
                    .log().ifValidationFails()
                    .extract()
                    .jsonPath().getList("",BorrowedBooksBYUser.class)
                ;

        listBooksByUserID.forEach(p -> System.out.println(p));

    }


}
