package com.example.demonn;

import javafx.collections.FXCollections;
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
    private ComboBox<String> attrac; // Комбобокс для выбора достопримечательности

    @FXML
    private DatePicker date; // Поле для выбора даты экскурсии

    @FXML
    private Text notify; // Текстовое поле для уведомлений об ошибках

    @FXML
    private ComboBox<String> user; // Комбобокс для выбора пользователя

    DB db = null; // Объект для работы с базой данных

    // Метод инициализации, вызывается при загрузке FXML
    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {
        db = new DB(); // Инициализация объекта базы данных

        // Получение списка пользователей из базы данных и добавление их в комбобокс
        ResultSet res = db.get_users();
        ObservableList<String> list = FXCollections.observableArrayList(); // Создание списка для пользователей
        while (res.next()) {
            list.add(res.getString(3)); // Добавление логина пользователя в список
        }
        user.setItems(list); // Установка списка пользователей в комбобокс

        // Получение списка достопримечательностей из базы данных и добавление их в комбобокс
        res = db.get_attracts();
        ObservableList<String> list1 = FXCollections.observableArrayList(); // Создание списка для достопримечательностей
        while (res.next()) {
            list1.add(res.getString(2)); // Добавление названия достопримечательности в список
        }
        attrac.setItems(list1); // Установка списка достопримечательностей в комбобокс
    }

    // Метод для добавления заказа на экскурсию
    @FXML
    void add_order(ActionEvent event) throws SQLException, ClassNotFoundException {
        notify.setText(""); // Очистка текстового поля уведомлений

        db = new DB(); // Инициализация объекта базы данных

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Формат даты
        LocalDate formattedDate = LocalDate.parse(date.getValue().format(dateFormatter)); // Преобразование выбранной даты в LocalDate

        // Проверка успешности создания заказа в базе данных
        if (!db.create_order(user.getValue(), attrac.getValue(), formattedDate)) {
            notify.setText("Ошибка"); // Уведомление об ошибке при создании заказа
        } else {
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close(); // Закрытие окна при успешном создании заказа
        }
    }
}