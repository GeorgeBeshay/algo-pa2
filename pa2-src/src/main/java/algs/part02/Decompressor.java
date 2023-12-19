package algs.part02;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Decompressor {

    public static void main(String[] args) {
        Decompressor decompressor = new Decompressor();
        String filePath = "D:\\College\\Level 3\\Fall 2023 - 2024\\CSE 321 - Analysis and Design of Algorithms\\Programming Assignments\\Assignment 02\\algo-pa2\\pa2-src\\src\\main\\java\\algs\\part02\\tests\\20010435_4.recorded_video.mp4.hc";
        String metadataFilePath = "D:\\College\\Level 3\\Fall 2023 - 2024\\CSE 321 - Analysis and Design of Algorithms\\Programming Assignments\\Assignment 02\\algo-pa2\\pa2-src\\src\\main\\java\\algs\\part02\\tests\\recorded_video_metadata.txt";
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

    public void decompress(String compressedFilePath, String metadataFilePath) {
        Logger.logMsgFrom(this.getClass().getName(), "File decompression process has been started ..", -1);

        // extract representation map from the metadata file.
        HashMap<String, String> representationMap = new HashMap<>();
        int paddingBits = Metadata.readAndExtractMetadata(metadataFilePath, representationMap);

        // decompress the file
        readBytesAndExportDecompression(compressedFilePath, representationMap, paddingBits);

        Logger.logMsgFrom(this.getClass().getName(), "File decompression process is successfully completed ..", 0);
    }

//    public void readBytesAndExportDecompressionPro(String compressedFilePath, Map<String, String> representationMap) {
//        // initialize data structures and objects
//        ProFileReader proFileReader = new ProFileReader(compressedFilePath);
//        byte[] readBytes;
//
//        ProFileWriter proFileWriter = new ProFileWriter(generateOriginalFilePath(compressedFilePath));
//        byte[] bytesToWrite;
//
//        StringBuilder bytesString = new StringBuilder();
//        StringBuilder nBytesRepresentation = new StringBuilder();
//
//        readBytes = proFileReader.readNextFilePart();
//        while (readBytes.length != 0) {
//            for (byte readByte : readBytes) {
//                nBytesRepresentation.append(String.format("%02X", readByte));
//                if(representationMap.containsKey(nBytesRepresentation.toString())) {
//                    bytesString.append(representationMap.get(nBytesRepresentation.toString()));
//                    nBytesRepresentation = new StringBuilder();
//                }
//            }
//            bytesToWrite = BytesManipulator.convertHexStringToBytesArray(bytesString.toString());
//            bytesString = new StringBuilder();
//            proFileWriter.writeNextFilePart(bytesToWrite);
//
//            readBytes = proFileReader.readNextFilePart();
//        }
//    }

    public void readBytesAndExportDecompression(String compressedFilePath, Map<String, String> representationMap, int paddingBits) {
        // initialize data structures and objects
        ProFileReader proFileReader = new ProFileReader(compressedFilePath);
        byte[] readBytes;

        ProFileWriter proFileWriter = new ProFileWriter(generateOriginalFilePath(compressedFilePath));
        byte[] bytesToWrite;

        StringBuilder bytesString = new StringBuilder();        // bytes in hex
        StringBuilder nBitsRepresentation = new StringBuilder();

        readBytes = proFileReader.readNextFilePart();
        while (readBytes.length != 0) {
            for (byte readByte : readBytes) {

                String binaryRepresentation = BytesManipulator.convertByteToBinaryString(readByte);
                for (int i = 0 ; i  < binaryRepresentation.length() ; i++){
                    if(paddingBits == 0)
                        nBitsRepresentation.append(binaryRepresentation.charAt(i));
                    else
                        paddingBits--;
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

    public String generateOriginalFilePath(String compressedFilePath) {
        // Extracting filename without extension
        String fileNameWithoutExt = compressedFilePath.substring(compressedFilePath.indexOf('.') + 1, compressedFilePath.lastIndexOf('.'));

        // Extracting directory path
        String directoryPath = compressedFilePath.substring(0, compressedFilePath.lastIndexOf('\\'));

        // Creating a new file path

        return directoryPath + "\\extracted." + fileNameWithoutExt;
    }
}
