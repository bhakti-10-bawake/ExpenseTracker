
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ExpenseFileHandler {

    private static final String FILE_NAME = "data/expenses.csv";

    // Save expenses to CSV file
    public void saveExpenses(ExpenseManager manager) {

        File folder = new File("data");

        // Create data folder if it does not exist
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(folder, "expenses.csv");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            // Write CSV header
            writer.write("ID,Title,Amount,Category,Date,Type,Frequency");
            writer.newLine();

            // Write each expense
            for (Expense expense : manager.getExpenses()) {

                String type = "Regular";
                String frequency = "";

                if (expense instanceof RecurringExpense) {
                    type = "Recurring";
                    frequency = ((RecurringExpense) expense).getFrequency();
                }

                // Avoid breaking CSV columns with commas
                String title = expense.getTitle().replace(",", " ");
                String category = expense.getCategory().replace(",", " ");

                writer.write(
                    expense.getId() + "," +
                    title + "," +
                    expense.getAmount() + "," +
                    category + "," +
                    expense.getDate() + "," +
                    type + "," +
                    frequency
                );

                writer.newLine();
            }

            System.out.println("Expenses saved successfully!");
            System.out.println("CSV saved at: " + file.getAbsolutePath());

        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }

    // Load expenses from CSV file
    public void loadExpenses(ExpenseManager manager) {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("No existing expense file found. Starting with empty records.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {

                // Skip CSV header
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                if (line.trim().isEmpty()) {
                    continue;
                }

                try {
                    String[] data = line.split(",", -1);

                    if (data.length < 7) {
                        System.out.println("Skipping invalid CSV row: " + line);
                        continue;
                    }

                    int id = Integer.parseInt(data[0]);
                    String title = data[1];
                    double amount = Double.parseDouble(data[2]);
                    String category = data[3];
                    LocalDate date = LocalDate.parse(data[4]);
                    String type = data[5];
                    String frequency = data[6];

                    Expense expense;

                    if (type.equalsIgnoreCase("Recurring")) {
                        expense = new RecurringExpense(
                            id, title, amount, category, date, frequency
                        );
                    } else {
                        expense = new Expense(
                            id, title, amount, category, date
                        );
                    }

                    manager.addExpense(expense);

                } catch (Exception e) {
                    System.out.println("Skipping invalid CSV row: " + line);
                }
            }

            System.out.println("Expenses loaded successfully!");

        } catch (IOException e) {
            System.out.println("Error loading expenses: " + e.getMessage());
        }
    }
}
