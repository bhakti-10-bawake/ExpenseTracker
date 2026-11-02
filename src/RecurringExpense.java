
import java.time.LocalDate;

public class RecurringExpense extends Expense {

    private String frequency;

    public RecurringExpense(int id, String title, double amount,
                            String category, LocalDate date,
                            String frequency) {

        super(id, title, amount, category, date);
        this.frequency = frequency;
    }

    public String getFrequency() {
        return frequency;
    }

    @Override
    public String toString() {
        return super.toString() +
               " | Frequency: " + frequency;
    }
}
