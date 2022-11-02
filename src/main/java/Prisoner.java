import java.time.LocalDate;

public class Prisoner {
    private int uniqueID;
    private String firstName;
    private String lastName;

    private int age;
    private String sex;
    private LocalDate entranceDate;
    private LocalDate releaseDate;
    private int securityLevel;
    private int cellNum;

    private String[] crimes;


    public int getUniqueID() {
        return uniqueID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public LocalDate getEntranceDate() {
        return entranceDate;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public int getSecurityLevel() {
        return securityLevel;
    }

    public int getCellNum() {
        return cellNum;
    }

    public String[] getCrimes() {
        return crimes;
    }

    public void setUniqueID(int uniqueID) {
        this.uniqueID = uniqueID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setEntranceDate(LocalDate entranceDate) {
        this.entranceDate = entranceDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setSecurityLevel(int securityLevel) {
        this.securityLevel = securityLevel;
    }

    public void setCellNum(int cellNum) {
        this.cellNum = cellNum;
    }

    public void setCrimes(String[] crimes) {
        this.crimes = crimes;
    }
}
