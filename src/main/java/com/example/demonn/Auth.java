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
    private TextField login_field; // Поле для ввода логина

    @FXML
    private TextField password_field; // Поле для ввода пароля

    DB db = null; // Объект для работы с базой данных

    // Метод для обработки события входа в систему
    @FXML
    void log_in(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        db = new DB(); // Инициализация объекта базы данных
        // Проверка пользователя по введённым логину и паролю
        if (db.check_user(login_field.getText(), password_field.getText())) {
            to_list(event); // Переход к списку, если вход успешен
        } else {
            System.out.println("error"); // Вывод сообщения об ошибке в консоль
        }
    }

    // Метод для обработки события входа как гость
    @FXML
    void log_in_as_guest(MouseEvent event) throws IOException {
        db = new DB(); // Инициализация объекта базы данных
        db.set_guest(); // Установка текущего пользователя как гостя
        to_list(event); // Переход к списку достопримечательностей
    }

    // Метод для перехода к экрану списка достопримечательностей при входе
    private void to_list(ActionEvent event) throws IOException {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close(); // Закрытие текущего окна

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("list.fxml")); // Загрузка FXML для списка
        Scene scene = new Scene(fxmlLoader.load()); // Создание новой сцены из загруженного FXML
        Stage stage = new Stage(); // Создание нового окна (Stage)
        stage.setResizable(false); // Запрет изменения размера окна
        stage.setScene(scene); // Установка новой сцены в окно
        stage.show(); // Отображение нового окна
    }

    // Перегруженный метод для перехода к экрану списка достопримечательностей при входе как гость
    private void to_list(MouseEvent event) throws IOException {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close(); // Закрытие текущего окна

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("list.fxml")); // Загрузка FXML для списка
        Scene scene = new Scene(fxmlLoader.load()); // Создание новой сцены из загруженного FXML
        Stage stage = new Stage(); // Создание нового окна (Stage)
        stage.setResizable(false); // Запрет изменения размера окна
        stage.setScene(scene); // Установка новой сцены в окно
        stage.show(); // Отображение нового окна
    }
}