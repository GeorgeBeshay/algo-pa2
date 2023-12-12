package algs.part01;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class ActivitySelectionDP {

    public static void main(String[] args) {

        if(args.length < 1)
            throw new RuntimeException("Input file was not passed as an argument.");

        ActivitySelectionDP activitySelectionDP = new ActivitySelectionDP();

        long ans = activitySelectionDP.solve(args[0], (args.length == 2 && Boolean.parseBoolean(args[1])));
        System.out.println("Solution = " + ans);
    }

    public long solve(String inputFilePath, boolean... topDownApproach) {

        int[][] activities = FileIO.scanInput(inputFilePath);
        Arrays.sort(activities, Comparator.comparingInt(a -> a[0]));        // sort based on activity start time.

        long ans;   // variable to store the result

        if (topDownApproach.length < 1 || !topDownApproach[0]) {
            System.out.println("Solving using bottom up approach (default) .. ");
            ans = bottomUpApproach(activities);            // solve using bottom up

        } else {
            System.out.println("Solving using top down approach .. ");
            ans = topDownApproach(activities, 0, null);     // solve using top down

        }

        FileIO.exportOutput(inputFilePath, ans);        // export to same input file directory.
        System.out.println("Result exported.");
        return ans;
    }

    private long bottomUpApproach(int[][] sortedActivities) {

        Long[] solution = new Long[sortedActivities.length];
        solution[sortedActivities.length - 1] = (long) sortedActivities[sortedActivities.length-1][2];

        for(int i = sortedActivities.length - 2 ; i >= 0 ; i--) {
            int nextCompatible = getFirstCompatibleActivityOptimized(sortedActivities, i);
            solution[i] = Math.max(
                    solution[i+1],
                    sortedActivities[i][2] +
                            (nextCompatible >= 0 ? solution[nextCompatible] : 0));
        }

        return solution[0];
    }

    private long topDownApproach(int[][] sortedActivities, int startActivityIdx, HashMap<Integer, Long> memo) {

        if(memo == null)
            memo = new HashMap<>();

        if(startActivityIdx < 0 || startActivityIdx >= sortedActivities.length)
            return 0L;

        if (!memo.containsKey(startActivityIdx)) {
            int firstCompatibleActivityIdx = getFirstCompatibleActivityOptimized(sortedActivities, startActivityIdx);
            memo.put(
                    startActivityIdx,
                    Math.max(
                            topDownApproach(sortedActivities, startActivityIdx + 1, memo),
                            sortedActivities[startActivityIdx][2] + topDownApproach(sortedActivities, firstCompatibleActivityIdx, memo)
                    )
            );

        }

        return memo.get(startActivityIdx);
    }

    private int getFirstCompatibleActivity(int[][] sortedActivities, int lastActivityIdx) {

        for(int i = lastActivityIdx + 1 ; i < sortedActivities.length ; i++) {
            if(sortedActivities[i][0] >= sortedActivities[lastActivityIdx][1])
                return i;
        }

        return -1;
    }

    private int getFirstCompatibleActivityOptimized(int[][] sortedActivities, int lastActivityIdx) {
        int start = lastActivityIdx + 1;
        int end = sortedActivities.length - 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;

            if (sortedActivities[mid][0] >= sortedActivities[lastActivityIdx][1]) {
                if (mid == 0 || sortedActivities[mid - 1][0] < sortedActivities[lastActivityIdx][1]) {
                    return mid;
                } else {
                    end = mid - 1; // Go left
                }
            } else {
                start = mid + 1; // Go right
            }
        }

        return -1;
    }

    private void displayActivities(boolean[] displaySteps, int[][] activities) {
        if(displaySteps.length < 1 || !displaySteps[0])
            return;
        for (int[] activity : activities) System.out.println(activity[0] + " " + activity[1] + " " + activity[2]);
        System.out.println();
    }

}
