import fit.ColumnFixture;
public class WypozyczalniaTestFit extends ColumnFixture{
    public String dbconnect;
    public String user;
    public String password;

    public String getDbconnect() {
        return dbconnect;
    }

    public void setDbconnect(String dbconnect) {
        this.dbconnect = dbconnect;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean inicjalizacjaPolaczenia() throws Exception {
        try{
            SetUp.wyp.initializeConnection(dbconnect, user, password);
        } catch(Exception e) {
            return false;
        }
        return true;
    }
}
