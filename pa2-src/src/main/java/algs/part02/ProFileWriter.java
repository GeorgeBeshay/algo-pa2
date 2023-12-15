package algs.part02;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is responsible for writing files, especially those HUGE ones.
 */
public class ProFileWriter {
    public static final int BUFFER_SIZE = 1_048_576;       // 1 Mega Bytes
    private FileOutputStream fileOutputStream;
    private final String filePath;
    private final byte[] buffer;
    private long totalBytesWritten;

    /**
     * Constructs a ProFileWriter object to write to the specified file.
     *
     * @param filePath The path of the file to be written.
     */
    public ProFileWriter(String filePath) {
        this.filePath = filePath;
        this.buffer = new byte[BUFFER_SIZE];
        this.totalBytesWritten = 0;
        this.prepareOutputStream();
    }

    /**
     * Writes the next part of the file, whose size may exceed the BUFFER_SIZE, given its data bytes.
     *
     * @param filePartBytes An array of bytes representing the part of the file to be written.
     * @throws RuntimeException if an IO error occurs.
     */
    public void writeNextFilePart(byte[] filePartBytes) {
        int bytesWrote = 0;

        while(bytesWrote < filePartBytes.length) {
            System.arraycopy(filePartBytes, bytesWrote, buffer, 0,
                    Math.min(filePartBytes.length - bytesWrote, BUFFER_SIZE));
            writeSomeBytes(Math.min(filePartBytes.length - bytesWrote, BUFFER_SIZE));
            bytesWrote += Math.min(filePartBytes.length - bytesWrote, BUFFER_SIZE);
        }

        totalBytesWritten += bytesWrote;
    }

    /**
     * Writes a specific number of bytes, (maximum BUFFER_SIZE) from the buffer to the output stream.
     *
     * @param bytesCount The number of bytes to write.
     * @throws RuntimeException if an IO error occurs.
     */
    private void writeSomeBytes(int bytesCount) {
        try {
            this.fileOutputStream.write(buffer, 0, bytesCount);

        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    /**
     * Prepares (Initializes) the output stream for writing to the file.
     *
     * @throws RuntimeException if the specified file path is not found.
     */
    private void prepareOutputStream() {
        try {
            this.fileOutputStream = new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Sorry, Couldn't write to the given file path.");
        }
    }

    /**
     * Retrieves the total number of bytes written to the file.
     *
     * @return Total bytes written.
     */
    public long getTotalBytesWritten() {
        return totalBytesWritten;
    }
}
