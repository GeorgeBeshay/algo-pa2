package algs.part02;

import java.time.Duration;
import java.util.Objects;
import java.time.Instant;

/*
    I acknowledge that I am aware of the academic integrity guidelines of this course,
    and that I worked on this assignment independently without any unauthorised help.
 */

/**
 * This class is the entry point for the second part of the assignment, giving the user the option
 * to compress / decompress a file of any type.
 * It accepts input through the command-line arguments.
 * For compression: java -jar 20010435_huffman.jar c absFilePath n
 * For decompression: java -jar 20010435_huffman.jar d absFilePath
 */
public class Main {

    /**
     * The main method demonstrating file compression / decompression procedures.
     *
     * @param args Command-line arguments: args[0] should contain the type of operation,
     *             args[1] should contain the absolute file path, and args[2] (in case of compression)
     *             should contain the n value.
     */
    public static void main(String[] args) {
        if (args.length == 3 && Objects.equals(args[0], "c")) {

            double originalFileSize = Analysis.getFileSizeInMegaBytes(args[1]);
            Instant start = Instant.now();

            Logger.logMsgFrom(Main.class.getName(), "Compression process is requested!", -1);
            Compressor compressor = new Compressor();
            compressor.compressFile(args[1], Integer.parseInt(args[2]));

            Instant end = Instant.now();
            double compressedFileSize = Analysis.getFileSizeInMegaBytes(Utilities.generateCompressionFilePath(args[1], Integer.parseInt(args[2])));

            Logger.logMsgFrom(Main.class.getName(), "Compression time: " + Analysis.formatDuration(Duration.between(start, end)), -1);
            Logger.logMsgFrom(Main.class.getName(), "Compression ratio: " + (compressedFileSize / originalFileSize), -1);

        } else if (args.length == 2 && Objects.equals(args[0], "d")) {

            Instant start = Instant.now();

            Logger.logMsgFrom(Main.class.getName(), "Decompression process is requested!", -1);
            Decompressor decompressor = new Decompressor();
            decompressor.decompress(args[1]);

            Instant end = Instant.now();
            Logger.logMsgFrom(Main.class.getName(), "Decompression time: " + Analysis.formatDuration(Duration.between(start, end)), -1);

        } else {
            Logger.logMsgFrom(Main.class.getName(), "Invalid # of parameters.", 1);

        }
    }

}
