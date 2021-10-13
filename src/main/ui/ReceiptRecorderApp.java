package ui;

import model.Budget;
import model.ReceiptRecorder;

import java.util.Scanner;

// Receipt recorder application
public class ReceiptRecorderApp {

    private ReceiptRecorder receiptRecorder;
    private Budget budget;
    private Scanner input;
    private static final String selectYourOperation =
            "You want to: \n"
            + "(1) Add a new receipt \n"
            + "(2) Change an existed receipt \n"
            + "(3) Delete an existed receipt \n"
            + "(4) Add a new budget \n"
            + "(5) Change an existed budget \n"
            + "(6) Delete an existed budget \n"
            + "(7) See your expenses in a day \n"
            + "(8) Check whether exceed your budget"
            + "(9) Save and quit recorder";

    // EFFECTS: runs the receipt recorder application
    public ReceiptRecorderApp() {
        runReceiptRecorder();
    }

    // MODIFIES: this
    // EFFECTS: processes user's command or quit app
    private void runReceiptRecorder() {
        boolean keepRunning = true;
        String operation = null;

        while (keepRunning) {
            System.out.println(selectYourOperation);
            operation = input.nextLine();

            if (operation.equals("9")) {
                keepRunning = false;
            } else {
                processOperation(operation);
            }
        }
        System.out.println("\nHave a nice day, goodbye!");
    }

    // EFFECTS: go to different operations based on user's choice
    private void processOperation(String operation) {
        if (operation == "1") {
            createNewReceipt();
        } else if (operation == "2") {
            changeReceipt();
        } else if (operation == "3") {
            deleteReceipt();
        } else if (operation == "4") {
            addBudget();
        } else if (operation == "5") {
            changeBudget();
        } else if (operation == "6") {
            deleteBudget();
        } else if (operation == "7") {
            checkExpenses();
        } else if (operation == "8") {
            checkBudget();
        }
    }

    // MODIFIES: this
    // EFFECTS: create a new receipt
    private void createNewReceipt() {
        System.out.println("Enter your new receipt in $: ");
        double amount = input.nextDouble();
        System.out.println("What did you buy: ");
        String item = input.nextLine();
        System.out.println("What is the date(MM/DD/YYYY): ");
        String date = input.nextLine();
        receiptRecorder.addReceipt(amount,item,date);
    }

    // MODIFIES: this
    // EFFECTS: change an existed receipt
    private void changeReceipt() {
        System.out.println("Enter your receipt date(MM/DD/YYYY): ");
        String date = input.nextLine();
        System.out.println("Enter what did you buy: ");
        String item = input.nextLine();
        receiptRecorder.findReceipt(date, item);

        System.out.println("What do you want to change? amount/item/date/nothing");
        while (input.nextLine() != "nothing") {
            makeChangeForReceipt();
        }
    }

    // MODIFIES: this
    // EFFECTS: delete a existed receipt
    private void deleteReceipt() {
        System.out.println("Enter the date of receipt you want to delete(MM/DD/YYYY)");
        String date = input.nextLine();
        System.out.println("Enter the item of receipt you want to delete");
        String item = input.nextLine();
        receiptRecorder.findReceipt(date, item);
        receiptRecorder.removeReceipt();
    }

    // MODIFIES: this
    // EFFECTS: create a budget
    private void addBudget() {
        System.out.println("Enter your preferred budget amount");
        String budgetAmount = input.nextLine();
        System.out.println("Enter the start date of budget(MM/DD/YYYY)");
        String startDate = input.nextLine();
        System.out.println("Enter the end date of budget(MM/DD/YYYY)");
        String endDate = input.nextLine();
        budget.addBudget(budgetAmount,startDate,endDate);
    }

    // MODIFIES: this
    // EFFECTS: change the budget
    private void changeBudget() {
        System.out.println("Enter what do you want to change? amount/item/start date/end date/nothing");
        while (input.nextLine() != "nothing") {
            makeChangeForBudget();
        }
    }

    // MODIFIES: this
    // EFFECTS: delete the budget
    private void deleteBudget() {
        System.out.println("Your budget has been deleted");
        budget.removeBudget();
    }

    // MODIFIES: this
    // EFFECTS: output the total amount of spending in a given time period
    private void checkExpenses() {
        System.out.println("Enter the start date(MM/DD/YYYY): ");
        String startDate = input.nextLine();
        System.out.println("Enter the end date(MM/DD/YYYY): ");
        String endDate = input.nextLine();
        double totalAmount = receiptRecorder.checkExpenses(startDate,endDate);
        System.out.println("Your total expenses amount is " + totalAmount + " dollars");
    }

    // MODIFIES: this
    // EFFECTS: check whether spending exceeds setting budget
    private void checkBudget() {
        budget.checkBudget();
        if (budget.checkBudget()) {
            System.out.println("Great, your spending doesn't exceed your budget");
        } else {
            System.out.println("Be careful, your spending exceeds your budget");
        }
    }

    // MODIFIES: this
    // EFFECTS: update the information about chosen receipt
    private void makeChangeForReceipt() {
        if (input.nextLine() == "amount") {
            double newAmount = input.nextDouble();
            receiptRecorder.changeAmount(newAmount);
        } else if (input.nextLine() == "item") {
            String newItem = input.nextLine();
            receiptRecorder.changeItem(newItem);
        } else if (input.nextLine() == "date") {
            String newDate = input.nextLine();
            receiptRecorder.changeDate(newDate);
        }
    }

    // MODIFIES: this
    // EFFECTS: update the information about budget
    private void makeChangeForBudget() {
        if (input.nextLine() == "amount") {
            double newAmount = input.nextDouble();
            budget.changeAmount(newAmount);
        } else if (input.nextLine() == "item") {
            String newItem = input.nextLine();
            budget.changeItem(newItem);
        } else if (input.nextLine() == "start date") {
            String newStartDate = input.nextLine();
            budget.changeStartDate(newStartDate);
        } else if (input.nextLine() == "end date") {
            String newEndDate = input.nextLine();
            budget.changeEndDate(newEndDate);
        }
    }
}
