package algs.part01;

import java.io.*;
import java.util.Scanner;

public class FileIO {
    public static int[][] scanInput(String inputFilePath){
        int[][] activities;

        try {
            File file = new File(inputFilePath);
            Scanner scanner = new Scanner(file);

            activities = new int[scanner.nextInt()][3];

            for(int i = 0 ; i < activities.length ; i++){
                activities[i][0] = scanner.nextInt();
                activities[i][1] = scanner.nextInt();
                activities[i][2] = scanner.nextInt();
            }

            return activities;

        } catch (FileNotFoundException E) {
            throw new RuntimeException("Input file wasn't found.");

        } catch (Exception E) {
            throw new RuntimeException("Something went wrong while scanning input file.");

        }
    }

    public static void exportOutput(String inputFilePath, long answer) {
        String outputFilePath = inputFilePath.substring(0, inputFilePath.length() - 3) + "_20010435.out";
        writeToFile(outputFilePath, String.valueOf(answer));
    }

    public static void writeToFile(String filePath, String data) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(data);
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

}
