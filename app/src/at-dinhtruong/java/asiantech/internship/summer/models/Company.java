package asiantech.internship.summer.models;

public class Company {
    private int mIdCompany;
    private String mNameCompany;

    public int getmIdCompany() {
        return mIdCompany;
    }

    public void setmIdCompany(int mIdCompany) {
        this.mIdCompany = mIdCompany;
    }

    public String getmNameCompany() {
        return mNameCompany;
    }

    public void setmNameCompany(String mNameCompany) {
        this.mNameCompany = mNameCompany;
    }

    public Company(int mIdCompany, String mNameKompany) {
        this.mIdCompany = mIdCompany;
        this.mNameCompany = mNameKompany;
    }

    public Company(String mNameCompany) {
        this.mNameCompany = mNameCompany;
    }

    public Company() {
    }
}
