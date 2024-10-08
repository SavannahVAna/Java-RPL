import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileRPL {
    public static void main(String[] args) throws FileNotFoundException {
        String input = args[0];
        String output = args[1];
        File inputfile = new File(input);
        File outputfile = new File(output);
        Scanner in = new Scanner(inputfile);
        Scanner out = new Scanner(outputfile);
        in.useDelimiter(" ");

    }
}
