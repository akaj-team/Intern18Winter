package asiantech.internship.summer.model;

public class Money {
    private String month;
    private int sale;
    private int expense;

    public Money(String month, int sale, int expense) {
        this.month = month;
        this.sale = sale;
        this.expense = expense;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public int getExpense() {
        return expense;
    }

    public void setExpense(int expense) {
        this.expense = expense;
    }
}
