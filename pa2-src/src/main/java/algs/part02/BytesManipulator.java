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
}
