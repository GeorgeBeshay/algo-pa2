package algs.part02;

import java.util.HashMap;

/**
 * The Decompressor class is the entity responsible for implementing the overall decompressing algorithm. It uses a set
 * of utilities methods.
 */
public class Decompressor {

    /*
     * Algorithm Pseudocode
     * --------------------
     *
     * 1. Input file name to decompress
     * 2. Generate representation map from the metadata part of the compressed file.
     * 3. scan file bytes and map them to the real bytes.
     * 4. write the new bytes generated
     */

    /**
     * <b>Decompresses a file using Huffman's algorithm.</b>
     * <hr>
     * Implements the decompression overall process, this happens by first reading the metadata file and extracting
     * the representation map, and finally scanning the compressed file part by part and converting each huffman code
     * with its actual represented bytes.
     * <hr>
     * @param compressedFilePath The file path of the compressed file.
     */
    public void decompress(String compressedFilePath) {
        Logger.logMsgFrom(this.getClass().getName(), "File decompression process has been started ..", -1);

        // extract representation map from the metadata file.
        HashMap<String, String> representationMap = new HashMap<>();

        // decompress the file
        readBytesAndExportDecompression(compressedFilePath, representationMap);
        Logger.logMsgFrom(this.getClass().getName(), "File has been decompressed successfully.", 0);

        Logger.logMsgFrom(this.getClass().getName(), "File decompression process is successfully completed ..", 0);
    }

    /**
     * Reads bytes from the compressed file, performs decompression, and writes the result to a new file.
     * @param compressedFilePath The file path of the compressed file.
     * @param representationMap The mapping of compressed codes to original bytes.
     */
    public void readBytesAndExportDecompression(String compressedFilePath, HashMap<String, String> representationMap) {
        // initialize data structures and objects
        ProFileReader proFileReader = new ProFileReader(compressedFilePath);
        byte[] readBytes;

        try {
            proFileReader.getNextXBytes(12);
            int n = BytesManipulator.convertBytesToInt(proFileReader.getNextXBytes(4));
            proFileReader = null;
            ProFileReader.setBufferSizeMatchN(n);
            ProFileWriter.setBufferSizeCompatibleWith(n);
            proFileReader = new ProFileReader(compressedFilePath);
        } catch (Exception ignored) {
            proFileReader = new ProFileReader(compressedFilePath);
        }

        ProFileWriter proFileWriter = new ProFileWriter(Utilities.generateOriginalFilePath(compressedFilePath));
        byte[] bytesToWrite;

        // read metadata
        Object[] tempResults = Metadata.readAndExtractMetadataPro(representationMap, proFileReader);
        int paddingBits = (byte) tempResults[2];
        Logger.logMsgFrom(this.getClass().getName(), "Metadata has been extracted successfully.", 0);
        // ----------


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
