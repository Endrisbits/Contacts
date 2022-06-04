package contacts.menus;

import contacts.records.ContactRecord;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

enum TYPE {MAIN_MENU, SEARCH_MENU, LIST_MENU, RECORD_MENU}

public class MenuManager {
    private HashMap<TYPE, Menu> menus = new HashMap<>(5);
    private Menu currentActiveMenu;
    private Scanner in;
    private PrintStream out;
    private ArrayList<ContactRecord> contactsList;
    private boolean exitFlag = false;

    public MenuManager(Scanner in, PrintStream out, ArrayList<ContactRecord> contactsList) {
        this.in = in;
        this.out = out;
        this.contactsList = contactsList;
        register(TYPE.MAIN_MENU, new MainMenu(in, out, contactsList, this));
        register(TYPE.SEARCH_MENU, new SearchMenu(in, out, contactsList, this));
        register(TYPE.LIST_MENU,new ListMenu(in, out, contactsList, this));
        register(TYPE.RECORD_MENU, new RecordMenu(in, out, contactsList, this));
        setCurrentActiveMenu(TYPE.MAIN_MENU);
    }

    public void register(TYPE type, Menu menu) {
        menus.put(type, menu);
    }

    public Menu getCurrentActiveMenu() {
        return this.currentActiveMenu;
    }

    public void setCurrentActiveMenu(TYPE type) {
        if(menus.containsKey(type)) this.currentActiveMenu = menus.get(type);
        else currentActiveMenu = null;
    }

    public Menu getMenu(TYPE key) {
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