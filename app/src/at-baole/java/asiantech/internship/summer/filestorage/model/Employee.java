package asiantech.internship.summer.filestorage.model;

public class Employee {
    private int mEmployeeId;
    private int mCompanyId;
    private String mEmployeeName;

    public Employee() {

    }

    public Employee(int employeeId, int companyId, String employeeName) {
        this.mEmployeeId = employeeId;
        this.mCompanyId = companyId;
        this.mEmployeeName = employeeName;
    }

    public int getEmployeeId() {
        return mEmployeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.mEmployeeId = employeeId;
    }

    public int getCompanyId() {
        return mCompanyId;
    }

    public void setCompanyId(int companyId) {
        this.mCompanyId = companyId;
    }

    public String getEmployeeName() {
        return mEmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.mEmployeeName = employeeName;
    }
}
