package algs.part02;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Metadata {

    // method gets the next representation map entry (String of bits, String of bytes).
    public static String[] getNextEntry(ProFileReader proFileReader, int n) {
        String[] ans = new String[2];

        int hcSizeInBytes = BytesManipulator.convertBytesToInt(proFileReader.getNextXBytes(4));
        byte hcPaddingInBits = proFileReader.getNextByte();

        // use an array of bytes, an offset, to get the string of bits.
        ans[0] = BytesManipulator.convertBytesToBinaryString(proFileReader.getNextXBytes(hcSizeInBytes), hcPaddingInBits);
        ans[1] = BytesManipulator.convertBytesToHexString(proFileReader.getNextXBytes(n), 0, n);

        return ans;
    }

    public static Object[] readAndExtractMetadataPro(HashMap<String, String> representationMap, ProFileReader proFileReader) {
        // this function needs to extract the following:
        // 1. Representation Map
        // 2. metadataSize
        // 3. padding bits.
        // 4. n

        // reading configuration data
        long metadataSize = BytesManipulator.convertBytesToLong(proFileReader.getNextXBytes(8));
        int mapSize = BytesManipulator.convertBytesToInt(proFileReader.getNextXBytes(4));
        int n = BytesManipulator.convertBytesToInt(proFileReader.getNextXBytes(4));
        byte paddingBits = proFileReader.getNextByte();

        // reading representation map
        int hcSizeInBytes;
        byte hcPaddingInBits;

        for (long i = 0 ; i < mapSize - 1 ; i++) {
            hcSizeInBytes = BytesManipulator.convertBytesToInt(proFileReader.getNextXBytes(4));
            hcPaddingInBits = proFileReader.getNextByte();
            representationMap.put(
                    BytesManipulator.convertBytesToBinaryString(
                            proFileReader.getNextXBytes(hcSizeInBytes),
                            hcPaddingInBits
                    ),
                    BytesManipulator.convertBytesToHexString(
                            proFileReader.getNextXBytes(n),
                            0,
                            n
                    )
            );
        }

        hcSizeInBytes = BytesManipulator.convertBytesToInt(proFileReader.getNextXBytes(4));
        hcPaddingInBits = proFileReader.getNextByte();
        int rcSizeInBytes = BytesManipulator.convertBytesToInt(proFileReader.getNextXBytes(4));
        representationMap.put(
                BytesManipulator.convertBytesToBinaryString(
                        proFileReader.getNextXBytes(hcSizeInBytes),
                        hcPaddingInBits
                ),
                BytesManipulator.convertBytesToHexString(
                        proFileReader.getNextXBytes(n),
                        0,
                        rcSizeInBytes
                )
        );


        return new Object[] {
                metadataSize,
                n,
                paddingBits
        };

    }

    public static Object[] computeMetadata(HashMap<String, String> representationMap, HashMap<String, Long> freqMap, int n) {
        LinkedList<Byte> metadataBytes = new LinkedList<>();
        byte[] tempBytes;
        long metadataSize = 0;
        long totalBits = 0L;

        // add the mapSize field
        tempBytes = BytesManipulator.convertIntToBytes(representationMap.size());
        for(byte b : tempBytes)
            metadataBytes.add(b);
        metadataSize += tempBytes.length;

        // add the n field
        tempBytes = BytesManipulator.convertIntToBytes(n);
        for(byte b : tempBytes)
            metadataBytes.add(b);
        metadataSize += tempBytes.length;

        // add the map entries
        String lastEntry = null;
        int count = representationMap.size();
        for (Map.Entry<String, String> entry : representationMap.entrySet()) {

            if ((entry.getKey().length() != 2 * n) || (count == 1 && lastEntry == null)) {
                lastEntry = entry.getKey();
            } else {
                // format: hcSizeInBytes hcPaddingInBits hc rc

                // add hcSizeInBytes
                tempBytes = BytesManipulator.convertIntToBytes((int) Math.ceil(entry.getValue().length() / 8.0));
                for(byte b : tempBytes)
                    metadataBytes.add(b);
                metadataSize += tempBytes.length;

                // add hcPaddingInBits
                metadataBytes.add(Utilities.computePaddingBits(entry.getValue().length()));
                metadataSize += 1;

                // add hc
                tempBytes = BytesManipulator.convertBinStringToBytesArray("0".repeat(Utilities.computePaddingBits(entry.getValue().length())) + entry.getValue());
                for(byte b : tempBytes)
                    metadataBytes.add(b);
                metadataSize += tempBytes.length;

                // add rc
                tempBytes = BytesManipulator.convertHexStringToBytesArray(entry.getKey());
                for(byte b : tempBytes)
                    metadataBytes.add(b);
                metadataSize += tempBytes.length;

            }

            totalBits += freqMap.get(entry.getKey()) * entry.getValue().length();
            count--;

        }

        if (lastEntry == null) {
            throw new RuntimeException("Last map entry was not found ..");
        }

        // format: hcSizeInBytes hcPaddingInBits rcSizeInBytes hc rc

        // add hcSizeInBytes
        tempBytes = BytesManipulator.convertIntToBytes((int) Math.ceil(representationMap.get(lastEntry).length() / 8.0));
        for(byte b : tempBytes)
            metadataBytes.add(b);
        metadataSize += tempBytes.length;

        // add hcPaddingInBits
        metadataBytes.add(Utilities.computePaddingBits(representationMap.get(lastEntry).length()));
        metadataSize += 1;

        // add rcSizeInBytes
        tempBytes = BytesManipulator.convertIntToBytes(lastEntry.length() / 2);
        for(byte b : tempBytes)
            metadataBytes.add(b);
        metadataSize += tempBytes.length;

        // add hc
        tempBytes = BytesManipulator.convertBinStringToBytesArray("0".repeat(
                Utilities.computePaddingBits(representationMap.get(lastEntry).length())
                ) + representationMap.get(lastEntry)
        );
        for(byte b : tempBytes)
            metadataBytes.add(b);
        metadataSize += tempBytes.length;

        // add rc
        tempBytes = BytesManipulator.convertHexStringToBytesArray(lastEntry);
        for(byte b : tempBytes)
            metadataBytes.add(b);
        metadataSize += tempBytes.length;

        // add the padding bits at the specified position.
        //        metadataSizeInBytes
        //        mapSizeInEntriesCount
        //        n
        //        paddingBits
        metadataBytes.add(8, (byte) ((8 - (totalBits % 8)) % 8));
        metadataSize += 1;

        // finally, add the metadataBytes at the beginning of the file.
        metadataSize += 8;
        tempBytes = BytesManipulator.convertLongToBytes(metadataSize);
        for (int i = tempBytes.length - 1 ; i >= 0 ; i--)
            metadataBytes.addFirst(tempBytes[i]);

        return new Object[] {metadataBytes, (byte) ((8 - (totalBits % 8)) % 8)};
    }

}
