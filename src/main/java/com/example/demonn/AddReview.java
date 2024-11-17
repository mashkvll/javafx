package com.example.demonn;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddReview {
    DB db = null;
    @FXML
    private TextArea reviewTextArea;

    public void add_review(ActionEvent event) throws SQLException, ClassNotFoundException {
        db = new DB();
        if (reviewTextArea.getText().length() < 10) {
            System.out.println("Короткий отзыв");
        } else {
            db.add_review(reviewTextArea.getText());
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        }
    }
}
