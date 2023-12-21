package algs.part02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

public class BytesManipulatorTests {

    @ParameterizedTest
    @MethodSource("generateBytesAndEquivalentStrings")
    public void bytesToHexadecimalStringTest(byte[] bytes, String expectedBytesToHexaString) {
        // Arrange - none

        // Act
        String actualBytesToHexaString = BytesManipulator.convertBytesToHexString(bytes, 0, bytes.length);

        // Assert
        assertEquals(expectedBytesToHexaString, actualBytesToHexaString);
    }

    @ParameterizedTest
    @MethodSource("generateBinaryStringAndByteArray")
    public void convertBitsStringToBytesArrayTest(String binaryString, byte[] expectedBytesArray) {
        // Arrange - none

        // Act
        byte[] actualBytesArray = BytesManipulator.convertBinStringToBytesArray(binaryString);

        // Assert
        assertArrayEquals(expectedBytesArray, actualBytesArray);
    }

    @ParameterizedTest
    @MethodSource("generateByteAndEquivalentBinaryString")
    public void convertByteToBinaryStringTest(byte b, String expectedBinaryString) {
        // Arrange - none

        // Act
        String actualBinaryString = BytesManipulator.convertByteToBinaryString(b);

        // Assert
        assertEquals(expectedBinaryString, actualBinaryString);
    }

    @Test
    public void testConvertBytesToInt_Success01() {
        byte[] intBytes = {0x00, 0x00, 0x00, 0x0A}; // Example byte array for int (0x0000000A)
        int expectedIntValue = 10;

        int actualIntValue = BytesManipulator.convertBytesToInt(intBytes);
        assertEquals(expectedIntValue, actualIntValue);
    }
    @Test
    public void testConvertBytesToInt_Success02() {
        byte[] intBytes = {(byte) 0x97, (byte) 0xFF, (byte) 0xFF, 0x0A};
        int expectedIntValue = -1744830710;

        int actualIntValue = BytesManipulator.convertBytesToInt(intBytes);
        assertEquals(expectedIntValue, actualIntValue);
    }

    @Test
    public void testConvertBytesToInt_Success03() {
        byte[] intBytes = {(byte) 0x13, (byte) 0x5F, (byte) 0xE3, (byte) 0xFF};
        int expectedIntValue = 325051391;

        int actualIntValue = BytesManipulator.convertBytesToInt(intBytes);
        assertEquals(expectedIntValue, actualIntValue);
    }

    @Test
    public void testConvertBytesToInt_InvalidSize() {
        byte[] invalidIntBytes = {0x00, 0x00, 0x0A}; // Invalid size byte array for int
        assertThrows(RuntimeException.class, () -> BytesManipulator.convertBytesToInt(invalidIntBytes));
    }

    @Test
    public void testConvertBytesToLong_Success01() {
        byte[] longBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0A}; // Example byte array for long (0x000000000000000A)
        long expectedLongValue = 10L;

