package asiantech.internship.summer.model;

public class Employee {
    private int id;
    private int code;
    private String name;

    public Employee(int id, int code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public Employee() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
