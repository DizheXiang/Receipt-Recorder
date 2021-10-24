package model;

import model.exceptions.ReceiptDoesNotExistException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Records each receipt and has a budget
public class ReceiptRecorder implements Writable {
    private ArrayList<Receipt> receipts;
    private String name;
    private double budget;

    // EFFECTS: construct a new receipt recorder
    public ReceiptRecorder(String name) {
        receipts = new ArrayList<>();
        this.budget = 0.00;
        this.name = name;
    }

    //EFFECTS: return the name of receipt recorder
    public String getName() {
        return name;
    }

    //EFFECTS: return the receipts inside receipt recorder
    public ArrayList<Receipt> getReceipts() {
        return receipts;
    }

    // MODIFIED: this
    // EFFECTS: add a receipt to the receipt recorder
    public void addReceipt(double amount, String item) {
        Receipt receipt = new Receipt(amount, item);
        receipts.add(receipt);
    }

    // EFFECTS: find the receipt with given item
    public Receipt findReceipt(String item) throws ReceiptDoesNotExistException {
        Receipt trueReceipt = null;
        for (Receipt receipt : receipts) {
            if (receipt.getItem().equals(item)) {
                trueReceipt = receipt;
            }
        }
        if (trueReceipt == null) {
            throw new ReceiptDoesNotExistException();
        }
        return trueReceipt;
    }

    // EFFECTS: produce the total spending amount
    public double checkExpenses() {
        double totalAmount = 0.00;
        for (Receipt receipt : receipts) {
            totalAmount += receipt.getAmount();
        }
        return totalAmount;
    }

    // EFFECTS: check whether total spending exceeds the budget
    public boolean checkBudget() {
        double spending = checkExpenses();
        return spending > budget;
    }

    // MODIFIES: this
    // EFFECTS: remove the receipt with given item
    public void removeReceipt(String item) {
        try {
            receipts.remove(findReceipt(item));
        } catch (ReceiptDoesNotExistException e) {
            System.out.println("Sorry, the receipt doesn't exist");
        }
    }

    // MODIFIES: this
    // EFFECTS: set the budget amount
    public double getBudget() {
        return budget;
    }

    // MODIFIES: this
    // EFFECTS: set the budget amount
    public void setBudget(double newAmount) {
        this.budget = newAmount;
    }

    // EFFECTS: return the size of receiptRecorder
    public int size() {
        return receipts.size();
    }

    public void showAllReceipt() {
        int number = 0;
        for (Receipt receipt : receipts) {
            number++;
            System.out.println("receipt" + number + ": \n amount "
                    + receipt.getAmount() + "\n item " + receipt.getItem());
        }
        showBudget();
    }

    public void showBudget() {
        System.out.println("budget: " +  getBudget() + "\n");
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("budget", budget);
        json.put("receipts", receiptsToJson());
        return json;
    }

    // EFFECTS: returns things in this receiptRecorder as a JSON array
    private JSONArray receiptsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Receipt r : receipts) {
            jsonArray.put(r.toJson());
        }

        return jsonArray;
    }


}
