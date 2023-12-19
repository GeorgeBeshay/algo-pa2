package algs.part02;

import java.util.HashMap;

public class BytesManipulator {

    public static HashMap<String, Integer> updateFrequencyMap(byte[] filePart, int n, HashMap<String, Integer> freqMap) {

        if(freqMap == null)
            freqMap = new HashMap<>();

        for(int i = 0 ; i < filePart.length ; i+=3) {

            String nBytesStringRepresentation = bytesToHexadecimalString(filePart, i, Math.min(i + n, filePart.length));
            if(freqMap.containsKey(nBytesStringRepresentation)) {
                freqMap.replace(nBytesStringRepresentation, freqMap.get(nBytesStringRepresentation) + 1);
            } else {
                freqMap.put(nBytesStringRepresentation, 1);
            }

        }

        return freqMap;
    }

    public static String bytesToHexadecimalString(byte[] bytes, int startIdx, int endIdx) {

        StringBuilder stringBuilder = new StringBuilder();
        for(int i = startIdx ; i < endIdx ; i++) {
            stringBuilder.append(String.format("%02X", bytes[i]));
        }

        return stringBuilder.toString();

    }

    public static byte[] convertBitsStringToBytes(String bitString) {
        byte[] equivalentBytes = new byte[(int) Math.ceil(bitString.length() / 8.0)];

        // The first B - 1 bytes are assured to be full bytes.
        int i = 0;
        for (; i < equivalentBytes.length - 1 ; i++)
            equivalentBytes[i] = Byte.parseByte(bitString.substring(i * 8, (i * 8) + 8), 2);

        // The last byte may have less than 8 bits.
        equivalentBytes[equivalentBytes.length - 1] = Byte.parseByte(
                bitString.substring(i * 8) + "0".repeat(8 - (bitString.length() % 8)),
                2);

        return equivalentBytes;
    }
}
