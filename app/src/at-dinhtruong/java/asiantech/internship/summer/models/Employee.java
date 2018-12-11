package asiantech.internship.summer.models;

public class Employee {
    private int mIdEmployee;
    private int mCompanyId;
    private String mNameEmployee;

    public int getIdEmployee() {
        return mIdEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.mIdEmployee = idEmployee;
    }

    public int getCompanyId() {
        return mCompanyId;
    }

    public void setCompanyId(int companyId) {
        this.mCompanyId = companyId;
    }

    public String getNameEmployee() {
        return mNameEmployee;
    }

    public void setNameEmployee(String nameEmployee) {
        this.mNameEmployee = nameEmployee;
    }

    public Employee(int idEmployee, int idCompany, String nameEmployee) {
        this.mIdEmployee = idEmployee;
        this.mCompanyId = idCompany;
        this.mNameEmployee = nameEmployee;
    }

    public Employee(int companyId, String nameEmployee) {
        this.mCompanyId = companyId;
        this.mNameEmployee = nameEmployee;
    }

    public Employee() {
    }
}
