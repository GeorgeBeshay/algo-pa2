package algs.part02;

import java.util.HashMap;
import java.util.LinkedList;

import static algs.part02.BytesManipulator.updateFrequencyMap;

/**
 * The Compressor class is the entity responsible for implementing the overall compressing algorithm. It uses a set
 * of utilities methods in addition to the Huffman encoding algorithm.
 */
public class Compressor {

    public static void main(String[] args) {
        Compressor compressor = new Compressor();
        String filePath = "D:\\College\\Level 3\\Fall 2023 - 2024\\CSE 321 - Analysis and Design of Algorithms\\Programming Assignments\\Assignment 02\\algo-pa2\\pa2-src\\src\\main\\java\\algs\\part02\\tests\\Algorithms - Lectures 7 and 8 (Greedy algorithms).pdf";
        compressor.compressFile(filePath, 1);
    }

    /**
     * <b>Compresses the specified file using Huffman encoding.</b>
     * <hr>
     * Algorithm Pseudocode: <br>
     * 1. Input file name to compress <br>
     * 2. Read file bytes and generate frequency map <br>
     * 3. Apply Huffman's algorithm and generate representation map <br>
     * 4. Generate the metadata required to be inserted in the compression file <br>
     * 5. Write the metadata to the compressed file. <br>
     * 6. Scan file bytes again and replace each n bytes by their representation code <br>
     * 7. Write the new bytes generated to the compressed file. <br>
     * <hr>
     * @param filePathToCompress The path of the file to be compressed
     * @param n The number of bytes to be compressed at once
     */
    public void compressFile(String filePathToCompress, int n) {

        Logger.logMsgFrom(this.getClass().getName(), "File compression process has been started ..", -1);

        // configurations
        Huffman.setNumberOfChildren(2);
        ProFileWriter.setBufferSize(n);
        ProFileReader.setBufferSizeMatchN(n);

        // iteratively, read bytes and update frequency map
        HashMap<String, Long> freqMap = readBytesAndUpdateFreqMap(filePathToCompress, n);
        Logger.logMsgFrom(this.getClass().getName(), "Computed the frequency map successfully", 0);

        // apply huffman's algorithm
        HashMap<String, String> representationMap = Huffman.huffman(freqMap);
        Logger.logMsgFrom(this.getClass().getName(), "Computed the representation map successfully", 0);

        // generate metadata of the compressed file
        LinkedList<Byte> metadataBytes = Metadata.computeMetadata(representationMap, freqMap, n);
        String metadata = Metadata.generateCompressionMetaData(freqMap, representationMap);
        Logger.logMsgFrom(this.getClass().getName(), "Computed the metadata successfully", 0);

        // write metadata to the compressed file
        String metadataFilePath = Utilities.generateMetadataFilePath(filePathToCompress, n);
        Metadata.writeMetadata(metadataFilePath, metadata);
        Logger.logMsgFrom(this.getClass().getName(), "Metadata file has been written successfully.", 0);

        // iteratively, read the bytes again from the file and generate their
        // equivalent bytes, and write them.
        readBytesAndExportCompression(filePathToCompress, n, representationMap, Integer.parseInt("" + metadata.charAt(metadata.length() - 2)));
        Logger.logMsgFrom(this.getClass().getName(), "Compressed file has been generated successfully", 0);

        // log end of compression statement.
        Logger.logMsgFrom(this.getClass().getName(), "File compression process is successfully completed ..", 0);
    }

    /**
     * <b>Reads bytes from the file and updates the frequency map.</b>
     * <hr>
     * Method is responsible for scanning the input file, and then updating the frequency map by the new scanned
     * file part. It uses the provided functionalities by the class ProFileReader.
     * <hr>
     * @param filePathToCompress The path of the file to be compressed
     * @param n The number of bytes to be compressed at once
     * @return A HashMap containing the frequency of bytes
     */
    public HashMap<String, Long> readBytesAndUpdateFreqMap(String filePathToCompress, int n) {

        // initialize data structures and objects
        ProFileReader proFileReader = new ProFileReader(filePathToCompress);
        byte[] readBytes;
        HashMap<String, Long> freqMap = null;

        // read next file part
        readBytes = proFileReader.readNextFilePart();

        // keep updating the frequency map
        while (readBytes.length != 0) {
            // update freq map
            freqMap = updateFrequencyMap(readBytes, n, freqMap);
            readBytes = proFileReader.readNextFilePart();
        }

        Logger.logMsgFrom(this.getClass().getName(), "Total # of bytes read = " + proFileReader.getTotalBytesRead(), 0);

        return freqMap;
    }

