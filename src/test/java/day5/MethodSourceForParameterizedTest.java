package day5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MethodSourceForParameterizedTest {

    @ParameterizedTest
    @MethodSource("getManyNames")
    public void testPrintNames(String name){

        System.out.println("name = " + name);
        assertTrue(name.length()>=4);

    }
    //create a static method to return many names
    public static Stream<String> getManyNames(){

        List<String> nameList = Arrays.asList("Emre","Mustafa","Diana","Tucky","Don Corleone","Nina","Erjon","Muhammed","Saya");
        return nameList.stream();

    }

}
