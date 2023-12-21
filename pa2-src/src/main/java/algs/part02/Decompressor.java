package algs.part02;

import java.util.HashMap;
import java.util.Map;

/**
 * The Decompressor class is the entity responsible for implementing the overall decompressing algorithm. It uses a set
 * of utilities methods.
 */
public class Decompressor {

    public static void main(String[] args) {
        Decompressor decompressor = new Decompressor();
        String filePath = "D:\\College\\Level 3\\Fall 2023 - 2024\\CSE 321 - Analysis and Design of Algorithms\\Programming Assignments\\Assignment 02\\algo-pa2\\pa2-src\\src\\main\\java\\algs\\part02\\tests\\20010435_1.Algorithms - Lectures 7 and 8 (Greedy algorithms).pdf.hc";
        String metadataFilePath = "D:\\College\\Level 3\\Fall 2023 - 2024\\CSE 321 - Analysis and Design of Algorithms\\Programming Assignments\\Assignment 02\\algo-pa2\\pa2-src\\src\\main\\java\\algs\\part02\\tests\\Algorithms - Lectures 7 and 8 (Greedy algorithms)_metadata_1.txt";
        decompressor.decompress(filePath, metadataFilePath);
    }

    /*
     * Algorithm Pseudocode
     * --------------------
     *
     * 1. Input file name to decompress
     * 2. Input metadata file to read
     * 3. Generate representation map from the metadata file
     * 4. scan file bytes and map them to the real bytes.
     * 5. write the new bytes generated
     */

    /**
     * <b>Decompresses a file using Huffman's algorithm.</b>
     * <hr>
     * Implements the decompression overall process, this happens by first reading the metadata file and extracting
     * the representation map, and finally scanning the compressed file part by part and converting each huffman code
     * with its actual represented bytes.
     * <hr>
     * @param compressedFilePath The file path of the compressed file.
     * @param metadataFilePath The file path of the metadata file.
     */
    public void decompress(String compressedFilePath, String metadataFilePath) {
        Logger.logMsgFrom(this.getClass().getName(), "File decompression process has been started ..", -1);

        // configurations
//        ProFileWriter.setBufferSize(n);
//        ProFileReader.setBufferSize(n);

        // extract representation map from the metadata file.
        HashMap<String, String> representationMap = new HashMap<>();
        int paddingBits = Metadata.readAndExtractMetadata(metadataFilePath, representationMap);
        Logger.logMsgFrom(this.getClass().getName(), "Metadata has been scanned successfully, " +
                "and the representation map has been computed.", 0);

        // decompress the file
        readBytesAndExportDecompression(compressedFilePath, representationMap, paddingBits);
        Logger.logMsgFrom(this.getClass().getName(), "File has been decompressed successfully.", 0);

        Logger.logMsgFrom(this.getClass().getName(), "File decompression process is successfully completed ..", 0);
    }

    /**
     * Reads bytes from the compressed file, performs decompression, and writes the result to a new file.
     * @param compressedFilePath The file path of the compressed file.
     * @param representationMap The mapping of compressed codes to original bytes.
     * @param paddingBits The number of padding bits used during compression.
     */
    public void readBytesAndExportDecompression(String compressedFilePath, Map<String, String> representationMap, int paddingBits) {
        // initialize data structures and objects
        ProFileReader proFileReader = new ProFileReader(compressedFilePath);
        byte[] readBytes;
        ProFileWriter proFileWriter = new ProFileWriter(Utilities.generateOriginalFilePath(compressedFilePath));
        byte[] bytesToWrite;

        StringBuilder bytesString = new StringBuilder();        // bytes in hex
        StringBuilder nBitsRepresentation = new StringBuilder();
        readBytes = proFileReader.readNextFilePart();

        while (readBytes.length != 0) {
            // handling file part (a group of bytes)
            for (byte readByte : readBytes) {

                String binaryRepresentation = BytesManipulator.convertByteToBinaryString(readByte);
                // handling a single byte
                for (int i = 0 ; i  < binaryRepresentation.length() ; i++){
                    if(paddingBits == 0) {
                        nBitsRepresentation.append(binaryRepresentation.charAt(i));
                    } else {
                        paddingBits--;
                    }
                    if (representationMap.containsKey(nBitsRepresentation.toString())) {
                        bytesString.append(representationMap.get(nBitsRepresentation.toString()));
                        nBitsRepresentation = new StringBuilder();
                    }
                }

            }

            bytesToWrite = BytesManipulator.convertHexStringToBytesArray(bytesString.toString());
            bytesString = new StringBuilder();
            proFileWriter.writeNextFilePart(bytesToWrite);

            readBytes = proFileReader.readNextFilePart();
        }
    }

}
