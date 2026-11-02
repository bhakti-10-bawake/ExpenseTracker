
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static ExpenseManager manager = new ExpenseManager();
    static ExpenseFileHandler fileHandler = new ExpenseFileHandler();

    public static void main(String[] args) {

        fileHandler.loadExpenses(manager);

        System.out.println("Welcome to Expense Tracker!");

        int choice;

        do {
            System.out.println("\n========== EXPENSE TRACKER ==========");
            System.out.println("1. Add Expense");
            System.out.println("2. Add Recurring Expense");
            System.out.println("3. View All Expenses");
            System.out.println("4. Update Expense");
            System.out.println("5. Delete Expense");
            System.out.println("6. Search Expense");
            System.out.println("7. Calculate Total Expenses");
            System.out.println("8. Category-wise Summary");
            System.out.println("9. Monthly Expense Report");
            System.out.println("10. Sort Expenses by Amount");
            System.out.println("11. Save Expenses");
            System.out.println("12. Exit");
            System.out.println("=====================================");

            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:
                        addExpense(false);
                        break;

                    case 2:
                        addExpense(true);
                        break;

                    case 3:
                        viewExpenses(manager.viewExpenses());
                        break;

                    case 4:
                        updateExpense();
                        break;

                    case 5:
                        deleteExpense();
                        break;

                    case 6:
                        searchExpense();
                        break;

                    case 7:
                        System.out.println("Total Expenses: Rs. "
                                + manager.calculateTotal());
                        break;

                    case 8:
                        categorySummary();
                        break;

                    case 9:
                        monthlyReport();
                        break;

                    case 10:
                        viewExpenses(manager.sortByAmount());
                        break;

                    case 11:
                        fileHandler.saveExpenses(manager);
                        break;

                    case 12:
                        fileHandler.saveExpenses(manager);
                        System.out.println("Thank you for using Expense Tracker!");
                        break;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                choice = 0;

            } catch (InvalidExpenseException e) {
                System.out.println("Error: " + e.getMessage());
                choice = 0;

            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Use YYYY-MM-DD.");
                choice = 0;
            }

        } while (choice != 12);

        sc.close();
    }

    // Add regular or recurring expense
    public static void addExpense(boolean recurring)
            throws InvalidExpenseException {

        int id = manager.generateId();

        System.out.print("Enter expense title: ");
        String title = sc.nextLine();

        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(sc.nextLine());

        System.out.print("Enter category: ");
        String category = sc.nextLine();

        System.out.print("Enter date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(sc.nextLine());

        Expense expense;

        if (recurring) {
            System.out.print("Enter frequency (Monthly/Weekly/Yearly): ");
            String frequency = sc.nextLine();

            expense = new RecurringExpense(
                    id, title, amount, category, date, frequency);
        } else {
            expense = new Expense(
                    id, title, amount, category, date);
        }

        manager.addExpense(expense);

        System.out.println("Expense added successfully!");
    }

    // View expenses
    public static void viewExpenses(List<Expense> expenses) {

        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }

        System.out.println("\n--------- EXPENSE DETAILS ---------");

        for (Expense e : expenses) {
            System.out.println(e);
        }
    }

    // Update expense
    public static void updateExpense()
            throws InvalidExpenseException {

        System.out.print("Enter expense ID: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("Enter new title: ");
        String title = sc.nextLine();

        System.out.print("Enter new amount: ");
        double amount = Double.parseDouble(sc.nextLine());

        System.out.print("Enter new category: ");
        String category = sc.nextLine();

        System.out.print("Enter new date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(sc.nextLine());

        boolean updated = manager.updateExpense(
                id, title, amount, category, date);

        if (updated) {
            System.out.println("Expense updated successfully!");
        } else {
            System.out.println("Expense ID not found.");
        }
    }

    // Delete expense
    public static void deleteExpense() {

        System.out.print("Enter expense ID: ");
        int id = Integer.parseInt(sc.nextLine());

        if (manager.deleteExpense(id)) {
            System.out.println("Expense deleted successfully!");
        } else {
            System.out.println("Expense not found.");
        }
    }

    // Search expense
    public static void searchExpense() {

        System.out.println("1. Search by Category");
        System.out.println("2. Search by Date");

        System.out.print("Enter choice: ");
        int option = Integer.parseInt(sc.nextLine());

        if (option == 1) {

            System.out.print("Enter category: ");
            String category = sc.nextLine();

            viewExpenses(manager.searchByCategory(category));

        } else if (option == 2) {

            System.out.print("Enter date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(sc.nextLine());

            viewExpenses(manager.searchByDate(date));

        } else {
            System.out.println("Invalid search option.");
        }
    }

    // Category-wise summary
    public static void categorySummary() {

        System.out.println("\n--- CATEGORY SUMMARY ---");

        for (Map.Entry<String, Double> entry :
                manager.categorySummary().entrySet()) {

            System.out.println(entry.getKey() + " : Rs. "
                    + entry.getValue());
        }
    }

    // Monthly report
    public static void monthlyReport() {

        System.out.print("Enter year: ");
        int year = Integer.parseInt(sc.nextLine());

        System.out.print("Enter month (1-12): ");
        int month = Integer.parseInt(sc.nextLine());

        YearMonth selectedMonth = YearMonth.of(year, month);

        List<Expense> result =
                manager.monthlyReport(year, month);

        viewExpenses(result);

        double total = 0;

        for (Expense e : result) {
            total += e.getAmount();
        }

        System.out.println("Monthly Total: Rs. " + total);
    }
}
