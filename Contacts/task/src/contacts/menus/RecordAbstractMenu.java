package contacts.menus;

import contacts.records.ContactRecord;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

class RecordMenu extends AbstractMenu {
    private final TYPE menuID = TYPE.RECORD_MENU;
    private enum COMMAND{
        EDIT("edit"),
        DELETE("delete"),
        MENU("menu");

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

    private ContactRecord selectedRecord;
    private int selectedRecordIndex = -1;

    public RecordMenu(Scanner in, PrintStream out, ArrayList<ContactRecord> data, MenuManager manager) {
        super(in, out, data, manager);
        this.selectedRecord = null;
        this.selectedRecordIndex = -1;
    }

    public RecordMenu(Scanner in, PrintStream out, ArrayList<ContactRecord> data, MenuManager manager,
                      ContactRecord contactRecord) {
        super(in, out, data, manager);
        this.selectedRecord = contactRecord;
        int i = 0;
        for(ContactRecord cR : contactsList) { //Necessary to make it useful with the search function as well.
            if(cR.equals(selectedRecord)) {this.selectedRecordIndex = i; break;}
            i++;
        }
    }


    @Override
    public void enterMainLoop() {
        while(true) {
            out.printf("\n[record] Enter action %s: ", COMMAND.printTypes());
            String cmd = in.nextLine();
            try {
                COMMAND command = COMMAND.parseCommand(cmd);
                switch (command) {
                    case EDIT: editContactRecord(); break;
                    case DELETE: {
                        contactsList.remove(selectedRecordIndex);
                        releaseControl(TYPE.MAIN_MENU);
                        return;
                    }
                    case MENU: {
                        releaseControl(TYPE.MAIN_MENU);
                        return;
                    }
                }
            } catch (IllegalArgumentException e) {
                out.printf("\nUnknown Command! Please try again...");
            } catch (IndexOutOfBoundsException e) {
                out.printf("\nThis contact does not exist! Going back to main menu...");
                releaseControl(TYPE.MAIN_MENU);
            }
        }
    }

    @Override
    public void takeControl() {
        this.printRecord();
        this.enterMainLoop();
    }

    @Override
    public void releaseControl(TYPE nextMenu) {
        selectedRecord = null;
        selectedRecordIndex = -1;
        menuManager.setCurrentActiveMenu(nextMenu);
    }

    public void editContactRecord() {
        if(contactsList.size()>0 && selectedRecordIndex!=-1) {
            ContactRecord contactToEdit = this.selectedRecord;
            out.print("\nSelect a field (");
            String[] modFields = contactToEdit.getModifiableFieldsNames();
            for(int i = 0; i < modFields.length; i++) {
                if(i < modFields.length-1) out.printf("%s, ",modFields[i]);
                else out.printf("%s): ",modFields[i]);
            }
            String selectedField = in.nextLine();
            boolean selectedFieldExists = false;
            for (String field : modFields) {
                if (selectedField.equals(field)) {
                    selectedFieldExists = true;
                    break;
                }
            }
            if(!selectedFieldExists) {
                out.println("\nUnknown Field!");
                return;
            }
            else {
                out.printf("\nEnter %s: ", selectedField);
                String newValue = in.nextLine();
                if(contactToEdit.modifyField(selectedField, newValue)) {
                    out.println("\nSaved!");
                } else { out.println("\nWrong value.");}
                printRecord();
            }
        }
        else {
            out.println("\nNo records to edit!");
        }
    }

    private void printRecord() {
        out.printf("\n%s", selectedRecord.toString());
    }
}



