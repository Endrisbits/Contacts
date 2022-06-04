package contacts.records;

import contacts.common.ContactRecord;

public class RecordBuilder {
    private enum TYPE {
        PERSON("person"),
        ORGANIZATION("organization");

        private String name;

        TYPE(String str) {
            this.name = str;
        }

        public static TYPE parseType(String str) throws IllegalArgumentException{
            for(TYPE type : TYPE.values()) {
                if(str.matches(type.getName())) return type;
            }
            throw new IllegalArgumentException("Unknown Command");
        }

        String getName() {
            return this.name;
        }
    }

    public static ContactRecord create(String typeName) throws IllegalArgumentException {
        TYPE type = TYPE.parseType(typeName);
        switch (type) {
            case PERSON: {
                return new PersonRecord();
            }
            case ORGANIZATION: {
                return new OrganizationRecord();
            }
            default : return null;
            }
    }

    public static String printTypes() {
        TYPE[] values = TYPE.values();
        int i = 0;

        StringBuilder sb = new StringBuilder("(");
        while(i < values.length-1) {
            sb.append(values[i].getName() + ", ");
            i++;
        }
        sb.append(values[i].getName() + ")");
        return sb.toString();
    }
}
