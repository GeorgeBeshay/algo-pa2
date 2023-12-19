package algs.part02;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DecompressorTests {

    @ParameterizedTest
    @MethodSource("generateCompressedPathsAndOriginalPaths")
    public void generateOriginalFilePath01() {
        // Arrange
        String compressedFilePath = "D:\\College\\Level 3\\Fall 2023 - 2024\\CSE 321 - Analysis and Design of Algorithms\\Programming Assignments\\Assignment 02\\algo-pa2\\pa2-src\\src\\main\\java\\algs\\part02\\tests\\20010435_1.simpletext.txt.hc";
        String expectedOriginalFilePath = "D:\\College\\Level 3\\Fall 2023 - 2024\\CSE 321 - Analysis and Design of Algorithms\\Programming Assignments\\Assignment 02\\algo-pa2\\pa2-src\\src\\main\\java\\algs\\part02\\tests\\extracted.simpletext.txt";
        Decompressor decompressor = new Decompressor();

        // Act
        String actualOriginalFilePath = decompressor.generateOriginalFilePath(compressedFilePath);

        // Assert
        assertEquals(expectedOriginalFilePath, actualOriginalFilePath);
    }

    // "path/to/your/123454_23.filename.txt.hc"

    private static Stream<Arguments> generateCompressedPathsAndOriginalPaths() {
        return Stream.of(
                Arguments.of(
                        "D:\\algo-pa2\\pa2-src\\src\\main\\java\\algs\\part02\\tests\\123123_122.simpletext.exe.hc",
                        "D:\\algo-pa2\\pa2-src\\src\\main\\java\\algs\\part02\\tests\\extracted.simpletext.exe"
                ),
                Arguments.of(
                        "path/to/your/123454_23.filename.txt.hc",
                        "path/to/your/filename.txt"
                )
        );
    }
}
