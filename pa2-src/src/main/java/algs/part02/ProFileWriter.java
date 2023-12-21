package algs.part02;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is responsible for writing files, especially those HUGE ones.
 */
public class ProFileWriter {

    public static void main(String[] args) {
        String filePath = "D:\\College\\Level 3\\Fall 2023 - 2024\\CSE 321 - Analysis and Design of Algorithms\\Programming Assignments\\Assignment 02\\algo-pa2\\pa2-src\\src\\test\\resources\\sample files\\getNextEntry_test0.txt";
        ProFileWriter proFileWriter = new ProFileWriter(filePath);
//        byte[] bytes = new byte[256];
//        for(int i = 0  ; i < 256 ; i++)
//            bytes[i] = (byte) i;

        byte[] arr2 = new byte[]{
                0x00, 0x00, 0x00, 0x04, 0x03, (byte) 0xFE, 0x3F, (byte) 0x9C, 0x57, (byte) 0xEE, 0x14
        };

        proFileWriter.writeNextFilePart(arr2);
    }

    public static int BUFFER_SIZE = 52_428_800;       // 50 Mega Bytes
    private FileOutputStream fileOutputStream;
    private final String filePath;
    private final byte[] buffer;
    private long totalBytesWritten;

    public static void setBufferSize(int n) {
        BUFFER_SIZE = (int) Math.ceil((double) 52_428_800 / n);
    }

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
