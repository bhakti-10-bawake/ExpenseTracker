# Expense Tracker System 💰

A Java-based desktop application designed to help users record, manage, track, and analyse their personal expenses. The system supports regular and recurring expenses, expense summaries, monthly reports, and CSV-based data storage.

Developed as a team project using **Core Java, Object-Oriented Programming, Java Swing, Collections, Exception Handling, and File Handling**.

## 📌 Project Overview

The Expense Tracker System provides an easy way to manage daily expenses through a graphical user interface (GUI) and a menu-driven console interface.

Users can add, view, update, delete, search, and sort expenses. The application also calculates total spending and generates category-wise and monthly expense summaries.

## ✨ Features

- **Add Expenses:** Add regular and recurring expenses.
- **View Expenses:** Display all saved expense records.
- **Update Expenses:** Modify existing expense details.
- **Delete Expenses:** Remove selected expense records.
- **Search Expenses:** Search expenses by category.
- **Total Expenses:** Calculate the total amount spent.
- **Category-wise Summary:** View spending grouped by category.
- **Monthly Report:** Generate monthly expense summaries.
- **Sort Expenses:** Sort expenses by amount.
- **CSV File Handling:** Save and load expense records automatically.
- **Graphical User Interface:** Interactive desktop interface developed using Java Swing.
- **Console Interface:** Menu-driven application using Core Java.

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java | Core programming language |
| Java Swing | Graphical User Interface |
| OOP | Encapsulation, inheritance and method overriding |
| ArrayList | Store expense records |
| HashMap | Category-wise summaries |
| Exception Handling | Handle invalid inputs and errors |
| Lambda Expressions | Collection processing and sorting |
| Java I/O | CSV file reading and writing |
| LocalDate | Expense date management |
| VS Code | Development environment |
| Git & GitHub | Version control and project hosting |

## 📂 Project Structure

```text
ExpenseTracker/
│
├── src/
│   ├── Expense.java
│   ├── RecurringExpense.java
│   ├── ExpenseManager.java
│   ├── ExpenseFileHandler.java
│   ├── InvalidExpenseException.java
│   ├── ExpenseTrackerGUI.java
│   └── Main.java
│
├── data/
│   └── expenses.csv
│
├── bin/                  (Generated during compilation)
│
├── .gitignore
└── README.md
```

## ⚙️ Installation and Execution

### Prerequisites

- Java Development Kit (JDK 8 or later)
- Visual Studio Code or another Java IDE
- Java configured in the system PATH

### Step 1: Clone the Repository

```bash
git clone https://github.com/bhakti-10-bawake/ExpenseTracker.git
```

### Step 2: Open the Project

```bash
cd ExpenseTracker
```

### Step 3: Compile the Java Files

```bash
javac -d bin src/*.java
```

### Step 4: Run the GUI Application

```bash
java -cp bin ExpenseTrackerGUI
```

### Run the Console Application

```bash
java -cp bin Main
```

## 💾 Data Storage

The application uses CSV (Comma-Separated Values) file handling to store expense records.

- Expense records are saved in `data/expenses.csv`.
- Existing records are loaded when the application starts.
- The CSV file is created automatically when expenses are saved, if it does not already exist.

## 🧠 Java Concepts Demonstrated

1. **Encapsulation:** Private data members with getters and setters.
2. **Inheritance:** RecurringExpense extends Expense.
3. **Method Overriding:** Specialised `toString()` implementation.
4. **Collections Framework:** ArrayList and HashMap.
5. **Exception Handling:** Custom InvalidExpenseException and try-catch blocks.
6. **Lambda Expressions:** Sorting and processing expense records.
7. **File Handling:** Reading and writing CSV files.
8. **GUI Programming:** Java Swing components, tables, buttons and dialog boxes.

## 👥 Team Members

| PRN | Team Member |
|---|---|
| 2125UCEF1044 | BAWAKE BHAKTI |
| 2125UCEF1039 | APURVA WABALE |
| 2125UCEM1047 | HARSHAL INGALE |
| 2125UCEF1103 | ADITYA PANDE |

## 🎯 Project Outcome

The Expense Tracker System demonstrates the practical application of Java programming concepts through a functional desktop application. It provides expense management, data persistence, reporting, and an interactive user interface.

## 🔮 Future Enhancements

- User authentication and multiple user profiles.
- Graphical charts for expense analysis.
- Monthly budget planning and alerts.
- Database integration.
- PDF report generation.

---

**Project:** Expense Tracker System  
**Domain:** Core Java Programming  
**Team Size:** 4 Members  
**Development Tool:** Visual Studio Code
