package contacts;

import java.time.LocalDateTime;

abstract class ContactRecord {
    protected String getPhoneNumber() {
        if(!this.hasNumber()) return "[no number]";
        return phoneNumber;
    }

    protected void setPhoneNumber(String phoneNumber)
    {
        boolean validNumber = isValidPhoneNumber(phoneNumber);
        if(validNumber) {
            this.phoneNumber = phoneNumber;
        } else {
            this.phoneNumber = "";
        }
    }

    private boolean hasNumber() {
        return !(this.phoneNumber.isEmpty());
    }

    protected boolean isValidPhoneNumber (String testPhoneNumber) {
        return testPhoneNumber.matches(this.validNumberRegex);
    }

    public ContactRecord() {
        this.dateTimeOfCreation = LocalDateTime.now().withNano(0);
        updateLastEditTime();
    }

    protected LocalDateTime getCreationTime () {
        return this.dateTimeOfCreation;
    }

    protected LocalDateTime getLastEditTime () {
        return this.dateTimeOfLastEdit;
    }

    protected void updateLastEditTime () {
        this.dateTimeOfLastEdit = LocalDateTime.now().withNano(0);
    }

    public abstract String[] getModifiableFieldsNames();

    public abstract boolean modifyField(String fieldName, String newValue);

    public abstract String getFieldValue (String fieldName);

    public abstract String listShortDetails();

    private String phoneNumber = "";
    private String generalGroupRegex = "([ -][0-9a-zA-Z]{2,})";
    private String validNumberRegex = "[+]?\\([0-9a-zA-Z]{1,}\\)" + generalGroupRegex + "*" +
            "|[+]?[0-9a-zA-Z]{1,}[ -]\\([0-9a-zA-Z]{2,}\\)" + generalGroupRegex + "*" +
            "|[+]?[0-9a-zA-Z]{1,}" + generalGroupRegex + "*";

    private final LocalDateTime dateTimeOfCreation;
    private LocalDateTime dateTimeOfLastEdit;
}



