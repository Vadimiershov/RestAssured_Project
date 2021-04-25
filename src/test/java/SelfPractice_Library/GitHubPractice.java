package SelfPractice_Library;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import static io.restassured.RestAssured.get;

public class GitHubPractice {

    @DisplayName("Get/GithubRepo")
    @Test
    public void getRepoID(){

        get("https://api.github.com/repos");

        ResponseSpecification response = given()
                .log().all()
                .queryParam("owner","/Vadimiershov")
                .queryParam("repo","/Group_project_LIbrary")
                .when()
                .log().all().
                then()
                .log().all()
                .statusCode(200)
                ;
    }

    @DisplayName("Get/user")
    @Test
    public void getUser(){

        get("https://api.github.com/Vadimiershov").prettyPeek();




    }


}
