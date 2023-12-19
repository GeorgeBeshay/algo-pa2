package algs.part02;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BytesManipulatorTests {

    @ParameterizedTest
    @MethodSource("generateBytesAndEquivalentStrings")
    public void bytesToHexadecimalStringTest(byte[] bytes, String expectedBytesToHexaString) {
        // Arrange - none

        // Act
        String actualBytesToHexaString = BytesManipulator.bytesToHexadecimalString(bytes, 0, bytes.length);

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
