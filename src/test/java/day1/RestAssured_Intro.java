package day1;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class RestAssured_Intro {

    @DisplayName("Testing hello endpoint")
    @Test
    public void testHelloEndPoint(){

        // http://75.101.202.237:8000/api/hello
        Response response =  get("http://75.101.202.237:8000/api/hello");

        //extracting information from Response object
        System.out.println("response.getStatusCode() = "
                + response.getStatusCode());

        System.out.println("response.statusCode() = "
                + response.statusCode());

        //getting specific header as a String
        System.out.println("response.getHeader(\"Content-Type\") = "
                + response.getHeader("Content-Type"));


        //getting content type header using ready method
        System.out.println("response.contentType() = "
                + response.contentType());
        System.out.println("response.getContentType() = "
                + response.getContentType());

        //getting the time spent for execution
        System.out.println("response.getTime() = "
                + response.getTime());
        System.out.println("response.time() = "
                + response.time());

        //getting body as a String
        System.out.println("response.asString() = "
                + response.asString());

        assertThat(response.statusCode(),is(200) );
        assertThat(response.contentType(),startsWith("text/plain"));
        assertThat(response.asString(), is("Hello from Sparta"));

        // printing the result
        // prettyPrint() -> return String
        // prettyPeek() -> return same response object

        response.prettyPrint();
        response.prettyPeek();


    }

    @DisplayName("Testing GET /api/spartans/{id} Endpoint")
    @Test
    public void testSingleSpartan(){
        Response response = get("http://75.101.202.237:8000/api/spartans/3");
        response.prettyPeek();
        assertThat(response.asString(),containsString("ABC"));
        assertThat(response.getStatusCode(), is(equalTo(200)));
        assertThat(response.contentType(), is("application/json"));
        assertThat(response.header("Connection"), equalTo("keep-alive"));

        System.out.println(response.asString());

        //getting the field value of Json Body
        // path method or (jsonPath method)
        System.out.println("response.path(\"id\") = " + response.path("id"));
        System.out.println("response.path(\"name\") = " + response.path("name"));
        System.out.println("response.path(\"gender\") = " + response.path("gender"));
        System.out.println("response.path(\"phone\") = " + response.path("phone"));

        //save id and variable into specific data type
        int myId = response.path("id") ;
        String myName = response.path("name");
        String myGender = response.path("gender");
        long myPhone = response.path("phone");
        System.out.println("myId = " + myId);
        System.out.println("myName = " + myName);
        System.out.println("myGender = " + myGender);
        System.out.println("myPhone = " + myPhone);
    }



}
