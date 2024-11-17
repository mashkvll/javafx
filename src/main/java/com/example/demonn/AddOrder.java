package com.example.demonn;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddOrder {

    @FXML
    private ComboBox<String> attrac;

    @FXML
    private DatePicker date;

    @FXML
    private Text notify;

    @FXML
    private ComboBox<String> user;

    DB db = null;
    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {
        db = new DB();
        ResultSet res = db.get_users();
        ObservableList<String> list = FXCollections.observableArrayList();
        while (res.next()){
            list.add(res.getString(3));
        }
        user.setItems(list);

        res = db.get_attracts();
        ObservableList<String> list1 = FXCollections.observableArrayList();
        while (res.next()){
            list1.add(res.getString(2));
        }
        attrac.setItems(list1);
    }


    @FXML
    void add_order(ActionEvent event) throws SQLException, ClassNotFoundException {
        notify.setText("");
        db = new DB();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate formattedDate = LocalDate.parse(date.getValue().format(dateFormatter));

        if (!db.create_order(user.getValue(), attrac.getValue(), formattedDate)){
            notify.setText("Ошибка");
        } else {
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        }


    }

}
