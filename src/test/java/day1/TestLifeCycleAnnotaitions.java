package day1;

import org.junit.jupiter.api.*;

@DisplayName("Learning Test Lifecycle Annotations")
public class TestLifeCycleAnnotaitions {

    @BeforeAll
    public static void init(){
        System.out.println("Before All is running");
    }

    @AfterAll
    public static void quit(){
        System.out.println("After All is finished");
    }

    @BeforeEach
    public void initEach(){
        System.out.println("Before Each is running");
    }

    @AfterEach
    public void tearDownEach(){
        System.out.println("After each is running");
    }

    @Test
    public void test1(){
        System.out.println("Test 1 is running");
    }

    @Test
    public void test2(){
        System.out.println("Test 2 is running");
    }

}
