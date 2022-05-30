package contacts;

import java.io.PrintStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PrintStream printStream = new PrintStream(System.out);
        Application application = new Application(scanner, printStream);

        while(!application.exitFlag) {
            application.requestCommand();
        }
    }

}
