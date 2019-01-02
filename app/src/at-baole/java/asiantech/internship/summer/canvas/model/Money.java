package asiantech.internship.summer.canvas.model;

public class Money {
    private String month;
    private int sale;
    private int expense;

    public Money(String month, int sale, int expense) {
        this.month = month;
        this.sale = sale;
        this.expense = expense;
    }

    public String getYear() {
        return month;
    }

    public int getSales() {
        return sale;
    }

    public int getExpenses() {
        return expense;
    }
}
