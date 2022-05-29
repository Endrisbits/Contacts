package contacts;

import java.util.ArrayList;
import java.util.Scanner;

public class AppMain {
    private static ArrayList<ContactRecord> contactsList = new ArrayList<>(50);

    public static void addContactRecord(ContactRecord record) {
        contactsList.add(record);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the name of the person:");
        String firstName = scanner.nextLine();
        System.out.println("Enter the surname of the person:");
        String lastName = scanner.nextLine();
        System.out.println("Enter the number:");
        String phoneNumber = scanner.nextLine();
        System.out.println();
        /*---------------------------------------------*/
        ContactRecord cNew = new ContactRecord(firstName, lastName, phoneNumber);
        System.out.println("A record created!");
        addContactRecord(cNew);
        System.out.println("A Phone Book with a single record created!");
    }
}
