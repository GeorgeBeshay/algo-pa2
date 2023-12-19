package algs.part02;

import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import static algs.part02.BytesManipulator.updateFrequencyMap;

public class Compressor {

    public static void main(String[] args) {
        Compressor compressor = new Compressor();
        String filePath = "D:\\College\\Level 3\\Fall 2023 - 2024\\CSE 321 - Analysis and Design of Algorithms\\Programming Assignments\\Assignment 02\\algo-pa2\\pa2-src\\src\\main\\java\\algs\\part02\\tests\\recorded_video.mp4";
        compressor.compressFile(filePath, 4);
    }

    /*
     * Algorithm Pseudocode
     * --------------------
     *
     * 1. Input file name to compress
     * 2. Read file bytes and generate frequency map
     * 3. Apply huffman's algorithm and generate representation map
     * 4. generate the metadata required to be inserted in the compression file
     * 5. write the metadata to the compressed file.
     * 6. scan file bytes again and replace each n bytes by their representation code
     * 7. write the new bytes generated to the compressed file.
     */

    public void compressFile(String filePathToCompress, int n) {

        Logger.logMsgFrom(this.getClass().getName(), "File compression process has been started ..", -1);
        Huffman.setNumberOfChildren(2);
        ProFileWriter.setBufferSize(n);
        ProFileReader.setBufferSize(n);

        // iteratively, read bytes and update frequency map
        HashMap<String, Long> freqMap = readBytesAndUpdateFreqMap(filePathToCompress, n);
        Logger.logMsgFrom(this.getClass().getName(), "Computed the frequency map successfully", 0);

        // apply huffman's algorithm
        HashMap<String, String> representationMap = Huffman.huffman(freqMap);
        Logger.logMsgFrom(this.getClass().getName(), "Computed the representation map successfully", 0);

        // generate metadata of the compressed file
        String metadata = Metadata.generateCompressionMetaData(freqMap, representationMap);
        Logger.logMsgFrom(this.getClass().getName(), "Computed the metadata successfully", 0);

        // write metadata to the compressed file
        String metadataFilePath = filePathToCompress.replaceFirst("[.][^.]+$", "_metadata.txt");
        Metadata.writeMetadata(metadataFilePath, metadata);
        Logger.logMsgFrom(this.getClass().getName(), "Metadata file has been written successfully.", 0);

        // iteratively, read the bytes again from the file and generate their
        // equivalent bytes, and write them.
        readBytesAndExportCompression(filePathToCompress, n, representationMap, Integer.parseInt("" + metadata.charAt(metadata.length() - 2)));
        Logger.logMsgFrom(this.getClass().getName(), "Compressed file has been generated successfully", 0);

        // log end of compression statement.
        Logger.logMsgFrom(this.getClass().getName(), "File compression process is successfully completed ..", 0);
    }

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

//    public void readBytesAndExportCompressionPro(String filePathToCompress, int n, HashMap<String, String> representationMap) {
//        // initialize data structures and objects
//        ProFileReader proFileReader = new ProFileReader(filePathToCompress);
//        byte[] readBytes;
//
//        ProFileWriter proFileWriter = new ProFileWriter(generateCompressionFilePath(filePathToCompress, n));
//        byte[] bytesToWrite;
//
//        StringBuilder bytesString = new StringBuilder();
//        String nBytesRepresentation;
//
//        readBytes = proFileReader.readNextFilePart();
//        while (readBytes.length != 0) {
//            for(int i = 0 ; i < readBytes.length ; i+=n) {
//                nBytesRepresentation = BytesManipulator.bytesToHexadecimalString(readBytes, i, Math.min(readBytes.length, i + n));
//                if(!representationMap.containsKey(nBytesRepresentation)){
//                    throw new RuntimeException("Error .. couldn't find the mapping for the n bytes.");
//                }
//                bytesString.append(representationMap.get(nBytesRepresentation));
//            }
//            bytesToWrite = BytesManipulator.convertHexStringToBytesArray(bytesString.toString());
//            bytesString = new StringBuilder();
//
//            proFileWriter.writeNextFilePart(bytesToWrite);
//
//            readBytes = proFileReader.readNextFilePart();
//        }
//
//
//    }

    public void readBytesAndExportCompression(String filePathToCompress, int n, HashMap<String, String> representationMap, int paddingBits) {

        // initialize data structures and objects
        ProFileReader proFileReader = new ProFileReader(filePathToCompress);
        byte[] readBytes;

        ProFileWriter proFileWriter = new ProFileWriter(generateCompressionFilePath(filePathToCompress, n));
        byte[] bytesToWrite;

        StringBuilder binaryStringBuilder = new StringBuilder("0".repeat(paddingBits));
        String tempNBytes;  // in hexadecimal

        readBytes = proFileReader.readNextFilePart();

        while (readBytes.length != 0) {

            for (int i = 0 ; i < readBytes.length ; i+=n) {

                tempNBytes = BytesManipulator.bytesToHexadecimalString(readBytes, i, Math.min(i + n, readBytes.length));
                if (!representationMap.containsKey(tempNBytes)) {
                    Logger.logMsgFrom(this.getClass().getName(), "Error when compressing the file .. an N bytes word had no mapping ..", 1);
                    throw new RuntimeException("Error when compressing the file .. an N bytes word had no mapping ..");
                }

                binaryStringBuilder.append(representationMap.get(tempNBytes));
            }

            String binaryString = binaryStringBuilder.toString();
            binaryStringBuilder = new StringBuilder(binaryString.substring(binaryString.length() - (binaryString.length() % 8)));
            binaryString = binaryString.substring(0, binaryString.length() - (binaryString.length() % 8));

            bytesToWrite = BytesManipulator.convertBinStringToBytesArray(binaryString);
            proFileWriter.writeNextFilePart(bytesToWrite);

            readBytes = proFileReader.readNextFilePart();
        }
    }

    private String generateCompressionFilePath(String originalFilePath, int n) {
        Path path = Paths.get(originalFilePath);
        Path parentDir = path.getParent();
        String newFileName = "20010435_" + n + "." + path.getFileName() + ".hc";
        Path newPath = parentDir.resolve(newFileName);
        return newPath.toString();
    }

}
