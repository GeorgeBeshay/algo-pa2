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
                        "D:\\algo-pa2\\pa2-src\\src\\main\\java\\algs\\part02\\tests\\20010435.2.simpleFile.exe.hc",
                        "D:\\algo-pa2\\pa2-src\\src\\main\\java\\algs\\part02\\tests\\extracted.20010435.2.simpleFile.exe"
                ),
                Arguments.of(
                        "path\\to\\your\\20010435.1.filename.txt.hc",
                        "path\\to\\your\\extracted.20010435.1.filename.txt"
                )
        );
    }
}
