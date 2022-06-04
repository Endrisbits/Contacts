package contacts.records;

import contacts.common.ContactRecord;

class OrganizationRecord extends ContactRecord {
    private String organizationName;
    private String address;

    private final String[] modifiableFields = {"name", "address", "number"};

    public OrganizationRecord() {
        super();
    }

    @Override
    public String[] getModifiableFieldsNames() {
        return modifiableFields;
    }

    @Override
    public boolean modifyField(String fieldName, String newValue) {
        switch (fieldName) {
            case "name" : setOrganizationName(newValue); break;
            case "address" : setAddress(newValue); break;
            case "number" : setPhoneNumber(newValue); break;
            default : return false;
        }
        updateLastEditTime();
        return true;
    }

    @Override
    public String getFieldValue(String fieldName) {
        switch (fieldName) {
            case "name" : return getOrganizationName();
            case "address" : return getAddress();
            case "number" : return getPhoneNumber();
        }
        return null;
    }

    @Override
    public String listShortDetails() {
        return this.getOrganizationName();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Organization name: " + this.getOrganizationName() + "\n");
        sb.append("Address: " + this.getAddress() + "\n");
        sb.append("Number: " + this.getPhoneNumber() + "\n");
        sb.append("Time created: " + this.getCreationTime().toString() + "\n");
        sb.append("Time last edit: " + this.getLastEditTime().toString());

        return sb.toString();
    }

    private String getOrganizationName() {
        return organizationName;
    }

    private void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    private String getAddress() {
        return address;
    }

    private void setAddress(String address) {
        this.address = address;
    }
}