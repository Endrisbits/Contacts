# A simple contacts application
This is a simple contacts application built in java. The main focus of this project was to implement it in a way that can be easily extended upon in the future. For that I made use of abstract classes and inheritance as well as polymorphism. Below you can find more details about the specifics of the implementation.
## Features and Menus
The application has the following options: 
- Add a new contact. 
- List all contacts 
- Count all contacts.
- Search for a specific contact.

Every menu has a list of commands that the user can type in as well as a shortcut pattern (first character of a command preceded by the character '-'). 
### Main Menu
This menu offers the option to add a new contact to the list and count all contacts already added. When done, the user can select the exit command to close the application. It also offers these commands that release control to the other menus as in: 
- list : releases control to the list menu.
- search :  releases control to the search menu.

### List Menu
This menu lists all contacts that are already saved. It offers the option to select one of them from the list by inputting their number. To go back to the Main Menu, the back command can be used. 

### Search Menu
This menu offers the option to search through the contacts by using a query. It does a simple linear search through the list by trying to match the query with the name of the contacts. After a query, a user can select to initiate a new search or select one of the results by typing the number they have on the list.

### Record Menu
This menu offers the option to edit the contact record or delete it from the list. If done, the user can go back to the main menu by typing the command "menu". 

## The Contact Records
This application contains two contact record classes, namely *PersonRecord* and *OrganizationRecord*, which both extend the abstract *ContactRecord* class. However, the implementation was done in such a way, that new types of records can be added in the future. Each class has its specific modifiable fields, but to make it possible to work generally, the functions *getModifiableFieldsNames()* and *modifyField(String fieldName, String newValue)* are used. 

There is also a RecordBuilder class, which offers the building of different types of Contact Record objects, depending on the user input. 

## The DataManager
This class offers an abstraction level of the implementation of the database functionality for this application. It is basically a wrapper of the ArrayList that is used to offer the main functions to add or remove a specific ContactRecord, as well as the functions *contains(ContactRecord cr), get(int i)*, and *size()*. 

Additionally, it offers the functions *serialize()* and *deserialize()*, to read and write from a file the contacts list the application uses.