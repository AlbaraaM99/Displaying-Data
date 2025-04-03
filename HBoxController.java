package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HBoxController {

    @FXML private Label idLabel;
    @FXML private Label nameLabel;
    @FXML private Label emailLabel;
    @FXML private Button nextButton;
    @FXML private Button prevButton;
    @FXML private Button switchButton;

    private List<String[]> personData;
    private int currentIndex = 0;

    private void fetchPersonData() {
        personData = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id, name, email FROM persons");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String[] row = new String[3];
                row[0] = rs.getString("id");
                row[1] = rs.getString("name");
                row[2] = rs.getString("email");
                personData.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayRecord(int index) {
        if (personData != null && !personData.isEmpty() && index >= 0 && index < personData.size()) {
            String[] record = personData.get(index);
            idLabel.setText(record[0]);
            nameLabel.setText(record[1]);
            emailLabel.setText(record[2]);
        }
    }

    @FXML
    public void initialize() {
        fetchPersonData();
        displayRecord(currentIndex);
    }

    @FXML
    void nextRecord(ActionEvent event) {
        if (currentIndex < personData.size() - 1) {
            currentIndex++;
            displayRecord(currentIndex);
        }
    }

    @FXML
    void prevRecord(ActionEvent event) {
        if (currentIndex > 0) {
            currentIndex--;
            displayRecord(currentIndex);
        }
    }

    @FXML
    void switchGridScene(ActionEvent event) throws IOException {
        App.setRoot("gridscene");
    }
}
