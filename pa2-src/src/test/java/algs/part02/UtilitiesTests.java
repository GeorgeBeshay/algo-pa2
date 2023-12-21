package algs.part02;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilitiesTests {

    @ParameterizedTest
    @MethodSource("generateCompressedPathsAndOriginalPaths")
    public void generateOriginalFilePath01(String compressedFilePath, String expectedOriginalFilePath) {
        // Arrange - none

        // Act
        String actualOriginalFilePath = Utilities.generateOriginalFilePath(compressedFilePath);

        // Assert
        assertEquals(expectedOriginalFilePath, actualOriginalFilePath);
    }

    private static Stream<Arguments> generateCompressedPathsAndOriginalPaths() {
        return Stream.of(
                Arguments.of(
                        "D:\\algo-pa2\\pa2-src\\src\\main\\java\\algs\\part02\\tests\\123123_122.simpletext.exe.hc",
                        "D:\\algo-pa2\\pa2-src\\src\\main\\java\\algs\\part02\\tests\\extracted.simpletext.exe"
                ),
                Arguments.of(
                        "path\\to\\your\\123454_23.filename.txt.hc",
                        "path\\to\\your\\extracted.filename.txt"
                )
        );
    }
}
