package algs.part02;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BytesManipulatorTests {

    @ParameterizedTest
    @MethodSource("generateBytesAndEquivalentStrings")
    public void bytesToHexadecimalStringTest01(byte[] bytes, String expectedBytesToHexaString) {
        // Arrange - none

        // Act
        String actualBytesToHexaString = BytesManipulator.bytesToHexadecimalString(bytes, 0, bytes.length);

        // Assert
        assertEquals(expectedBytesToHexaString, actualBytesToHexaString);
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

}