        long actualLongValue = BytesManipulator.convertBytesToLong(longBytes);
        assertEquals(expectedLongValue, actualLongValue);
    }

    @Test
    public void testConvertBytesToLong_Success02() {
        byte[] longBytes = {(byte) 0xE0, (byte) 0x19, (byte) 0x73, (byte) 0xCC, (byte) 0xD2, (byte) 0x1B, (byte) 0x00, (byte) 0x99}; // Example byte array for long (0x000000000000000A)
        long expectedLongValue = -2298678811260419943L;

        long actualLongValue = BytesManipulator.convertBytesToLong(longBytes);
        assertEquals(expectedLongValue, actualLongValue);
    }

    @Test
    public void testConvertBytesToLong_Success03() {
        byte[] longBytes = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}; // Example byte array for long (0x000000000000000A)
        long expectedLongValue = -1;

        long actualLongValue = BytesManipulator.convertBytesToLong(longBytes);
        assertEquals(expectedLongValue, actualLongValue);
    }

    @Test
    public void testConvertBytesToLong_InvalidSize() {
        byte[] invalidLongBytes = {0x00, 0x00, 0x00, 0x0A}; // Invalid size byte array for long
        assertThrows(RuntimeException.class, () -> BytesManipulator.convertBytesToLong(invalidLongBytes));
    }

    @Test
    public void testConvertBytesToBinaryString_NoPadding() {
        byte[] bytes = {(byte) 0x97, (byte) 0xFF}; // Example byte array (0x97, 0xFF)
        byte paddingBits = 0;
        String expectedBits = "1001011111111111"; // Expected bits without padding

        String resultBits = BytesManipulator.convertBytesToBinaryString(bytes, paddingBits);
        assertEquals(expectedBits, resultBits);
    }

    @Test
    public void testConvertBytesToBinaryString_WithPadding() {
        byte[] bytes = {(byte) 0x97, (byte) 0xFF}; // Example byte array (0x97, 0xFF)
        byte paddingBits = 4; // Adding 4 bits of padding
        String expectedBits = "011111111111"; // Expected bits with padding

        String resultBits = BytesManipulator.convertBytesToBinaryString(bytes, paddingBits);
        assertEquals(expectedBits, resultBits);
    }

    @Test
    public void testConvertBytesToBinaryString_FullPadding() {
        byte[] bytes = {(byte) 0x97, (byte) 0xFF}; // Example byte array (0x97, 0xFF)
        byte paddingBits = 16; // Adding 16 bits of padding, more than available bits
        String expectedBits = ""; // Expected an empty string due to full padding

        String resultBits = BytesManipulator.convertBytesToBinaryString(bytes, paddingBits);
        assertEquals(expectedBits, resultBits);
    }


    private static Stream<Arguments> generateBytesAndEquivalentStrings() {
        return Stream.of(
                Arguments.of(       // simple non overlapping activities
                        new byte[] {
                                (byte) 0xFF,
                                (byte) 0xEF,
                                (byte) 0x11,
                                (byte) 0x00,
                                (byte) 0x90,
                        },
                        "FFEF110090"
                ),
                Arguments.of(
                        new byte[] {
                        },
                        ""
                ),
                Arguments.of(
                        new byte[] {
                                (byte) 0x00,
                                (byte) 0x11,
                                (byte) 0x00,
                                (byte) 0xAA,
                                (byte) 0xBB,
                                (byte) 0xCC,
                        },
                        "001100AABBCC"
                ),
                Arguments.of(
                        new byte[] {
                                (byte) 0x11,
                                (byte) 0x22,
                                (byte) 0xFF,
                                (byte) 0xEE,
                                (byte) 0xDD,
                                (byte) 0xCC,
                                (byte) 0x00,
                                (byte) 0x10,
                        },
                        "1122FFEEDDCC0010"
                )
        );
    }

    private static Stream<Arguments> generateBinaryStringAndByteArray() {
        return Stream.of(
                Arguments.of(
                        "",
                        new byte[] {}
                ),
                Arguments.of(
                        "00000000",
                        new byte[]{
                                (byte) 0x00
                        }
                ),
                Arguments.of(
                        "01111111",
                        new byte[]{
                                (byte) 127
                        }
                ),
                Arguments.of(
                        "0000000111111111",
                        new byte[]{
                                (byte) 0x01,
                                (byte) 0xFF
                        }
                ),
                Arguments.of(
                        "0000101011101111",
                        new byte[]{
                                (byte) 10,
                                (byte) 239
                        }
                ),
                Arguments.of(
                        "1000001011111111",
                        new byte[] {
                                (byte) 130,
                                (byte) 0xFF
                        }
                ),
                Arguments.of(
                        "11100000000000001010101111011101",
                        new byte[]{
                                (byte) 224,
                                (byte) 0,
                                (byte) 171,
                                (byte) 221
                        }
                )
        );
    }

    private static Stream<Arguments> generateByteAndEquivalentBinaryString() {
        return Stream.of(
                Arguments.of(
                        (byte) 0x0F,
                        "00001111"
                )
        );
    }

}
