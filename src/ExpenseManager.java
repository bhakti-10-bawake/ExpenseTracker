
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class ExpenseManager {

    private ArrayList<Expense> expenses;

    // Constructor
    public ExpenseManager() {
        expenses = new ArrayList<>();
    }

    // Add expense
    public boolean addExpense(Expense expense) throws InvalidExpenseException {

        if (expense == null) {
            throw new InvalidExpenseException("Expense cannot be empty.");
        }

        if (expense.getTitle() == null || expense.getTitle().trim().isEmpty()) {
            throw new InvalidExpenseException("Title cannot be empty.");
        }

        if (expense.getAmount() <= 0) {
            throw new InvalidExpenseException("Amount must be greater than zero.");
        }

        if (expense.getCategory() == null || expense.getCategory().trim().isEmpty()) {
            throw new InvalidExpenseException("Category cannot be empty.");
        }

        if (expense.getDate() == null || expense.getDate().isAfter(LocalDate.now())) {
            throw new InvalidExpenseException("Invalid expense date.");
        }

        expenses.add(expense);
        return true;
    }

    // View all expenses
    public ArrayList<Expense> viewExpenses() {
        return new ArrayList<>(expenses);
    }

    // Get expenses for GUI and CSV handler
    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    // Update expense
    public boolean updateExpense(int id, String title, double amount,
                                 String category, LocalDate date)
                                 throws InvalidExpenseException {

        if (title == null || title.trim().isEmpty()) {
            throw new InvalidExpenseException("Title cannot be empty.");
        }

        if (amount <= 0) {
            throw new InvalidExpenseException("Amount must be greater than zero.");
        }

        if (category == null || category.trim().isEmpty()) {
            throw new InvalidExpenseException("Category cannot be empty.");
        }

        if (date == null || date.isAfter(LocalDate.now())) {
            throw new InvalidExpenseException("Invalid expense date.");
        }

        for (Expense expense : expenses) {

            if (expense.getId() == id) {
                expense.setTitle(title);
                expense.setAmount(amount);
                expense.setCategory(category);
                expense.setDate(date);

                return true;
            }
        }

        return false;
    }

    // Delete expense
    public boolean deleteExpense(int id) {

        for (int i = 0; i < expenses.size(); i++) {

            if (expenses.get(i).getId() == id) {
                expenses.remove(i);
                return true;
            }
        }

        return false;
    }

    // Search by category
    public ArrayList<Expense> searchByCategory(String category) {

        ArrayList<Expense> result = new ArrayList<>();

        for (Expense expense : expenses) {
            if (expense.getCategory().equalsIgnoreCase(category)) {
                result.add(expense);
            }
        }

        return result;
    }

    // Search by date
    public ArrayList<Expense> searchByDate(LocalDate date) {

        ArrayList<Expense> result = new ArrayList<>();

        for (Expense expense : expenses) {
            if (expense.getDate().equals(date)) {
                result.add(expense);
            }
        }

        return result;
    }

    // Calculate total expenses
    public double calculateTotal() {

        double total = 0;

        for (Expense expense : expenses) {
            total += expense.getAmount();
        }

        return total;
    }

    // Category-wise summary using HashMap
    public HashMap<String, Double> categorySummary() {

        HashMap<String, Double> summary = new HashMap<>();

        for (Expense expense : expenses) {

            String category = expense.getCategory();

            summary.put(
                category,
                summary.getOrDefault(category, 0.0) + expense.getAmount()
            );
        }

        return summary;
    }

    // Monthly report
    public ArrayList<Expense> monthlyReport(int month, int year) {

        ArrayList<Expense> report = new ArrayList<>();

        for (Expense expense : expenses) {

            if (expense.getDate().getMonthValue() == month &&
                expense.getDate().getYear() == year) {

                report.add(expense);
            }
        }

        return report;
    }

    // Sort expenses using lambda expression
    public ArrayList<Expense> sortByAmount() {

        ArrayList<Expense> sortedExpenses = new ArrayList<>(expenses);

        sortedExpenses.sort(
            (e1, e2) -> Double.compare(e1.getAmount(), e2.getAmount())
        );

        return sortedExpenses;
    }

    // Generate next expense ID
    public int generateId() {

        int maxId = 0;

        for (Expense expense : expenses) {
            if (expense.getId() > maxId) {
                maxId = expense.getId();
            }
        }

        return maxId + 1;
    }
}
