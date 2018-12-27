package asiantech.internship.summer.models;

public class Employee {
    private int mIdEmployee;
    private int mCompanyId;
    private String mNameEmployee;

    public Employee(int idEmployee, int idCompany, String nameEmployee) {
        mIdEmployee = idEmployee;
        mCompanyId = idCompany;
        mNameEmployee = nameEmployee;
    }

    public Employee() {
    }

    public int getIdEmployee() {
        return mIdEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        mIdEmployee = idEmployee;
    }

    public int getCompanyId() {
        return mCompanyId;
    }

    public void setCompanyId(int companyId) {
        mCompanyId = companyId;
    }

    public String getNameEmployee() {
        return mNameEmployee;
    }

    public void setNameEmployee(String nameEmployee) {
        mNameEmployee = nameEmployee;
    }

}
