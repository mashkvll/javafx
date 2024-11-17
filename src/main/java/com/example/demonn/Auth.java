package com.example.demonn;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import static com.example.demonn.DB.*;

public class Auth {

    @FXML
    private TextField login_field;

    @FXML
    private TextField password_field;

    DB db = null;

    @FXML
    void log_in(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        db = new DB();
        if (db.check_user(login_field.getText(), password_field.getText())){
            to_list(event);
        } else {
            System.out.println("error");
        }
    }

    @FXML
    void log_in_as_guest(MouseEvent event) throws IOException {
        db = new DB();
        db.set_guest();
        to_list(event);
    }

    private void to_list(ActionEvent event) throws IOException {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("list.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    private void to_list(MouseEvent event) throws IOException {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("list.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }


}
