package Journey_of_Taro_V3.Journey_of_Taro_V3.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestGlobalCounterAddOne {


    @Test
    void testGlobalCounterAddOne() {

        // Arrange
        GlobalCounter counter = new GlobalCounter();

        // Act
        counter.add();


        // Assert
        int expected = 1;
        int actual = counter.getCount();
        assertEquals(expected, actual);
    }


}