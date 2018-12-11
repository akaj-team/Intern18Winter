package asiantech.internship.summer.models;

public class Employee {
    private int mIdEmployee;
    private int mCompanyId;
    private String mNameEmployee;

    public int getmIdEmployee() {
        return mIdEmployee;
    }

    public void setmIdEmployee(int mIdEmployee) {
        this.mIdEmployee = mIdEmployee;
    }

    public int getmCompanyId() {
        return mCompanyId;
    }

    public void setmCompanyId(int mCompanyId) {
        this.mCompanyId = mCompanyId;
    }

    public String getmNameEmployee() {
        return mNameEmployee;
    }

    public void setmNameEmployee(String mNameEmployee) {
        this.mNameEmployee = mNameEmployee;
    }

    public Employee(int mIdEmployee, int mIdKompany, String mNameEmployee) {
        this.mIdEmployee = mIdEmployee;
        this.mCompanyId = mIdKompany;
        this.mNameEmployee = mNameEmployee;
    }

    public Employee(int mCompanyId, String mNameEmployee) {
        this.mCompanyId = mCompanyId;
        this.mNameEmployee = mNameEmployee;
    }

    public Employee() {
    }
}
