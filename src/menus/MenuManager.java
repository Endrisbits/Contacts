package contacts.menus;

import contacts.database.DataManager;
import contacts.records.ContactRecord;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

enum TYPE {MAIN_MENU, SEARCH_MENU, LIST_MENU, RECORD_MENU}

public class MenuManager {
    private HashMap<TYPE, AbstractMenu> menus = new HashMap<>(5);
    private AbstractMenu currentActiveMenu;
    private Scanner in;
    private PrintStream out;
    private boolean exitFlag = false;
    private DataManager dataManager;

    public MenuManager(Scanner in, PrintStream out, DataManager dataManager) {
        this.in = in;
        this.out = out;
        this.dataManager = dataManager;
        register(TYPE.MAIN_MENU, new MainMenu(in, out, dataManager, this));
        register(TYPE.SEARCH_MENU, new SearchMenu(in, out, dataManager, this));
        register(TYPE.LIST_MENU,new ListMenu(in, out, dataManager, this));
        register(TYPE.RECORD_MENU, new RecordMenu(in, out, dataManager, this));
        setCurrentActiveMenu(TYPE.MAIN_MENU);
    }

    public void register(TYPE type, AbstractMenu menu) {
        menus.put(type, menu);
    }

    public AbstractMenu getCurrentActiveMenu() {
        return this.currentActiveMenu;
    }

    public void setCurrentActiveMenu(TYPE type) {
        if(menus.containsKey(type)) this.currentActiveMenu = menus.get(type);
        else currentActiveMenu = null;
    }

    public AbstractMenu getMenu(TYPE key) {
        if(menus.containsKey(key)) return menus.get(key);
        else return null;
    }

    public void dispatch() {
        while(!exitFlag) {
            this.getCurrentActiveMenu().takeControl();
            out.println();
        }
    }

    public void close() {
        this.exitFlag = true;
    }
}