package com.example;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class VBoxController {

    @FXML private Label nameLabel;
    @FXML private Label cityLabel;
    @FXML private Label zipcodeLabel;

    @FXML private Button nextButton;
    @FXML private Button prevButton;
    @FXML private Button switchButton;

    private List<String[]> personData = new ArrayList<>();
    private int currentIndex = 0;

    @FXML
    public void initialize() {
        personData = fetchPersonData();
        if (!personData.isEmpty()) {
            currentIndex = 0;
            displayRecord(currentIndex);
        }
    }

    private List<String[]> fetchPersonData() {
        List<String[]> data = new ArrayList<>();
        String query = "SELECT name, city, zipcode FROM person";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("name");
                String city = rs.getString("city");
                String zipcode = rs.getString("zipcode");
                data.add(new String[]{name, city, zipcode});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    private void displayRecord(int index) {
        if (index >= 0 && index < personData.size()) {
            String[] person = personData.get(index);
            nameLabel.setText("Name: " + person[0]);
            cityLabel.setText("City: " + person[1]);
            zipcodeLabel.setText("Zipcode: " + person[2]);
        }
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
    void switchHScene(ActionEvent event) throws IOException {
        App.setRoot("hboxscene");
    }
}
