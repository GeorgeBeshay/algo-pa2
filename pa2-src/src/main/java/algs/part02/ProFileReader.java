package algs.part02;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class is responsible for reading files, especially those HUGE files.
 */
public class ProFileReader {

    public static int BUFFER_SIZE = 52_428_800;       // 50 Mega Bytes
    private FileInputStream fileInputStream;
    private final String filePath;
    private final byte[] buffer;
    private long totalBytesRead;

    public static void setBufferSize(int n) {
        BUFFER_SIZE = (int) Math.ceil((double) 52_428_800 / n);
    }

    /**
     * Constructs a ProFileReader object to read the specified file.
     *
     * @param filePath The path of the file to be read.
     */
    public ProFileReader(String filePath) {
        this.filePath = filePath;
        this.buffer = new byte[BUFFER_SIZE];
        this.totalBytesRead = 0;
        this.prepareInputStream();
    }

    /**
     * Reads the next part of the file.
     *
     * @return An array of bytes representing the part of the file read.
     * Its size would never exceed BUFFER_SIZE bytes.
     * @throws RuntimeException if an IO error occurs.
     */
    public byte[] readNextFilePart() {
        try {
            int bytesFoundCount = fileInputStream.read(buffer);
            if(bytesFoundCount == -1) {
                bytesFoundCount = 0;
                Logger.logMsgFrom(this.getClass().getName(), "All file has been read successfully.", 0);
            }

            byte[] bytesFound = new byte[bytesFoundCount];
            System.arraycopy(buffer, 0, bytesFound, 0, bytesFoundCount);
            totalBytesRead += bytesFoundCount;

            return bytesFound;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Prepares (Initializes) the input stream for reading the file.
     *
     * @throws RuntimeException if the specified file is not found.
     */
    private void prepareInputStream() {
        try {
            this.fileInputStream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Sorry, Couldn't read the given file.");
        }
    }

    /**
     * Retrieves the total number of bytes read from the file.
     *
     * @return Total bytes read.
     */
    public long getTotalBytesRead() {
        return totalBytesRead;
    }
}
