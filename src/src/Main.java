import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The File is read in as a command line argument.
 */
public class Main {
    private static  String[][] fileArray;
    public static void main(String[] args) {
        List<String> fileString = new ArrayList<>();
        try {
            File file = new File(System.getProperty("user.dir") + "/TestCases/" + args[0]);
            Scanner sc = new Scanner(file);
            while (sc.hasNext()){
                fileString.add(sc.nextLine());
            }

        } catch (Exception e){
            System.out.println(e);
        }
        fileArray = to2DArray(fileString);

        // Print the 2D array to verify
        for (String[] row : fileArray) {
            for (String element : row) {
                System.out.print(element);
            }
            System.out.println();
        }
    }

    /**
     * This is turning the file into  2d Array, separated by each character.
     * @param file
     * @return
     */
    private static String[][] to2DArray(List<String> file) {
        String[][] result = new String[file.size()][];
        for (int i = 0; i < file.size(); i++) {
            String line = file.get(i);
            result[i] = new String[line.length()];
            for (int j = 0; j < line.length(); j++) {
                result[i][j] = String.valueOf(line.charAt(j));
            }
        }
        return result;
    }

    /**
     * method to get fileArray in another file.
     * @return
     */
    public String[][] getFileArray(){
        return fileArray;
    }
}