package contacts.menus;

import contacts.common.ContactRecord;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

abstract class Menu {
    protected MenuManager menuManager;
    protected Scanner in;
    protected PrintStream out;
    protected ArrayList<ContactRecord> contactsList;

    public abstract void enterMainLoop();
    public abstract void takeControl();
    public abstract void releaseControl(TYPE nextMenuName);

    protected String getListOfCommands(String[] commandsList) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for(int i = 0; i < commandsList.length; i++) {
            if(i < commandsList.length - 1) sb.append(commandsList[i] +", ");
            else sb.append(commandsList[i]+"):");
        }
        return sb.toString();
    }

    public Menu(Scanner inputSource, PrintStream outputConsole, ArrayList<ContactRecord> data, MenuManager manager) {
        this.in = inputSource;
        this.out = outputConsole;
        this.contactsList = data;
        this.menuManager = manager;
    }
}
