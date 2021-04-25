package day7;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import pojo.Character;

import java.util.List;

public class Breaking_Bad_POJO_Test {

    @BeforeAll
    public static void init(){
        baseURI = "https://www.breakingbadapi.com";
        basePath = "/api";
    }

    @AfterAll
    public static void cleanup(){
        reset();
    }

    @DisplayName("GET /charaacters")
    @Test
    public void testPracticeDeserialization(){

        //send request to https://www.breakingbadapi.com/api/characters
        //save first item in to character pojo
        Character c1 = get("/characters")
                        .jsonPath().getObject("[0]",Character.class);
        System.out.println("c1 = " + c1);


        // save all items into list of characters

        List<Character> allCharacters = get("/characters").jsonPath()
                .getList("",Character.class);

        allCharacters.forEach(p -> System.out.println(p));

        //print all character name who appeared exactly 5 times(all 5 season)
        for (Character each : allCharacters) {
            if (each.getAppearance().size() == 5) {
                System.out.println(each.getName());
            }
        }
            for (Character each : allCharacters){
            if(each.getAppearance().size()==1 && each.getAppearance().get(0)==3){
                System.out.println("each = " + each);
            }
        }




    }



}
