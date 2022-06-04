package contacts.menus;
import contacts.records.ContactRecord;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

class ListMenu extends Menu {
    private final TYPE menuID = TYPE.LIST_MENU;

    private enum COMMAND{
        SELECT("[number]"),
        BACK("back");

        private String name;
        private int selectedIndex = -1;

        COMMAND(String name) {
            this.name = name;
        }

        int getSelectedIndex() {
            return selectedIndex;
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
                if (str.matches("[0-9]*")) {
                    return COMMAND.SELECT.setIndex(Integer.parseInt(str));
                } else if(str.matches(cmd.getName())) return cmd;
            }
            throw new IllegalArgumentException("Unknown Command");
        }

        private COMMAND setIndex(int index) {
            this.selectedIndex = index;
            return this;
        }
    }

    ListMenu(Scanner in, PrintStream out, ArrayList<ContactRecord> data, MenuManager manager) {
        super(in, out, data, manager);
    }

    @Override
    public void enterMainLoop() {
        while (true) {
            out.printf("\n[list] Enter action %s: ", COMMAND.printTypes());
            String cmd = in.nextLine();

            try {
                COMMAND command = COMMAND.parseCommand(cmd);
                switch (command) {
                    case SELECT: {
                        ContactRecord selectedRecord = contactsList.get(command.getSelectedIndex()-1); //Starting from 0, displaying from 1
                        menuManager.register(TYPE.RECORD_MENU,
                                new RecordMenu(this.in, this.out, contactsList, menuManager,
                                selectedRecord));
                        releaseControl(TYPE.RECORD_MENU);
                        return;
                    }
                    case BACK: {
                        releaseControl(TYPE.MAIN_MENU);
                        return;
                    }
                }
            } catch (IllegalArgumentException e) {
                out.printf("\nWrong Command! Please try again...");
            } catch (IndexOutOfBoundsException e) {
                out.printf("\nChosen contact does not exist!");
            }
        }
    }

    @Override
    public void takeControl() {
        this.listAllRecords();
        this.enterMainLoop();
    }

    private void listAllRecords() {
        int nRecords = contactsList.size();
        if(nRecords == 0) {
            out.printf("\nThere are no records to be listed.");
            releaseControl(TYPE.MAIN_MENU);
        } else {
            int i = 1;
            for (ContactRecord cR : contactsList)
                out.printf("\n%d. %s", i, cR.listShortDetails());
                i++;
        }
    }

    @Override
    public void releaseControl(TYPE nextMenu) {
        menuManager.setCurrentActiveMenu(nextMenu);
    }
}