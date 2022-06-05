package contacts.menus;

import contacts.database.DataManager;
import java.io.PrintStream;
import java.util.Scanner;

abstract class AbstractMenu {
    protected MenuManager menuManager;
    protected Scanner in;
    protected PrintStream out;
    protected DataManager dataManager;

    public abstract void enterMainLoop();
    public abstract void takeControl();
    public abstract void releaseControl(TYPE nextMenuName);

    public AbstractMenu(Scanner inputSource, PrintStream outputConsole, DataManager dataManager, MenuManager manager) {
        this.in = inputSource;
        this.out = outputConsole;
        this.dataManager = dataManager;
        this.menuManager = manager;
    }
}
