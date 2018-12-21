package asiantech.internship.summer.model;

public class Employee {
    private int idEmployee;
    private String nameEmployee;
    private int id_company;
    private boolean hasSelected;

    public Employee() {
    }

    public Employee(int idEmployee, String nameEmployee, int id_company, boolean hasSelected) {
        this.idEmployee = idEmployee;
        this.nameEmployee = nameEmployee;
        this.id_company = id_company;
        this.hasSelected = hasSelected;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getNameEmployee() {
        return nameEmployee;
    }

    public void setNameEmployee(String nameEmployee) {
        this.nameEmployee = nameEmployee;
    }

    public int getId_company() {
        return id_company;
    }

    public void setId_company(int id_company) {
        this.id_company = id_company;
    }

    public boolean isSelected() {
        return hasSelected;
    }

    public void setSelected(boolean hasSelected) {
        this.hasSelected = hasSelected;
    }
}
