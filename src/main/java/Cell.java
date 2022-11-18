import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cell {

    private int cellID;
    private int maxCapacity;
    private int usedCapacity = 0;

    private final List<Prisoner> cellMembers = new ArrayList<>();



    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getUsedCapacity() {
        return usedCapacity;
    }

    public int getCellID(){
        return cellID;
    }

    public int getFreeCapacity(){
        return maxCapacity-usedCapacity;
    }

    public int getCellSecurityLevel() {
        return cellMembers.stream().map(Prisoner::getSecurityLevel).max(Integer::compare).get();
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setUsedCapacity(int usedCapacity) {
        this.usedCapacity = usedCapacity;
    }

    public void setCellID(int cellID) {
        this.cellID = cellID;
    }

    public void addPrisonerToCell(Prisoner p){
        this.usedCapacity++;
        cellMembers.add(p);
    }

    public  void addPrisonersToCell(Prisoner[] prisoners){
        for (Prisoner p : prisoners)
            this.addPrisonerToCell(p);
    }

}
