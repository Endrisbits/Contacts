package contacts;

public interface Menu {
    enum COMMAND{ADD, REMOVE, EDIT, COUNT, INFO, EXIT};
    public final String requestCommandTextLine = "Enter action (add, remove, edit, count, info, exit):";
    public void requestCommand();
    public void handleCommand(COMMAND cmd);
    public void addContactRecord();
    public void removeContactRecord();
    public void editContactRecord();
    public void countContactRecords();
    public void infoAboutRecords(boolean b);
    public void exit();
}
