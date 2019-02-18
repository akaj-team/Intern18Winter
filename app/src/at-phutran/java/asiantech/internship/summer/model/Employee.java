package asiantech.internship.summer.model;

public class Employee {
    private int idEmployee;
    private String nameEmployee;
    private int idCompany;

    public Employee() {
    }

    public Employee(int idEmployee, String nameEmployee, int idCompany) {
        this.idEmployee = idEmployee;
        this.nameEmployee = nameEmployee;
        this.idCompany = idCompany;
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

    public int getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }
}
