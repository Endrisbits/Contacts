package contacts.common;

import contacts.menus.MenuManager;
import contacts.records.ContactRecord;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Application {
    private ArrayList<ContactRecord> contactsList = new ArrayList<>(50);
    private final Scanner in;
    private final PrintStream out;
    private MenuManager menuManager;


    public Application(Scanner inputSource, PrintStream outputConsole) {
        this.in = inputSource;
        this.out = outputConsole;
        menuManager = new MenuManager(in, out, contactsList);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PrintStream printStream = new PrintStream(System.out);
        Application application = new Application(scanner, printStream);
        application.menuManager.dispatch();
    }
}