    /**
     * <b>Reads file bytes, applies compression, and writes them to a new file.</b>
     * <hr>
     * Method is responsible for the total scan-and-compress process, that is, reading the file bytes part by part,
     * and then compressing them read file part and finally writing the compressed data bytes, then repeating again
     * the first step.
     * <hr>
     * @param filePathToCompress The path of the file to be compressed
     * @param n The number of bytes to be compressed at once
     * @param representationMap The map containing byte representations after Huffman encoding
     * @param paddingBits The number of padding bits required for the last byte
     */
    public void readBytesAndExportCompression(String filePathToCompress, int n, HashMap<String, String> representationMap, int paddingBits) {

        // initialize data structures and objects
        ProFileReader proFileReader = new ProFileReader(filePathToCompress);
        byte[] readBytes;

        ProFileWriter proFileWriter = new ProFileWriter(Utilities.generateCompressionFilePath(filePathToCompress, n));
        byte[] bytesToWrite;

        // write metadata
//        LinkedList<Byte> metadataBytes = new LinkedList<>();
//        bytesToWrite = new byte[metadataBytes.size()];
//
//        for (int i = 0 ; i < bytesToWrite.length ; i++) {
//            bytesToWrite[i] = metadataBytes.poll();
//        }
//
//        proFileWriter.writeNextFilePart(bytesToWrite);
//        bytesToWrite = null;
        // finished writing metadata.

        StringBuilder binaryStringBuilder = new StringBuilder("0".repeat(paddingBits));
//        String tempNBytes;  // in hexadecimal

        readBytes = proFileReader.readNextFilePart();

        while (readBytes.length != 0) {

//            for (int i = 0 ; i < readBytes.length ; i+=n) {
//
//                tempNBytes = BytesManipulator.bytesToHexadecimalString(readBytes, i, Math.min(i + n, readBytes.length));
//                if (!representationMap.containsKey(tempNBytes)) {
//                    Logger.logMsgFrom(this.getClass().getName(), "Error when compressing the file .. an N bytes word had no mapping ..", 1);
//                    throw new RuntimeException("Error when compressing the file .. an N bytes word had no mapping ..");
//                }
//
//                binaryStringBuilder.append(representationMap.get(tempNBytes));
//            }
            handleFilePartCompression(n, readBytes, representationMap, binaryStringBuilder);

            String binaryString = binaryStringBuilder.toString();
            binaryStringBuilder = new StringBuilder(binaryString.substring(binaryString.length() - (binaryString.length() % 8)));
            binaryString = binaryString.substring(0, binaryString.length() - (binaryString.length() % 8));

            bytesToWrite = BytesManipulator.convertBinStringToBytesArray(binaryString);
            proFileWriter.writeNextFilePart(bytesToWrite);

            readBytes = proFileReader.readNextFilePart();
        }
    }

    /**
     * Handles the compression process for a part of the file.
     * <hr>
     * Method compress the given file part read (readBytes), this happens through scanning n bytes and converting
     * them to the hexadecimal representation, and finally appending their huffman code to the binaryStringBuilder.
     * In case of not finding a huffman code for a given n bytes, the program will through runtime exception.
     * <hr>
     * @param n The number of bytes to be compressed at once
     * @param readBytes The bytes read from the file
     * @param representationMap The map containing byte representations after Huffman encoding
     * @param binaryStringBuilder The StringBuilder to construct the binary representation of the file
     */
    public void handleFilePartCompression(int n,
                                          byte[] readBytes,
                                          HashMap<String, String> representationMap,
                                          StringBuilder binaryStringBuilder) {

        String tempNBytes;  // in hexadecimal

        for (int i = 0 ; i < readBytes.length ; i+=n) {

            tempNBytes = BytesManipulator.convertBytesToHexString(readBytes, i, Math.min(i + n, readBytes.length));
            if (!representationMap.containsKey(tempNBytes)) {
                Logger.logMsgFrom(this.getClass().getName(), "Error when compressing the file .. an N bytes word had no mapping ..", 1);
                throw new RuntimeException("Error when compressing the file .. an N bytes word had no mapping ..");
            }

            binaryStringBuilder.append(representationMap.get(tempNBytes));
        }

    }

}
