package contacts;

public class ContactRecord {
    private String firstName;
    private String lastName;
    private String phoneNumber = "";
    private String generalGroupRegex = "([ -][0-9a-zA-Z]{2,})";
    private String validNumberRegex = "[+]?\\([0-9a-zA-Z]{1,}\\)" + generalGroupRegex + "*" +
            "|[+]?[0-9a-zA-Z]{1,}[ -]\\([0-9a-zA-Z]{2,}\\)" + generalGroupRegex + "*" +
            "|[+]?[0-9a-zA-Z]{1,}" + generalGroupRegex + "*";

    private boolean isValidPhoneNumber (String testPhoneNumber) {
        return testPhoneNumber.matches(this.validNumberRegex);
    }

    public ContactRecord() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        if(!this.hasNumber()) return "[no number]";
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) throws Exception
    {
        boolean validNumber = isValidPhoneNumber(phoneNumber);
        if(validNumber) {
            this.phoneNumber = phoneNumber;
        } else {
            this.phoneNumber = "";
            throw new Exception(("Wrong Number Format"));
        }
    }

    public boolean hasNumber() {
        return !(this.phoneNumber.isEmpty());
    }
}
