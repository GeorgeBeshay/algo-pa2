package algs.part02;

import java.io.File;
import java.time.Duration;
import java.time.Instant;

public class Analysis {

    public static void main(String[] args) {

        int[] Ns = new int[] {1, 2, 3, 4, 5};
        String[] filePaths = new String[] {
                "D:\\College\\Level 3\\Fall 2023 - 2024\\CSE 321 - Analysis and Design of Algorithms\\Programming Assignments\\Assignment 02\\algo-pa2\\pa2-src\\src\\main\\java\\algs\\part02\\tests\\gbbct10.seq",
                "D:\\College\\Level 3\\Fall 2023 - 2024\\CSE 321 - Analysis and Design of Algorithms\\Programming Assignments\\Assignment 02\\algo-pa2\\pa2-src\\src\\main\\java\\algs\\part02\\tests\\Algorithms - Lectures 7 and 8 (Greedy algorithms).pdf"
        };

        for (String fp : filePaths) {
            for (int n : Ns) {

                double originalFileSize = getFileSizeInMegaBytes(fp);
                Instant start = Instant.now();

                Compressor compressor = new Compressor();
                compressor.compressFile(fp, n);

                Instant end = Instant.now();
                double compressedFileSize = getFileSizeInMegaBytes(Utilities.generateCompressionFilePath(fp, n));

                System.out.println("Time taken for compression: " + formatDuration(Duration.between(start, end)));
                System.out.println("Compression Ratio: " + compressedFileSize / originalFileSize);

            }
        }

    }
    public static double getFileSizeInMegaBytes(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            long fileSizeInBytes = file.length();
            return (double) fileSizeInBytes / (1024 * 1024); // Convert bytes to megabytes
        } else {
            return -1; // Return -1 if the file doesn't exist or is not a regular file
        }
    }

    public static String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

}
