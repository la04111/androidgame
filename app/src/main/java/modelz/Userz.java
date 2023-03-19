package modelz;

public class Userz {

    private int userzID;
    private String userzName;
    private String userzPassword;
    private String userzMail;
    private int userzRole;

    public Userz() {
    }

    public Userz(int userzID, String userzName, String userzMail, String userzPassword) {
        this.userzID = userzID;
        this.userzName = userzName;
        this.userzPassword = userzPassword;
        this.userzMail = userzMail;
    }

    public Userz(String userzName, String userzMail, String userzPassword) {
        this.userzName = userzName;
        this.userzPassword = userzPassword;
        this.userzMail = userzMail;
    }

    public int getUserzID() {
        return userzID;
    }

    public void setUserzID(int userzID) {
        this.userzID = userzID;
    }

    public String getUserzName() {
        return userzName;
    }

    public void setUserzName(String userzName) {
        this.userzName = userzName;
    }

    public String getUserzPassword() {
        return userzPassword;
    }

    public void setUserzPassword(String userzPassword) {
        this.userzPassword = userzPassword;
    }

    public String getUserzMail() {
        return userzMail;
    }

    public void setUserzMail(String userzMail) {
        this.userzMail = userzMail;
    }

    public int getUserzRole() {
        return userzRole;
    }

    public void setUserzRole(int userzRole) {
        this.userzRole = userzRole;
    }
}
