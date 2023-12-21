package algs.part02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

public class MetadataTests {
    @Test
    public void testGetNextEntry() {
        // Arrange
        String fp = "D:\\College\\Level 3\\Fall 2023 - 2024\\CSE 321 - Analysis and Design of Algorithms\\Programming Assignments\\Assignment 02\\algo-pa2\\pa2-src\\src\\test\\resources\\sample files\\getNextEntry_test0.txt";
        ProFileReader proFileReader = new ProFileReader(fp);
        String[] expectedEntry = new String[]{"11110001111111001110001010111", "EE14"};
        Metadata metadata = new Metadata();

        // Act
        String[] actualEntry = metadata.getNextEntry(proFileReader, 2);

        // Assert
        assertArrayEquals(expectedEntry, actualEntry);
    }
}
