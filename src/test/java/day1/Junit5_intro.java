package day1;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Day 1 of JUnit5 Test")//setting display name of the test class in test result using @displayName
public class Junit5_intro {

    @DisplayName("Testing numbers")
    @Test
    public void test(){
        System.out.println("Learning JUnit5");

        assertEquals(1,1);
        assertEquals(1,3,"Something is wrong!!!");

    }
    //add one more test,
    //to assert your name first character start with letter A
   @DisplayName("")
    @Test
    public void testName() {
        String name = "Vadym";
        assertEquals('V',name.charAt(0),"First letter is nor A");
    }
    //@BeforeAll @AfterAll @BeforeEach AfterEach

}
