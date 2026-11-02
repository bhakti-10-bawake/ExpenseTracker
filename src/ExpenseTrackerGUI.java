
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public class ExpenseTrackerGUI extends JFrame {

    private ExpenseManager manager = new ExpenseManager();
    private ExpenseFileHandler fileHandler = new ExpenseFileHandler();

    private DefaultTableModel tableModel;
    private JTable expenseTable;

    private JLabel totalLabel;
    private JLabel countLabel;

    private Color primary = new Color(28, 155, 143);
    private Color background = new Color(245, 248, 250);

    public ExpenseTrackerGUI() {

        setTitle("Expense Tracker System");
        setSize(1050, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        fileHandler.loadExpenses(manager);

        createInterface();
        refreshTable();
    }

    private void createInterface() {

        setLayout(new BorderLayout());
        getContentPane().setBackground(background);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(primary);
        header.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel heading = new JLabel("EXPENSE TRACKER");
        heading.setFont(new Font("Arial", Font.BOLD, 25));
        heading.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Manage your daily expenses easily");
        subtitle.setForeground(Color.WHITE);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 13));

        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setOpaque(false);
        titlePanel.add(heading);
        titlePanel.add(subtitle);

        header.add(titlePanel, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // Dashboard cards
        JPanel dashboard = new JPanel(new GridLayout(1, 2, 20, 0));
        dashboard.setBackground(background);
        dashboard.setBorder(BorderFactory.createEmptyBorder(20, 25, 15, 25));

        totalLabel = new JLabel("Total Expenses: Rs. 0.00");
        countLabel = new JLabel("Total Transactions: 0");

        totalLabel.setFont(new Font("Arial", Font.BOLD, 19));
        countLabel.setFont(new Font("Arial", Font.BOLD, 19));

        dashboard.add(createCard(totalLabel));
        dashboard.add(createCard(countLabel));

        // Expense table
        String[] columns = {
            "ID", "Title", "Amount", "Category", "Date", "Type"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        expenseTable = new JTable(tableModel);
        expenseTable.setRowHeight(30);
        expenseTable.setFont(new Font("Arial", Font.PLAIN, 13));
        expenseTable.getTableHeader().setFont(
            new Font("Arial", Font.BOLD, 13)
        );

        JScrollPane scrollPane = new JScrollPane(expenseTable);

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(background);
        center.setBorder(BorderFactory.createEmptyBorder(0, 25, 20, 25));

        center.add(dashboard, BorderLayout.NORTH);
        center.add(scrollPane, BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);

        // Buttons
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        buttons.setBackground(Color.WHITE);

        addButton(buttons, "Add Expense", this::addExpense);
        addButton(buttons, "Edit Expense", this::updateExpense);
        addButton(buttons, "Delete", this::deleteExpense);
        addButton(buttons, "Search", this::searchExpense);
        addButton(buttons, "Sort", this::sortExpenses);
        addButton(buttons, "Summary", this::showSummary);
        addButton(buttons, "Monthly Report", this::monthlyReport);
        addButton(buttons, "Save", this::saveExpenses);

        add(buttons, BorderLayout.SOUTH);
    }

    private JPanel createCard(JLabel label) {

        JPanel card = new JPanel(new FlowLayout(FlowLayout.CENTER));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(
            new Color(220, 230, 230)
        ));

        label.setForeground(primary);
        card.add(label);

        return card;
    }

    private void addButton(JPanel panel, String name, Runnable action) {

        JButton button = new JButton(name);
        button.setBackground(primary);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));

        button.addActionListener(e -> action.run());

        panel.add(button);
    }

    // Add Expense
    private void addExpense() {

        JTextField title = new JTextField();
        JTextField amount = new JTextField();
        JComboBox<String> category = new JComboBox<>(
            new String[] {
                "Food", "Travel", "Shopping",
                "Education", "Bills", "Health", "Other"
            }
        );
        JTextField date = new JTextField(
            LocalDate.now().toString()
        );

        JCheckBox recurring = new JCheckBox("Recurring Expense");
        JTextField frequency = new JTextField("Monthly");

        JPanel form = new JPanel(new GridLayout(6, 2, 8, 8));

        form.add(new JLabel("Expense Title:"));
        form.add(title);

        form.add(new JLabel("Amount:"));
        form.add(amount);

        form.add(new JLabel("Category:"));
        form.add(category);

        form.add(new JLabel("Date (YYYY-MM-DD):"));
        form.add(date);

        form.add(recurring);
        form.add(frequency);

        form.add(new JLabel("Frequency (if recurring):"));
        form.add(new JLabel("Weekly / Monthly / Yearly"));

        int result = JOptionPane.showConfirmDialog(
            this, form, "Add New Expense",
            JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {

            try {

                int id = manager.generateId();
                double value = Double.parseDouble(amount.getText());
                LocalDate expenseDate = LocalDate.parse(date.getText());

                Expense expense;

                if (recurring.isSelected()) {
                    expense = new RecurringExpense(
                        id, title.getText(), value,
                        category.getSelectedItem().toString(),
                        expenseDate, frequency.getText()
                    );
                } else {
                    expense = new Expense(
                        id, title.getText(), value,
                        category.getSelectedItem().toString(),
                        expenseDate
                    );
                }

                manager.addExpense(expense);

                refreshTable();

                JOptionPane.showMessageDialog(
                    this, "Expense added successfully!"
                );

            } catch (Exception e) {

                JOptionPane.showMessageDialog(
                    this, "Invalid details: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // Refresh Table
    private void refreshTable() {

        tableModel.setRowCount(0);

        for (Expense e : manager.viewExpenses()) {

            String type = "Regular";

            if (e instanceof RecurringExpense) {
                type = "Recurring";
            }

            tableModel.addRow(new Object[] {
                e.getId(),
                e.getTitle(),
                e.getAmount(),
                e.getCategory(),
                e.getDate(),
                type
            });
        }

        totalLabel.setText(
            String.format("Total Expenses: Rs. %.2f",
                manager.calculateTotal())
        );

        countLabel.setText(
            "Total Transactions: " + manager.viewExpenses().size()
        );
    }

    // Update Expense
    private void updateExpense() {

        int row = expenseTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                this, "Please select an expense first."
            );
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);

        JTextField title = new JTextField(
            tableModel.getValueAt(row, 1).toString()
        );

        JTextField amount = new JTextField(
            tableModel.getValueAt(row, 2).toString()
        );

        JTextField category = new JTextField(
            tableModel.getValueAt(row, 3).toString()
        );

        JTextField date = new JTextField(
            tableModel.getValueAt(row, 4).toString()
        );

        JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));

        form.add(new JLabel("Title:"));
        form.add(title);

        form.add(new JLabel("Amount:"));
        form.add(amount);

        form.add(new JLabel("Category:"));
        form.add(category);

        form.add(new JLabel("Date:"));
        form.add(date);

        int result = JOptionPane.showConfirmDialog(
            this, form, "Update Expense",
            JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {

            try {

                boolean updated = manager.updateExpense(
                    id,
                    title.getText(),
                    Double.parseDouble(amount.getText()),
                    category.getText(),
                    LocalDate.parse(date.getText())
                );

                if (updated) {
                    refreshTable();
                    JOptionPane.showMessageDialog(
                        this, "Expense updated successfully!"
                    );
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                    this, "Error: " + e.getMessage()
                );
            }
        }
    }

    // Delete Expense
    private void deleteExpense() {

        int row = expenseTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                this, "Please select an expense first."
            );
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);

        int confirm = JOptionPane.showConfirmDialog(
            this, "Are you sure you want to delete this expense?",
            "Confirm Delete", JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {

            manager.deleteExpense(id);
            refreshTable();

            JOptionPane.showMessageDialog(
                this, "Expense deleted successfully!"
            );
        }
    }

    // Search Expense
    private void searchExpense() {

        String category = JOptionPane.showInputDialog(
            this, "Enter category to search:"
        );

        if (category == null) return;

        List<Expense> results = manager.searchByCategory(category);

        tableModel.setRowCount(0);

        for (Expense e : results) {
            tableModel.addRow(new Object[] {
                e.getId(), e.getTitle(), e.getAmount(),
                e.getCategory(), e.getDate(),
                e instanceof RecurringExpense ? "Recurring" : "Regular"
            });
        }
    }

    // Sort Expenses
    private void sortExpenses() {

        List<Expense> sorted = manager.sortByAmount();

        tableModel.setRowCount(0);

        for (Expense e : sorted) {
            tableModel.addRow(new Object[] {
                e.getId(), e.getTitle(), e.getAmount(),
                e.getCategory(), e.getDate(),
                e instanceof RecurringExpense ? "Recurring" : "Regular"
            });
        }
    }

    // Category Summary
    private void showSummary() {

        StringBuilder summary = new StringBuilder();

        for (Map.Entry<String, Double> entry :
                manager.categorySummary().entrySet()) {

            summary.append(entry.getKey())
                   .append(" : Rs. ")
                   .append(entry.getValue())
                   .append("\n");
        }

        JOptionPane.showMessageDialog(
            this,
            summary.length() == 0 ? "No expenses found." : summary.toString(),
            "Category-wise Summary",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    // Monthly Report
    private void monthlyReport() {

        JTextField year = new JTextField(
            String.valueOf(LocalDate.now().getYear())
        );

        JTextField month = new JTextField(
            String.valueOf(LocalDate.now().getMonthValue())
        );

        JPanel form = new JPanel(new GridLayout(2, 2, 8, 8));

        form.add(new JLabel("Year:"));
        form.add(year);

        form.add(new JLabel("Month (1-12):"));
        form.add(month);

        int result = JOptionPane.showConfirmDialog(
            this, form, "Monthly Expense Report",
            JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {

            try {

                int y = Integer.parseInt(year.getText());
                int m = Integer.parseInt(month.getText());

                List<Expense> expenses = manager.monthlyReport(y, m);

                double total = 0;

                for (Expense e : expenses) {
                    total += e.getAmount();
                }

                JOptionPane.showMessageDialog(
                    this,
                    "Number of expenses: " + expenses.size() +
                    "\nTotal monthly expenses: Rs. " + total,
                    "Monthly Report",
                    JOptionPane.INFORMATION_MESSAGE
                );

            } catch (Exception e) {

                JOptionPane.showMessageDialog(
                    this, "Please enter a valid year and month."
                );
            }
        }
    }

    // Save Expenses
    private void saveExpenses() {

        fileHandler.saveExpenses(manager);

        JOptionPane.showMessageDialog(
            this, "Expenses saved successfully!"
        );
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            ExpenseTrackerGUI gui = new ExpenseTrackerGUI();
            gui.setVisible(true);
        });
    }
}
