package asiantech.internship.summer.filestorage.model;

public class Company {
    private int mCompanyId;
    private String mCompanyName;

    public Company() {

    }

    public Company(String companyName) {
        this.mCompanyName = companyName;
    }

    public Company(int companyId, String companyName) {
        this.mCompanyId = companyId;
        this.mCompanyName = companyName;
    }

    public int getCompanyId() {
        return mCompanyId;
    }

    public void setCompanyId(int companyId) {
        this.mCompanyId = companyId;
    }

    public String getCompanyName() {
        return mCompanyName;
    }

    public void setCompanyName(String companyName) {
        this.mCompanyName = companyName;
    }
}
