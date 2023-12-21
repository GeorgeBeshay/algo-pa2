package algs.part02;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProFileReaderTests {
    @Test
    public void test01() {
        // Arrange
        String fp = "D:\\College\\Level 3\\Fall 2023 - 2024\\CSE 321 - Analysis and Design of Algorithms\\Programming Assignments\\Assignment 02\\algo-pa2\\pa2-src\\src\\test\\resources\\sample files\\pro_file_reader_0.txt";
        byte[] expectedBytes = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        ProFileReader.setBufferSizeMatchN(ProFileReader.getBufferSize());

        // Act
        ProFileReader proFileReader = new ProFileReader(fp);
        byte[] actualBytes = proFileReader.getNextXBytes(7);

        // Assert
        assertArrayEquals(expectedBytes, actualBytes);

    }

    @Test
    public void test02() {
        // Arrange
        String fp = "D:\\College\\Level 3\\Fall 2023 - 2024\\CSE 321 - Analysis and Design of Algorithms\\Programming Assignments\\Assignment 02\\algo-pa2\\pa2-src\\src\\test\\resources\\sample files\\pro_file_reader_0.txt";
        byte[] expectedBytes = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};

        // Act
        ProFileReader proFileReader = new ProFileReader(fp);
        byte[] actualBytes = proFileReader.getNextXBytes(7);

        // Assert
        assertArrayEquals(expectedBytes, actualBytes);

    }

    @Test
    public void test03() {
        // Arrange
        String fp = "D:\\College\\Level 3\\Fall 2023 - 2024\\CSE 321 - Analysis and Design of Algorithms\\Programming Assignments\\Assignment 02\\algo-pa2\\pa2-src\\src\\test\\resources\\sample files\\pro_file_reader_0.txt";
        ProFileReader.setBufferSize(3);
        byte expectedFirstChar = 'A';
        byte expectedSecondChar = 'B';
        byte[] expectedMidChars = new byte[] {'C', 'D', 'E'};
        byte expectedSixthChar = 'F';
        byte expectedSeventhChar = 'G';

        // Act
        ProFileReader proFileReader = new ProFileReader(fp);
        byte actualFirstChar = proFileReader.getNextByte();
        byte actualSecondChar = proFileReader.getNextByte();
        byte[] actualMidChars = proFileReader.getNextXBytes(3);
        byte actualSixthChar = proFileReader.getNextByte();
        byte actualSeventhChar = proFileReader.getNextByte();

        // Assert
        assertEquals(expectedFirstChar, actualFirstChar);
        assertEquals(expectedSecondChar, actualSecondChar);
        assertArrayEquals(expectedMidChars, actualMidChars);
        assertEquals(expectedSixthChar, actualSixthChar);
        assertEquals(expectedSeventhChar, actualSeventhChar);
    }

}
