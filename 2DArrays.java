import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.File;
import java.util.Random;

/**
* The ClassMarks program reads students.txt and assignments.txt
* into two string arrays. It calls GenerateMarks,
* which uses these arrays to randomly generate marks
* (mean 75, SD 10), and outputs the results to marks.csv.
*
* @author Remy Skelton
* @version 1.0
* @since 2025-04-06
*/

final class ClassMarks {

    /**
    * The maximum mark.
    */
    public static final int MAX_MARK = 100;

    /**
    * The minimum mark.
    */
    public static final int MIN_MARK = 0;

    /**
     * This is a private constructor used to satisfy the
     * style checker.
     *
     * @exception IllegalStateException Utility class.
     * @see IllegalStateException
    */
    private ClassMarks() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * This is the main method.
     *
     * @param args Unused.
     */

    public static void main(final String[] args) throws Exception {

        // Define paths to student and assignment input files
        File studentFile = new File("./students.txt");
        File assignmentFile = new File("./assignments.txt");

        // Set up scanners to read files
        Scanner studentScanner = new Scanner(studentFile);
        Scanner assignmentScanner = new Scanner(assignmentFile);

        // Read student names into a list
        ArrayList<String> studentNames = new ArrayList<String>();
        while (studentScanner.hasNextLine()) {
            studentNames.add(studentScanner.nextLine() + " ");
        }

        // Read assignment titles into a list
        ArrayList<String> assignmentTitles = new ArrayList<String>();
        while (assignmentScanner.hasNextLine()) {
            assignmentTitles.add(assignmentScanner.nextLine());
        }

        // Convert lists to arrays
        String[] studentsArray = new String[studentNames.size()];
        String[] assignmentsArray = new String[assignmentTitles.size()];

        for (int i = 0; i < studentNames.size(); i++) {
            studentsArray[i] = studentNames.get(i);
        }
        for (int i = 0; i < assignmentTitles.size(); i++) {
            assignmentsArray[i] = assignmentTitles.get(i);
        }

        // Close scanners
        studentScanner.close();
        assignmentScanner.close();

        // Generate random marks
        String[][] marksArray = generateMarks(assignmentsArray, studentsArray);

        // Write results to CSV
        FileWriter writer = new FileWriter("Marks.csv");

        // Write header row with assignment names
        writer.append("Student Name");
        for (String assignment : assignmentsArray) {
            writer.append(", " + assignment);
        }
        writer.append("\n");

        // Write student rows with marks
        for (int s = 0; s < studentsArray.length; s++) {
            writer.append(studentsArray[s]);
            for (int a = 0; a < assignmentsArray.length; a++) {
                writer.append(", " + marksArray[s + 1][a + 1]);
            }
            writer.append("\n");
        }

        // Close writer and confirm success
        writer.close();
        System.out.println("The file has been written successfully.");
    }

    /**
     * This method generates a 2D array of random marks
     * for each student and assignment.
     *
     * @param assignmentsArray Array of assignment names.
     * @param studentsArray Array of student names.
     * @return 2D array of generated marks as strings.
     */
    public static String[][] generateMarks(
            final String[] assignmentsArray, final String[] studentsArray) {

        // Set up Random object for generating Gaussian-distributed numbers
        Random randGen = new Random();

        // Initialize 2D array to store marks
        String[][] marksArray =
        new String[studentsArray.length + 1][assignmentsArray.length + 1];

        // Loop through students
        for (int studentIdx = 0;
        studentIdx < studentsArray.length + 1; studentIdx++) {

            // Loop through assignments
            for (int assignIdx = 0;
            assignIdx < assignmentsArray.length + 1; assignIdx++) {

                // Generate a mark with mean 75 and standard deviation 10
                int rawMark = (int) (randGen.nextGaussian() * 10 + 75);

                // Clamp mark between MIN_MARK and MAX_MARK and store as string
                marksArray[studentIdx][assignIdx] = Integer.toString(
                        Math.max(MIN_MARK, Math.min(MAX_MARK, rawMark)));
            }
        }

        // Return 2D array of marks
        return marksArray;
    }

}
