package contacts.database;

import contacts.records.ContactRecord;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataManager {
    private String filePath = "Contacts.db";
    private ArrayList<ContactRecord> contactsList;

    public DataManager(String filePath) throws IOException, FileNotFoundException {
        if(filePath != null && !filePath.isBlank()) {
            this.filePath = filePath;
            //Read from disk
        }
        try (FileInputStream fis = new FileInputStream(this.filePath)) {
            contactsList = (ArrayList<ContactRecord>) this.deserialize();
            if(contactsList == null) contactsList = new ArrayList<>(50);
        } catch (Exception e) {
            contactsList = new ArrayList<>(50);
        }
    }

    public ArrayList<ContactRecord> simpleSearch(String query) {
        ArrayList<ContactRecord> searchResults = new ArrayList<>(5);
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
        return searchResults;
    }

    public ArrayList<ContactRecord> getData () {
        return this.contactsList;
    }

    public void serialize() throws IOException {
        FileOutputStream fos = new FileOutputStream(this.filePath);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this.contactsList);
        oos.close();
    }

    public Object deserialize() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(this.filePath);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }

    public int size() {
        if(contactsList == null) return 0;
        else return contactsList.size();
    }

    public void add(ContactRecord contactRecord) {
        this.contactsList.add(contactRecord);
        try {
            serialize();
        } catch (IOException e) {}
    }

    public String printShortDetails() {
        int i = 1;
        StringBuilder sb = new StringBuilder();
        for(ContactRecord cR : contactsList) {
            sb.append(i + ". " + cR.listShortDetails());
            i++;
        }
        return sb.toString();
    }

    public ContactRecord get(int i) {
        //User will see list numbering starting from 1, therefore theirs selections will reflect that.
        return contactsList.get(i-1);
    }

    public void remove(ContactRecord recordToRemove) {
        if(contactsList.remove(recordToRemove)) {
            try {
                serialize();
            } catch (IOException e) {}
        }
    }

    public boolean contains(ContactRecord selectedRecord) {
        return this.contactsList.contains(selectedRecord);
    }
}


