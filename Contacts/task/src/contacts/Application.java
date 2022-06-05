package contacts;

import contacts.database.DataManager;
import contacts.menus.MenuManager;
import contacts.records.ContactRecord;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Application {
    private ArrayList<ContactRecord> contactsList = new ArrayList<>(50);
    private final Scanner in;
    private final PrintStream out;
    private MenuManager menuManager;


    public Application(Scanner inputSource, PrintStream outputConsole, DataManager dataManager) {
        this.in = inputSource;
        this.out = outputConsole;
        menuManager = new MenuManager(in, out, dataManager);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PrintStream printStream = new PrintStream(System.out);
        DataManager dataManager = null;
        if(args.length >= 0) {
            try {
                if(args.length == 1){
                    dataManager = new DataManager(args[0]);
                } else if(args.length == 0) {
                    dataManager = new DataManager("");
                }
            } catch (IOException exception){
                printStream.println("Can't write to disk. Closing application.");
                return;
            }
        }

        Application application = new Application(scanner, printStream, dataManager);
        application.menuManager.dispatch();
    }
}