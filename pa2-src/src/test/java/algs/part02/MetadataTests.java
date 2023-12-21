package algs.part02;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

public class MetadataTests {
    @Test
    public void testGetNextEntry() {
        // Arrange
        String fp = "D:\\College\\Level 3\\Fall 2023 - 2024\\CSE 321 - Analysis and Design of Algorithms\\Programming Assignments\\Assignment 02\\algo-pa2\\pa2-src\\src\\test\\resources\\sample files\\getNextEntry_test0.txt";
        ProFileReader proFileReader = new ProFileReader(fp);
        String[] expectedEntry = new String[]{"11110001111111001110001010111", "EE14"};

        // Act
        String[] actualEntry = Metadata.getNextEntry(proFileReader, 2);

        // Assert
        assertArrayEquals(expectedEntry, actualEntry);
    }

    @Test
    public void testExportingAndImportingMetadata() {
        // Arrange
        HashMap<String, Long> freqMap = new HashMap<>();
        HashMap<String, String> representationMap = new HashMap<>();

        freqMap.put("FFEC", 2L);
        freqMap.put("01A0", 3L);
        freqMap.put("10D1", 6L);
        freqMap.put("FF", 1L);

        representationMap.put("FFEC", "110");
        representationMap.put("01A0", "10");
        representationMap.put("10D1", "0");
        representationMap.put("FF", "1110");

        int expectedN = 2;
        byte expectedPaddingBits = 2;
        long expectedMetadataSizeInBytes = 52;      // 17 + 8 + 8 + 8 + 11

        // Act
        LinkedList<Byte> metadataBytes = (LinkedList<Byte>) (Metadata.computeMetadata(representationMap, freqMap, expectedN))[0];

        // Assert
        assertEquals(expectedMetadataSizeInBytes, metadataBytes.size());

        byte[] temp = new byte[8];      // 8
        for(int i = 0 ; i < 8 ; i++)
            temp[i] = metadataBytes.get(i);
        assertEquals(expectedMetadataSizeInBytes, BytesManipulator.convertBytesToLong(temp));

        temp = new byte[4];             // 4
        for(int i = 0 ; i < 4 ; i++)
            temp[i] = metadataBytes.get(i + 8);
        assertEquals(4, BytesManipulator.convertBytesToInt(temp));

        temp = new byte[4];             // 4
        for(int i = 0 ; i < 4 ; i++)
            temp[i] = metadataBytes.get(i + 12);
        assertEquals(expectedN, BytesManipulator.convertBytesToInt(temp));

        assertEquals(expectedPaddingBits, metadataBytes.get(16));       // 1

        temp = new byte[4];
        for(int i = 0 ; i < 4 ; i++)
            temp[i] = metadataBytes.get(i + 17);                        // 4
        assertEquals(1, BytesManipulator.convertBytesToInt(temp));

        assertEquals((byte) 6, metadataBytes.get(21)); // 1
    }
}
