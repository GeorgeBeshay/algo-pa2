package algs.part02;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Utilities {

    public static String generateCompressionFilePath(String originalFilePath, int n) {
        Path path = Paths.get(originalFilePath);
        Path parentDir = path.getParent();
        String newFileName = "20010435." + n + "." + path.getFileName() + ".hc";
        Path newPath = parentDir.resolve(newFileName);
        return newPath.toString();
    }

    public static String generateOriginalFilePath(String compressedFilePath) {
        // Extracting filename without extension         path\20010435.1.filename.txt.hc => path\extracted.20010435.1.filename.txt
        String fileNameWithoutExt = compressedFilePath.substring(compressedFilePath.lastIndexOf('\\') + 1, compressedFilePath.lastIndexOf('.'));

        // Extracting directory path
        String directoryPath = compressedFilePath.substring(0, compressedFilePath.lastIndexOf('\\'));

        // Creating a new file path
        return directoryPath + "\\extracted." + fileNameWithoutExt;
    }

    public static byte computePaddingBits(int binaryStringLength) {
        return (byte) ((8 - (binaryStringLength % 8)) % 8);
    }

}
