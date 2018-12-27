package asiantech.internship.summer.models;

public class Company {
    private int mIdCompany;
    private String mNameCompany;

    public Company(int idCompany, String nameCompany) {
        mIdCompany = idCompany;
        mNameCompany = nameCompany;
    }

    public Company(String nameCompany) {
        mNameCompany = nameCompany;
    }

    public Company() {
    }

    public int getIdCompany() {
        return mIdCompany;
    }

    public void setIdCompany(int idCompany) {
        mIdCompany = idCompany;
    }

    public String getNameCompany() {
        return mNameCompany;
    }

    public void setNameCompany(String nameCompany) {
        mNameCompany = nameCompany;
    }
}
