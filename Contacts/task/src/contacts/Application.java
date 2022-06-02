package contacts;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Application implements Menu{
    private ArrayList<ContactRecord> contactsList = new ArrayList<>(50);
    private final Scanner inputConsole;
    private final PrintStream outputConsole;
    private Pattern[] cmdPatterns;
    public boolean running = true;


    Application(Scanner inputSource, PrintStream outputConsole) {
        this.inputConsole = inputSource;
        this.outputConsole = outputConsole;
        cmdPatterns = new Pattern[COMMAND.values().length];
        for (int i = 0; i < COMMAND.values().length; i++) {
            COMMAND cmd = COMMAND.values()[i];
            cmdPatterns[i] = Pattern.compile("\\s*" + cmd.name() + "\\s*", Pattern.CASE_INSENSITIVE);
        }
    }

    @Override
    public void requestCommand() {
        outputConsole.print(requestCommandTextLine);
        String userCommand = inputConsole.nextLine();
        COMMAND translatedUserCommand = translateCommand(userCommand);
        if(translatedUserCommand!=null) handleCommand(translatedUserCommand);
        else outputConsole.println("Command unrecognized. Please try again!");
    }

    private COMMAND translateCommand(String userCommand) {
        COMMAND translatedUserCommand = null;
        for(int i = 0; i< cmdPatterns.length; i++) {
            if(cmdPatterns[i].matcher(userCommand).matches()) {
                translatedUserCommand = COMMAND.values()[i];
            }
        }
        return translatedUserCommand;
    }

    @Override
    public void handleCommand(COMMAND action) {
        switch (action) {
            case ADD:     this.addContactRecord(); break;
            case REMOVE:  this.removeContactRecord(); break;
            case EDIT:    this.editContactRecord(); break;
            case COUNT:   this.countContactRecords(); break;
            case INFO:    this.infoAboutRecords(true); break;
            case EXIT:    this.exit(); break;
        }
    }

    @Override
    public void addContactRecord() {
        String type;

        outputConsole.print("Enter the type (person, organization): ");
        type = this.inputConsole.nextLine();
        switch (type) {
            case "person" : {
                PersonRecord newPersonRecord = new PersonRecord();
                populateContactRecord(newPersonRecord);
                break;
            }
            case "organization" : {
                OrganizationRecord newOrganizationRecord = new OrganizationRecord();
                populateContactRecord(newOrganizationRecord);
                break;
            }
            default : {
                outputConsole.println("\nUnknown command!");
                break;
            }
        }
    }

    private void populateContactRecord(ContactRecord contactRecord) {
        String newValue;
        for (String modField : contactRecord.getModifiableFieldsNames()) {
            outputConsole.printf("\nEnter the %s:", modField);
            newValue = this.inputConsole.nextLine();
            if(!contactRecord.modifyField(modField,newValue)) {
                outputConsole.printf("\nBad %s!", modField);
            }
        }
        this.contactsList.add(contactRecord);
        outputConsole.println("\nThe record added.");
    }

    @Override
    public void removeContactRecord() {
        if(this.contactsList.size()>0) {
            infoAboutRecords(false);
            outputConsole.print("Select a record: ");
            int selectedRecord = inputConsole.nextInt();
            inputConsole.nextLine();
            try {
                contactsList.remove(selectedRecord-1);
                outputConsole.println("\nThe record removed!");
            } catch (IndexOutOfBoundsException e){
                outputConsole.println("\nThe selected record doesn't exist!");
            }
        }
        else {
            outputConsole.println("No records to remove!");
        }
    }

    @Override
    public void editContactRecord() {
        if(contactsList.size()>0) {
            infoAboutRecords(false);
            outputConsole.print("\nSelect a record: ");
            int selectedRecord = inputConsole.nextInt();
            inputConsole.nextLine();
            try {
                ContactRecord contactToEdit = contactsList.get(selectedRecord - 1);
                outputConsole.print("\nSelect a field (");
                String[] modFields = contactToEdit.getModifiableFieldsNames();
                for(int i = 0; i < modFields.length; i++) {
                    if(i < modFields.length-1) outputConsole.printf("%s, ",modFields[i]);
                    else outputConsole.printf("%s): ",modFields[i]);
                }
                String selectedField = inputConsole.nextLine();
                boolean selectedFieldExists = false;
                for (String field : modFields) {
                    if (selectedField.equals(field)) {
                        selectedFieldExists = true;
                        break;
                    }
                }
                if(!selectedFieldExists) {
                    outputConsole.println("\nUnknown Field!");
                    return;
                }

                outputConsole.printf("\nEnter %s: ", selectedField);
                String newValue = inputConsole.nextLine();

                if(contactToEdit.modifyField(selectedField, newValue)) {
                    outputConsole.println("\nRecord saved!");
                } else { outputConsole.println("\nWrong value.");}
            } catch (IndexOutOfBoundsException e) {
                this.outputConsole.println("\nRecord doesn't exist!");
            }
        }
        else {
            outputConsole.println("\nNo records to edit!");
        }
    }

    @Override
    public void countContactRecords() {
        outputConsole.printf("\nThe Phone Book has %d records", this.contactsList.size());
    }

    @Override
    public void infoAboutRecords(boolean specific) {
        int selected = -1;
        for(int i = 0; i<contactsList.size(); i++) {
            ContactRecord contactRecord = contactsList.get(i);
            outputConsole.printf("\n%d. %s",(i+1),contactRecord.listShortDetails());
        }
        if(specific) {
            outputConsole.print("\nSelect a record:");
            selected = inputConsole.nextInt();
            inputConsole.nextLine();
            outputConsole.println();
            try {
                outputConsole.println(contactsList.get(selected-1).toString());
            } catch (IndexOutOfBoundsException e) {
                //outputConsole.println("Contact doesn't exist!");
            }
        }
    }

    @Override
    public void exit() {
        this.running = false;
    }
}
