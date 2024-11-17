package com.example.demonn;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static com.example.demonn.DB.current_user_role;

public class Reviews {
    @FXML
    private Button add_order_btn; // Кнопка для добавления заказа
    DB db = null; // Объект для работы с базой данных

    @FXML
    private VBox vboxmain; // Основной контейнер для размещения отзывов

    // Метод инициализации, вызывается при загрузке FXML
    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {
        set_reviews(); // Установка отзывов в интерфейсе
        // Скрытие кнопки добавления заказа, если текущая роль не администратор
        if (!Objects.equals(current_user_role, "Администратор")) {
            add_order_btn.setVisible(false);
        }
    }

    // Метод для получения и отображения отзывов из базы данных
    private void set_reviews() throws SQLException, ClassNotFoundException {
        db = new DB(); // Инициализация объекта базы данных
        ResultSet reviews = db.get_reviews(); // Получение отзывов из базы данных

        // Цикл по всем полученным отзывам
        while (reviews.next()) {
            HBox hbox = new HBox(); // Создание горизонтального контейнера для отзыва
            Text review = new Text(reviews.getInt(1) + ".  " + reviews.getString(2)); // Создание текста отзыва с номером
            review.setWrappingWidth(550); // Установка ширины обертки текста
            review.setFont(Font.font(15)); // Установка шрифта текста

            // Настройка внешнего вида контейнера отзыва
            hbox.setPadding(new Insets(10, 10, 10, 5)); // Установка отступов внутри контейнера
            hbox.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT))); // Установка границы

            vboxmain.getChildren().add(hbox); // Добавление контейнера отзыва в основной VBox
            hbox.getChildren().add(review); // Добавление текста отзыва в контейнер
        }
    }

    // Метод для перехода к списку (закрывает текущее окно и открывает новое)
    public void to_list(ActionEvent event) throws IOException {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close(); // Закрытие текущего окна
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("list.fxml")); // Загрузка нового FXML файла
        Scene scene = new Scene(fxmlLoader.load()); // Создание новой сцены из загруженного FXML
        Stage stage = new Stage(); // Создание нового окна (Stage)
        stage.setResizable(false); // Запрет изменения размера окна
        stage.setScene(scene); // Установка новой сцены в окно
        stage.show(); // Отображение нового окна
    }

    // Метод для открытия окна добавления заказа
    public void add_order(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add_order.fxml")); // Загрузка FXML для добавления заказа
        Scene scene = new Scene(fxmlLoader.load()); // Создание новой сцены из загруженного FXML
        Stage stage = new Stage(); // Создание нового окна (Stage)
        stage.setResizable(false); // Запрет изменения размера окна
        stage.setScene(scene); // Установка новой сцены в окно

        stage.initModality(Modality.WINDOW_MODAL); // Установка модальности окна (блокирует взаимодействие с родительским окном)
        stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow()); // Установка родительского окна для модального окна

        stage.show(); // Отображение нового модального окна
    }
}