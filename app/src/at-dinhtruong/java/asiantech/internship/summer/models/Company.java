package asiantech.internship.summer.models;

public class Company {
    private int mIdCompany;
    private String mNameCompany;

    public Company(int idCompany, String nameCompany) {
        this.mIdCompany = idCompany;
        this.mNameCompany = nameCompany;
    }

    public Company(String nameCompany) {
        this.mNameCompany = nameCompany;
    }

    public Company() {
    }

    public int getIdCompany() {
        return mIdCompany;
    }

    public void setIdCompany(int idCompany) {
        this.mIdCompany = idCompany;
    }

    public String getNameCompany() {
        return mNameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.mNameCompany = nameCompany;
    }
}
