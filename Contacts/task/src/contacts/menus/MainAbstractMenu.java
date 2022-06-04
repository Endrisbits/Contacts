package contacts.menus;

import contacts.records.ContactRecord;
import contacts.records.RecordBuilder;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

class MainAbstractMenu extends AbstractMenu {
    private final TYPE menuID = TYPE.MAIN_MENU;
    private enum COMMAND{
        ADD("add"),
        LIST("list"),
        SEARCH("search"),
        COUNT("count"),
        EXIT("exit");

        private String name;
        COMMAND (String name) {
            this.name = name;
        }

        String getName() {
            return this.name;
        }

        public static String printTypes () {
            COMMAND[] values = COMMAND.values();
            int i = 0;

            StringBuilder sb = new StringBuilder("(");
            while(i < values.length-1) {
                sb.append(values[i].getName() + ", ");
                i++;
            }
            sb.append(values[i].getName() + ")");
            return sb.toString();
        }

        static COMMAND parseCommand(String str) throws IllegalArgumentException {
            for(COMMAND cmd : COMMAND.values()) {
                if(str.matches(cmd.getName())) return cmd;
            }
            throw new IllegalArgumentException("Unknown Command");
        }
    }

    public MainAbstractMenu(Scanner in, PrintStream out, ArrayList<ContactRecord> data, MenuManager manager) {
        super(in, out, data, manager);
    }

    @Override
    public void enterMainLoop() {
        String cmd;
        while (true) {
            out.printf("\n[menu] Enter action %s: ", COMMAND.printTypes());
            cmd = in.nextLine();
            try {
                COMMAND command = COMMAND.parseCommand(cmd);
                switch (command) {
                    case ADD : this.addContactRecord(); break;
                    case LIST : releaseControl(TYPE.LIST_MENU); return;
                    case SEARCH: releaseControl(TYPE.SEARCH_MENU); return;
                    case COUNT:  countContactRecords(); break;
                    case EXIT: menuManager.close(); return;
                }
            } catch (IllegalArgumentException e) {
                out.printf("\nWrong Command! Please try again...");
            }
        }
    }

    @Override
    public void takeControl() {
        this.enterMainLoop();
    }

    @Override
    public void releaseControl(TYPE nextMenu) {
        menuManager.setCurrentActiveMenu(nextMenu);
    }

    private void addContactRecord() {
        String type;
        out.printf("Enter the type %s: ", RecordBuilder.printTypes());
        type = this.in.nextLine();
        try {
            ContactRecord newRecord = RecordBuilder.create(type);
            populateContactRecord(newRecord);
        } catch (IllegalArgumentException e) {
            out.printf("\nUnknown type! Please try again...");
        }
    }

    private void populateContactRecord(ContactRecord contactRecord) {
        String newValue;
        for (String modField : contactRecord.getModifiableFieldsNames()) {
            out.printf("\nEnter the %s:", modField);
            newValue = this.in.nextLine();
            if(!contactRecord.modifyField(modField,newValue)) {
                out.printf("\nBad %s!", modField);
            }
        }
        this.contactsList.add(contactRecord);
        out.println("\nThe record added.");
    }

    public void countContactRecords() {
        out.printf("\nThe Phone Book has %d records", this.contactsList.size());
    }
}