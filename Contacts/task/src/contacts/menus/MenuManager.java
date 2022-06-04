package contacts.menus;

import contacts.records.ContactRecord;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

enum TYPE {MAIN_MENU, SEARCH_MENU, LIST_MENU, RECORD_MENU}

public class MenuManager {
    private HashMap<TYPE, AbstractMenu> menus = new HashMap<>(5);
    private AbstractMenu currentActiveAbstractMenu;
    private Scanner in;
    private PrintStream out;
    private ArrayList<ContactRecord> contactsList;
    private boolean exitFlag = false;

    public MenuManager(Scanner in, PrintStream out, ArrayList<ContactRecord> contactsList) {
        this.in = in;
        this.out = out;
        this.contactsList = contactsList;
        register(TYPE.MAIN_MENU, new MainAbstractMenu(in, out, contactsList, this));
        register(TYPE.SEARCH_MENU, new SearchAbstractMenu(in, out, contactsList, this));
        register(TYPE.LIST_MENU,new ListAbstractMenu(in, out, contactsList, this));
        register(TYPE.RECORD_MENU, new RecordAbstractMenu(in, out, contactsList, this));
        setCurrentActiveMenu(TYPE.MAIN_MENU);
    }

    public void register(TYPE type, AbstractMenu abstractMenu) {
        menus.put(type, abstractMenu);
    }

    public AbstractMenu getCurrentActiveMenu() {
        return this.currentActiveAbstractMenu;
    }

    public void setCurrentActiveMenu(TYPE type) {
        if(menus.containsKey(type)) this.currentActiveAbstractMenu = menus.get(type);
        else currentActiveAbstractMenu = null;
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