
import java.time.LocalDate;

public class Expense {

    // Private data members
    private int id;
    private String title;
    private double amount;
    private String category;
    private LocalDate date;

    // Constructor
    public Expense(int id, String title, double amount,
                   String category, LocalDate date) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

   
    @Override
    public String toString() {
        return "ID: " + id +
               " | Title: " + title +
               " | Amount: Rs. " + amount +
               " | Category: " + category +
               " | Date: " + date;
    }
}

