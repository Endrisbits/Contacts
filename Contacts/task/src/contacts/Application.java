package contacts;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Application implements Menu{
    private ArrayList<ContactRecord> contactsList = new ArrayList<>(50);
    private Scanner inputConsole;
    private PrintStream outputConsole;
    private Pattern[] cmdPatterns;
    public boolean exitFlag = false;

    Application(Scanner inputSource, PrintStream outputConsole) {
        this.inputConsole = inputSource;
        this.outputConsole = outputConsole;
        cmdPatterns = new Pattern[COMMAND.values().length];
        for(int i = 0; i< COMMAND.values().length; i++) {
            COMMAND cmd = COMMAND.values()[i];
            cmdPatterns[i] = Pattern.compile("\\s*"+cmd.name()+"\\s*", Pattern.CASE_INSENSITIVE);
        }
    }

    @Override
    public void requestCommand() {
        outputConsole.println("Enter action (add, remove, edit, count, list, exit):");
        String userCommand = inputConsole.nextLine();
        COMMAND translatedUserCommand = null;
        for(int i = 0; i< cmdPatterns.length; i++) {
            if(cmdPatterns[i].matcher(userCommand).matches()) {
                translatedUserCommand = COMMAND.values()[i];
            }
        }
        if(translatedUserCommand!=null) handleCommand(translatedUserCommand);
        else outputConsole.println("Command unrecognized. Please try again!");
    }

    @Override
    public void handleCommand(COMMAND action) {
        switch (action) {
            case ADD:     this.addContactRecord(); break;
            case REMOVE:  this.removeContactRecord(); break;
            case EDIT:    this.editContactRecord(); break;
            case COUNT:   this.countContactRecords(); break;
            case LIST:    this.listContactRecords(); break;
            case EXIT:    this.exit(); break;
        }
    }

    @Override
    public void addContactRecord() {
        String firstName, lastName, phoneNumber;

        outputConsole.println("Enter the name:");
        firstName = this.inputConsole.nextLine();
        outputConsole.println("\nEnter the surname:");
        lastName = this.inputConsole.nextLine();
        outputConsole.println("\nEnter the number:");
        phoneNumber = this.inputConsole.nextLine();

        ContactRecord newContactRecord = new ContactRecord();
        newContactRecord.setFirstName(firstName);
        newContactRecord.setLastName(lastName);
        try {
            newContactRecord.setPhoneNumber(phoneNumber);
        } catch (Exception e) {
            outputConsole.println(e.getMessage());
        }
        this.contactsList.add(newContactRecord);
        outputConsole.println("\nThe record added.");
    }

    @Override
    public void removeContactRecord() {
        if(this.contactsList.size()>0) {
            listContactRecords();
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
            listContactRecords();
            outputConsole.print("Select a record: ");
            int selectedRecord = inputConsole.nextInt();
            inputConsole.nextLine();
            try {
                ContactRecord contactToEdit = contactsList.get(selectedRecord-1);
                outputConsole.print("\nSelect a field (name, surname, number):");
                String selectedField = inputConsole.nextLine();
                String newFieldData = "";
                switch (selectedField) {
                    case "name" : {
                        outputConsole.print ("\nEnter name:");
                        newFieldData = inputConsole.nextLine();
                        contactToEdit.setFirstName(newFieldData);
                        break;
                    }
                    case "surname" : {
                        outputConsole.print ("\nEnter surname:");
                        newFieldData = inputConsole.nextLine();
                        contactToEdit.setLastName(newFieldData);
                        break;
                    }
                    case "number" : {
                        outputConsole.print ("\nEnter number:");
                        newFieldData = inputConsole.nextLine();
                        contactToEdit.setPhoneNumber(newFieldData);
                        break;
                    }
                    default: throw new Exception("\nSelected field does not exist!");
                }

                outputConsole.println("\nThe record updated!");
            } catch (IndexOutOfBoundsException e){
                outputConsole.println("\nThe selected Record doesn't exist!");
            } catch (Exception e) {
                outputConsole.println(e.getMessage());
            }
        }
        else {
            outputConsole.println("No records to edit!");
        }
    }

    @Override
    public void countContactRecords() {
        outputConsole.printf("\nThe Phone Book has %d records", this.contactsList.size());
    }

    @Override
    public void listContactRecords() {
        for(int i = 0; i<contactsList.size(); i++) {
            ContactRecord contactRecord = contactsList.get(i);
            outputConsole.printf("\n%d. %s %s, %s",(i+1),contactRecord.getFirstName(), contactRecord.getLastName(), contactRecord.getPhoneNumber());
        }
        outputConsole.println();
    }

    @Override
    public void exit() {
        this.exitFlag = true;
    }
}
