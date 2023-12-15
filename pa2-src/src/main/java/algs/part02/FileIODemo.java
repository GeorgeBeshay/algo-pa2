package algs.part02;

/**
 * This class demonstrates LARGE file input/output operations by reading from a file using ProFileReader
 * and writing to another file using ProFileWriter.
 */
public class FileIODemo {

    /**
     * The main method demonstrating file input/output operations.
     *
     * @param args Command-line arguments: args[0] should contain the path of the input file.
     */
    public static void main(String[] args) {

        ProFileReader proFileReader = new ProFileReader(args[0]);
        ProFileWriter proFileWriter = new ProFileWriter(args[0].replace(".", "_copy."));
        byte[] readBytes;

        readBytes = proFileReader.readNextFilePart();

        while(readBytes.length != 0) {
            proFileWriter.writeNextFilePart(readBytes);
            readBytes = proFileReader.readNextFilePart();
        }

        Logger.logMsgFrom(FileIODemo.class.getName(), "Total Read Bytes = " + proFileReader.getTotalBytesRead(), 0);
        Logger.logMsgFrom(FileIODemo.class.getName(), "Total Written Bytes = " + proFileWriter.getTotalBytesWritten(), 0);

    }

}
