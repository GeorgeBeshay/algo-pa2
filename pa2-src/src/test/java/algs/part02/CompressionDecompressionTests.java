package algs.part02;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class CompressionDecompressionTests {

    @ParameterizedTest
    @MethodSource("generateAbsoluteFilePath")
    public void compressAndDecompressSimpleFiles(String originalFilePath, int n) {
        // Arrange
        String metadataFilePath = Utilities.generateMetadataFilePath(originalFilePath, n);
        String compressedFilePath = Utilities.generateCompressionFilePath(originalFilePath, n);
        String decompressedFilePath = Utilities.generateOriginalFilePath(compressedFilePath);


        ProFileReader.setBufferSize(n);
        ProFileWriter.setBufferSize(n);
        Compressor compressor = new Compressor();
        Decompressor decompressor = new Decompressor();

        // Act
        compressor.compressFile(originalFilePath, n);
        decompressor.decompress(compressedFilePath, metadataFilePath);

        // Assert
        try {
            assertTrue(FileUtils.contentEquals(new File(originalFilePath), new File(decompressedFilePath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    private static boolean areFilesEqual(File file1, File file2) {
//        try (BufferedInputStream bis1 = new BufferedInputStream(new FileInputStream(file1));
//             BufferedInputStream bis2 = new BufferedInputStream(new FileInputStream(file2))) {
//
//            int byte1, byte2;
//            while ((byte1 = bis1.read()) != -1) {
//                byte2 = bis2.read();
//                if (byte1 != byte2) {
//                    return false;
//                }
//            }
//            return bis2.read() == -1; // Ensure file2 has no extra content
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    private static Stream<Arguments> generateAbsoluteFilePath() {
        return Stream.of(
                Arguments.of(
                        "D:\\College\\Level 3\\Fall 2023 - 2024\\CSE 321 - Analysis and Design of Algorithms\\Programming Assignments\\Assignment 02\\algo-pa2\\pa2-src\\src\\test\\resources\\sample files\\Algorithms - Lectures 7 and 8 (Greedy algorithms).pdf",
                        1
                ),
//                Arguments.of(
//                        "D:\\College\\Level 3\\Fall 2023 - 2024\\CSE 321 - Analysis and Design of Algorithms\\Programming Assignments\\Assignment 02\\algo-pa2\\pa2-src\\src\\test\\resources\\sample files\\recorded_video.mp4",
//                        1
//                ),
                Arguments.of(
                        "D:\\College\\Level 3\\Fall 2023 - 2024\\CSE 321 - Analysis and Design of Algorithms\\Programming Assignments\\Assignment 02\\algo-pa2\\pa2-src\\src\\test\\resources\\sample files\\simpletext.txt",
                        1
                ),
                Arguments.of(
                        "D:\\College\\Level 3\\Fall 2023 - 2024\\CSE 321 - Analysis and Design of Algorithms\\Programming Assignments\\Assignment 02\\algo-pa2\\pa2-src\\src\\test\\resources\\sample files\\test0.txt",
                        1   // TODO will fail if n > 3
                ),
                Arguments.of(
                        "D:\\College\\Level 3\\Fall 2023 - 2024\\CSE 321 - Analysis and Design of Algorithms\\Programming Assignments\\Assignment 02\\algo-pa2\\pa2-src\\src\\test\\resources\\sample files\\test256bytes.txt",
                        1
                )
        );
    }
}
