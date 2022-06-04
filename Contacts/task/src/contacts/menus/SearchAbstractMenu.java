package contacts.menus;

import contacts.records.ContactRecord;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class SearchMenu extends AbstractMenu {
    private final TYPE menuID = TYPE.SEARCH_MENU;
    private enum COMMAND{
        SELECT("[number]"),
        BACK("back"),
        AGAIN("again");

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
    private String query = "";
    private ArrayList<ContactRecord> searchResults;

    public SearchMenu (Scanner in, PrintStream out, ArrayList<ContactRecord> data, MenuManager manager) {
        super(in, out, data, manager);
        searchResults = new ArrayList<ContactRecord>();
    }

    @Override
    public void enterMainLoop() {
        String cmd;
        while (true) {
            out.printf("\n[search] Enter action %s: ", COMMAND.printTypes());
            cmd = in.nextLine();
            try {
                COMMAND command = COMMAND.parseCommand(cmd);
                switch (command) {
                    case SELECT: {
                        ContactRecord selectedRecord = searchResults.get(command.getSelectedIndex());
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
                    case AGAIN: {
                        simpleSearch();
                        break;
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
        simpleSearch();
        enterMainLoop();
    }

    private void simpleSearch() {
        try{
            searchResults.clear();
        } catch (Exception e) {}
        query = "";

        out.printf("\nEnter search query: ");
        query = in.nextLine();
        Pattern pattern = Pattern.compile(query, Pattern.CASE_INSENSITIVE);
        for(ContactRecord record : contactsList) {
            StringBuilder contactSearchable = new StringBuilder();
            for(String field: record.getModifiableFieldsNames()) {
                String fieldValue = record.getFieldValue(field);
                if(fieldValue != null) contactSearchable.append(fieldValue + " , ");
            }
            Matcher matcher = pattern.matcher(contactSearchable.toString());
            if(matcher.find()) searchResults.add(record);
        }
        int nResutls = searchResults.size();
        if(nResutls == 0) {out.printf("\nFound no results.");}
        else {
            out.printf("\nFound %d results:", nResutls);
            listSearchResults();
        }
    }

    private void listSearchResults() {
        for(ContactRecord record : searchResults) {
            out.printf("\n%s", record.listShortDetails());
        }
    }

    @Override
    public void releaseControl(TYPE nextMenu) {
        query = "";
        searchResults.clear();
        menuManager.setCurrentActiveMenu(nextMenu);
    }


}