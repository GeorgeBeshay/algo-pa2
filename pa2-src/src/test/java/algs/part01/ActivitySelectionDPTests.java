package algs.part01;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActivitySelectionDPTests {

    private ActivitySelectionDP activitySelectionDP;
    private static final String testPath =  "D:\\College\\Level 3\\Fall 2023 - 2024\\CSE 321 - Analysis and Design of Algorithms\\Programming Assignments\\Assignment 02\\algo-pa2\\pa2-src\\src\\test\\java\\algs\\part01\\IOFiles\\test.in";

    @BeforeEach
    public void setup() {
        activitySelectionDP = new ActivitySelectionDP();
    }

    @AfterAll
    public static void terminate() {
        System.out.println("All testcases have been executed.");
    }

    @ParameterizedTest
    @MethodSource("generateActivities")
    public void test(int[][] activities, long expectedResult) {
        // Arrange
        String activitiesString = convertArrayToString(activities);
        FileIO.writeToFile(testPath, activitiesString);

        // Act
        long bottomUpResult = activitySelectionDP.solve(testPath);
        long topDownResult = activitySelectionDP.solve(testPath, true);

        // Assert
        assertEquals(bottomUpResult, topDownResult);
        assertEquals(bottomUpResult, expectedResult);
    }

    private static Stream<Arguments> generateActivities() {
        return Stream.of(
                Arguments.of(       // simple non overlapping activities
                        new int[][] {
                                {8, 9, 2},
                                {3, 6, 4},
                                {1, 3, 2},
                        },
                        8
                ),
                Arguments.of(               // overlapping activities
                        new int[][] {
                                {7, 9, 2},
                                {3, 6, 4},
                                {2, 5, 5},
                                {1, 3, 4},
                        },
                        10
                ),
                Arguments.of(               // all activities are overlapping with each other
                        new int[][] {
                                {1, 5, 15},
                                {1, 5, 24},
                                {1, 5, 12},
                                {1, 5, 33},
                                {1, 5, -1},
                                {1, 5, 0},
                                {1, 5, 1},
                                {1, 5, 2},
                                {1, 5, 3},
                                {1, 5, 4},

                        },
                        33
                ),
                Arguments.of(
                        new int[][] {
                                {1, 3, 5},
                                {2, 5, 8},
                                {3, 6, 7},
                                {4, 7, 9},
                        },
                        14
                ),
                Arguments.of(
                        new int[][] {
                                {5, 8, 3},
                                {2, 4, 2},
                                {7, 9, 5},
                                {1, 3, 4},
                                {15, 17, 1},
                        },
                        10
                ),
                Arguments.of(
                        new int[][] {
                                {1, 2, 3},
                                {2, 5, 4},
                                {2, 3, 1},
                                {3, 4, 2},
                        },
                        7
                ),
                Arguments.of(
                        new int[][] {
                                {1, 2, 1},
                                {2, 3, 2},
                                {3, 4, 5},
                        },
                        8
                )
        );
    }

    public String convertArrayToString(int[][] activities) {
        StringBuilder ans = new StringBuilder();

        ans.append(activities.length).append("\n");
        for (int[] activity : activities) {
            ans.append(activity[0]).append(" ").append(activity[1]).append(" ").append(activity[2]).append("\n");
        }

        return ans.toString();
    }
}
