package contacts;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

class PersonRecord extends ContactRecord {
    private String firstName;
    private String lastName;
    private String gender = null;
    private LocalDate birthday = null;

    private final String[] modifiableFields = {"name", "surname", "gender", "birthday", "number"};

    PersonRecord () {
        super();
    }

    @Override
    public String[] getModifiableFieldsNames() {
        return modifiableFields;
    }

    @Override
    public boolean modifyField(String fieldName, String newValue) {
        switch (fieldName) {
            case "name" : setFirstName(newValue); break;
            case "surname" : setLastName(newValue); break;
            case "gender" : {
                if(!isValidGender(newValue)) return false;
                else {setGender(newValue); break;}
            }
            case "birthday" : {
                if(!isValidBirthday(newValue)) return false;
                else {setBirthday(newValue);break;}
            }
            case "number" : setPhoneNumber(newValue); break;
            default : return false;
        }
        updateLastEditTime();
        return true;
    }

    @Override
    public String getFieldValue(String fieldName) {
        switch (fieldName) {
            case "name" : return getFirstName();
            case "surname" : return getLastName();
            case "gender" : return getGender();
            case "birthday" : return getBirthday().toString();
            case "number" : return getPhoneNumber();
        }
        return null;
    }

    @Override
    public String listShortDetails() {
        return getFirstName() + " " + getLastName();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: " + this.getFirstName() + "\n");
        sb.append("Surname: " + this.getLastName() + "\n");
        //Birthday Display management Stuff
        sb.append("Birth date: ");
        if(this.getBirthday() != null) {
            sb.append(birthday.toString());
        } else {
            sb.append("[no data]");
        }
        sb.append("\n");

        sb.append("Gender: " + this.getGender() + "\n");
        sb.append("Number: " + this.getPhoneNumber() + "\n");
        sb.append("Time created: " + this.getCreationTime().toString() +"\n");
        sb.append("Time last edit: " + this.getLastEditTime().toString());

        return sb.toString();
    }

    private String getFirstName() {
        return firstName;
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private String getLastName() {
        return lastName;
    }

    private void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private String getGender() {
        if(this.gender ==null) {
            return "[no data]";
        } else {
            return this.gender;
        }

    }

    private void setGender(String gender) {
        if(isValidGender(gender)) this.gender = gender;
        else this.gender=null;
    }

    private boolean isValidGender(String test) {
        if(test == null) return false;
        else if(test.matches("[MF]")) return true;
        else return false;
    }

    private LocalDate getBirthday() {
        return birthday;
    }

    private void setBirthday(String birthday) {
        try {
            this.birthday = LocalDate.parse(birthday);
        } catch (DateTimeParseException e) {
            this.birthday = null;
        }

    }

    private boolean isValidBirthday(String test) {
        try {
            LocalDate.parse(test);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}