package algs.part02;

import java.util.Objects;

/**
 * This class demonstrates LARGE file input/output operations by reading from a file using ProFileReader
 * and writing to another file using ProFileWriter.
 */
public class Main {

    /**
     * The main method demonstrating file input/output operations.
     *
     * @param args Command-line arguments: args[0] should contain the path of the input file.
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
