package algs.part02;

import java.util.HashMap;

public class BytesManipulator {

    public static HashMap<String, Long> updateFrequencyMap(byte[] filePart, int n, HashMap<String, Long> freqMap) {

        if(freqMap == null)
            freqMap = new HashMap<>();

        for(int i = 0 ; i < filePart.length ; i+=n) {

            String nBytesStringRepresentation = bytesToHexadecimalString(filePart, i, Math.min(i + n, filePart.length));
            if(freqMap.containsKey(nBytesStringRepresentation)) {
                freqMap.replace(nBytesStringRepresentation, freqMap.get(nBytesStringRepresentation) + 1);
            } else {
                freqMap.put(nBytesStringRepresentation, 1L);
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

    public static byte[] convertBinStringToBytesArray(String binaryString) {
        if (binaryString.isEmpty())
            return new byte[]{};
        if (binaryString.length() % 8 != 0) {
            Logger.logMsgFrom(BytesManipulator.class.getName(), "Error in converting binary string to bytes array ..", 1);
            throw new RuntimeException("Error in converting binary string to bytes array ..");
        }

        byte[] equivalentBytes = new byte[(int) Math.ceil(binaryString.length() / 8.0)];

        for (int i = 0; i < equivalentBytes.length ; i++) {
            equivalentBytes[i] = (byte) Integer.parseInt(binaryString.substring( i * 8, (i * 8) + 8), 2);
        }

        return equivalentBytes;
    }

    public static byte[] convertHexStringToBytesArray(String bytesString) {
        if (bytesString.isEmpty())
            return new byte[]{};

        if (bytesString.length() % 2 != 0)
            Logger.logMsgFrom(BytesManipulator.class.getName(), "Error in converting hexadecimal string to bytes array ..", 1);

        byte[] equivalentBytes = new byte[(int) Math.ceil(bytesString.length() / 2.0)];

        for (int i = 0; i < equivalentBytes.length ; i++) {
            equivalentBytes[i] = (byte) Integer.parseInt(bytesString.substring(2 * i, (2 * i) + 2), 16);
        }

        return equivalentBytes;
    }

    public static String convertByteToBinaryString(byte b) {
        return String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
    }
}
