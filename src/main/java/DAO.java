import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class DAO {
    String url = "jdbc:mysql://localhost:3306/prison_management";
    String uname = "root";
    String password = "Szoftmod2022";
    Connection conn = DriverManager.getConnection(url, uname, password);
    Statement statement = conn.createStatement();

    public DAO() throws SQLException {

    }

    public List<String> getPrisonNames() throws SQLException {
        ResultSet result = statement.executeQuery("select * from prisons");
        List<String> prisons = new ArrayList<>();

        while (result.next()) {
            prisons.add(result.getString(1));
        }

        return prisons;
    }

    public List<String> getRegisteredUsers() throws SQLException {
        ResultSet result = statement.executeQuery("select * from users");
        List<String> users = new ArrayList<>();

        while (result.next()) {
            users.add(result.getString(1));
        }

        return users;
    }

    public HashMap<String, String> getRegisteredPasswords() throws SQLException {
        ResultSet result = statement.executeQuery("select * from passwords");
        HashMap<String, String> passwords = new HashMap<String, String>();

        while (result.next()) {
            passwords.put(result.getString(2), result.getString(1));
        }

        return passwords;
    }

    public void registerNewUser(String username, String password) throws SQLException {
        statement.executeUpdate("INSERT INTO users VALUES ('" + username + "')");
        statement.executeUpdate("INSERT INTO passwords VALUES ('" + password + "','" + username + "')");
    }

    public List<String> getCrimes() throws SQLException {
        ResultSet result = statement.executeQuery("select * from crimes");
        List<String> crimes = new ArrayList<>();

        while (result.next()) {
            crimes.add(result.getString(1));
        }

        return crimes;
    }


    public  List<Prisoner> getPrisonerList() throws SQLException {
        ResultSet result = statement.executeQuery("select * from prisoners");
        List<Prisoner> prisonerList = new ArrayList<>();
        while (result.next()) {
            Prisoner currentPrisoner = new Prisoner();
            currentPrisoner.setUniqueID(Integer.parseInt(result.getString(1)));
            currentPrisoner.setFirstName(result.getString(2));
            currentPrisoner.setLastName(result.getString(3));
            currentPrisoner.setAge(Integer.parseInt(result.getString(4)));
            currentPrisoner.setSex(result.getString(5));
            currentPrisoner.setEntranceDate(LocalDate.parse(result.getString(6)));
            currentPrisoner.setReleaseDate(LocalDate.parse(result.getString(7)));
            currentPrisoner.setSecurityLevel(Integer.parseInt(result.getString(8)));
            currentPrisoner.setCellNum(Integer.parseInt(result.getString(9)));
            currentPrisoner.setCrimes(new String[]{result.getString(10)});
            
            prisonerList.add(currentPrisoner);
        }
        return prisonerList;
    }

    public void  addNewPrisoner(int ID, String fName, String lName, int age, String sex, LocalDate entranceDate, LocalDate releaseDate, int secLevel, int cellNum, String crimes) throws SQLException {
        statement.executeUpdate("INSERT INTO prisoners VALUE ('" + ID + "','" + fName  + "','" + lName + "','" + age + "','" + sex + "','" + entranceDate + "','" + releaseDate + "','" + secLevel + "','" + cellNum + "','" + crimes + "')");
    }

    public void  removePrisoner(int ID) throws SQLException {
        statement.executeUpdate("DELETE FROM prisoners WHERE id = " + ID);
    }

    public int calculateSecurityLevel(List<String> crimes) throws SQLException {
        int secLevel = 0;

        for (String crime : crimes){
            ResultSet result = statement.executeQuery("select security_level from crimes where crime_name = '" + crime + "'");
            while (result.next())
                if (result.getInt(1) > secLevel)
                    secLevel = result.getInt(1);
        }

        return secLevel;
    }

    public void addSelectedCellNum(int cellNum) throws SQLException {
        statement.executeUpdate("INSERT INTO temp (cell_num) VALUE ('" + cellNum + "')");
    }

    public int getLastSelectedCellNum() throws SQLException {
        ResultSet resultSet = statement.executeQuery(" select * from temp order by Id DESC LIMIT 1");
        resultSet.next();
        return resultSet.getInt(1);
    }
}
