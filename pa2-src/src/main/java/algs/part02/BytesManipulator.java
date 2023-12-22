package algs.part02;

import java.util.HashMap;

public class BytesManipulator {

    public static HashMap<String, Long> updateFrequencyMap(byte[] filePart, int n, HashMap<String, Long> freqMap) {

        if(freqMap == null)
            freqMap = new HashMap<>();

        for(int i = 0 ; i < filePart.length ; i+=n) {

            String nBytesStringRepresentation = convertBytesToHexString(filePart, i, Math.min(i + n, filePart.length));
            if(freqMap.containsKey(nBytesStringRepresentation)) {
                freqMap.replace(nBytesStringRepresentation, freqMap.get(nBytesStringRepresentation) + 1);
            } else {
                freqMap.put(nBytesStringRepresentation, 1L);
            }

        }

        return freqMap;
    }

    public static String convertBytesToHexString(byte[] bytes, int startIdx, int endIdx) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = startIdx; i < endIdx; i++) {
            stringBuilder.append(convertByteToHex(bytes[i]));
        }
        return stringBuilder.toString();
    }

    // Helper method to convert byte to its uppercase hexadecimal representation
    public static String convertByteToHex(byte b) {
        char[] hexChars = new char[2];
        hexChars[0] = Character.toUpperCase(Character.forDigit((b >> 4) & 0xF, 16));
        hexChars[1] = Character.toUpperCase(Character.forDigit((b & 0xF), 16));
        return new String(hexChars);
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
        StringBuilder binaryString = new StringBuilder(8);
        for (int i = 7; i >= 0; i--) {
            binaryString.append((b >> i) & 1);
        }
        return binaryString.toString();
    }

    public static int convertBytesToInt(byte[] bytes) {
        if (bytes.length != 4)
            throw new RuntimeException("Bytes array must be of size 4 to be converted to an int ..");

        int value = 0;
        for(byte b : bytes) {
            value = (value << 8) | (b & 0xFF);
        }

        return value;
    }

    public static long convertBytesToLong(byte[] bytes) {
        if (bytes.length != 8)
            throw new RuntimeException("Bytes array must be of size 8 to be converted to a long ..");

        long value = 0;
        for(byte b : bytes) {
            value = (value << 8) | (b & 0xFF);
        }

        return value;
    }

    public static String convertBytesToBinaryString(byte[] bytes, byte paddingBits) {
        StringBuilder stringBuilder = new StringBuilder();

        for (byte b : bytes) {
            for (int i = 7 ; i >= 0 ; i--) {
                if(paddingBits == 0) {
                    stringBuilder.append((b >> i) & 1);
                } else {
                    paddingBits--;
                }
            }
        }

        return stringBuilder.toString();
    }

    public static byte[] convertIntToBytes(int number) {

        byte[] bytes = new byte[Integer.BYTES];
        for (int i = 0; i < bytes.length; i++) {
            bytes[bytes.length - i - 1] = (byte) (number & 0xFF);
            number >>= 8;
        }

        return bytes;
    }

    public static byte[] convertLongToBytes(long number) {

        byte[] bytes = new byte[Long.BYTES];
        for (int i = 0; i < bytes.length; i++) {
            bytes[bytes.length - i - 1] = (byte) (number & 0xFF);
            number >>= 8;
        }

        return bytes;
    }

}
