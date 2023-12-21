package algs.part02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Metadata {

    public static String generateCompressionMetaData(HashMap<String, Long> freqMap, HashMap<String, String> representationMap) {
        StringBuilder metadata = new StringBuilder();

        metadata.append(representationMap.values().size()).append("\n");

        BigInteger totalBits = BigInteger.ZERO;
        for (Map.Entry<String, String> entry : representationMap.entrySet()) {
            metadata.append(entry.getValue()).append("=").append(entry.getKey()).append("\n");
            totalBits = totalBits.add(
                    BigInteger.valueOf(freqMap.get(entry.getKey()) * entry.getValue().length())
            );
        }

        metadata.append(
                BigInteger.valueOf(8).subtract(
                        totalBits.mod(BigInteger.valueOf(8))
                ).mod(BigInteger.valueOf(8))
        ).append("\n");

        return metadata.toString();
    }

    public static void writeMetadata(String metadataFilePath, String metadata) {
        try {
            FileWriter fileWriter = new FileWriter(metadataFilePath);
            fileWriter.write(metadata);
            fileWriter.close();
        } catch (IOException e) {
            Logger.logMsgFrom(Main.class.getName(), "Error occurred while writing file metadata\n" + e, 1);
        }
    }

    public static int readAndExtractMetadata(String metadataFilePath, HashMap<String, String> representationMap) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(metadataFilePath))) {
            String line;

            line = bufferedReader.readLine();
            int mapEntriesCount = Integer.parseInt(line);

            for (int i = 0 ; i < mapEntriesCount ; i++) {
                String[] keyValArr = bufferedReader.readLine().split("=");
                representationMap.put(keyValArr[0], keyValArr[1]);
            }

            return Integer.parseInt(bufferedReader.readLine());

        } catch (IOException e) {
            Logger.logMsgFrom(Metadata.class.getName(), "Error while reading metadata file: " + e, 1);
            throw new RuntimeException(e);
        }
    }

    // method gets the next representation map entry (String of bits, String of bytes)
    public String[] getNextEntry(ProFileReader proFileReader, int n) {
        String[] ans = new String[2];

        int hcSizeInBytes = BytesManipulator.convertBytesToInt(proFileReader.getNextXBytes(4));
        byte hcPaddingInBits = proFileReader.getNextByte();

        // use an array of bytes, an offset, to get the string of bits.
        ans[0] = BytesManipulator.convertBytesToBinaryString(proFileReader.getNextXBytes(hcSizeInBytes), hcPaddingInBits);
        ans[1] = BytesManipulator.convertBytesToHexString(proFileReader.getNextXBytes(n), 0, n);

        return ans;
    }

}
