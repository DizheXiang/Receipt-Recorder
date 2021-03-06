package persistence;

import model.ReceiptRecorder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

import javax.swing.*;


// citation URL: https://github.com/stleary/JSON-java.git
// Represents a reader that reads receiptRecorder from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads receiptRecorder from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ReceiptRecorder read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseReceiptRecorder(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses receiptRecorder from JSON object and returns it
    private ReceiptRecorder parseReceiptRecorder(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        ReceiptRecorder receiptRecorder = new ReceiptRecorder(name);
        double budget = jsonObject.getDouble("budget");
        receiptRecorder.setBudget(budget);
        addReceipts(receiptRecorder, jsonObject);
        return receiptRecorder;
    }

    // MODIFIES: receiptRecorder
    // EFFECTS: parses thingies from JSON object and adds them to receiptRecorder
    private void addReceipts(ReceiptRecorder receiptRecorder, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("receipts");
        for (Object json : jsonArray) {
            JSONObject nextReceipt = (JSONObject) json;
            addReceipt(receiptRecorder, nextReceipt);
        }
    }

    // MODIFIES: receiptRecorder
    // EFFECTS: parses thingy from JSON object and adds it to receiptRecorder
    private void addReceipt(ReceiptRecorder receiptRecorder, JSONObject jsonObject) {
        double amount = jsonObject.getDouble("amount");
        String item = jsonObject.getString("item");
        receiptRecorder.addReceipt(amount, item);
    }
}
