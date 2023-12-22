package algs.part02;

import java.util.Objects;

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

            Logger.logMsgFrom(Main.class.getName(), "Compression process is requested!", -1);
            Compressor compressor = new Compressor();
            compressor.compressFile(args[1], Integer.parseInt(args[2]));

        } else if (args.length == 2 && Objects.equals(args[0], "d")) {

            Logger.logMsgFrom(Main.class.getName(), "Decompression process is requested!", -1);
            Decompressor decompressor = new Decompressor();
            decompressor.decompress(args[1]);

        } else {
            Logger.logMsgFrom(Main.class.getName(), "Invalid # of parameters.", 1);

        }
    }

}
