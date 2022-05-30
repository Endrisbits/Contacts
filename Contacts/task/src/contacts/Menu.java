package contacts;

public interface Menu {
    enum COMMAND{ADD,REMOVE,EDIT,COUNT,LIST,EXIT};
    public void requestCommand();
    public void handleCommand(COMMAND cmd);
    public void addContactRecord();
    public void removeContactRecord();
    public void editContactRecord();
    public void countContactRecords();
    public void listContactRecords();
    public void exit();
}
