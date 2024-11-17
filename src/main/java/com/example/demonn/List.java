package com.example.demonn;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static com.example.demonn.DB.current_user_role;

public class List {
    DB db = null; // Объект для работы с базой данных

    @FXML
    private VBox vboxMain; // Основной контейнер для размещения достопримечательностей
    @FXML
    private Button button_to_reviews; // Кнопка для перехода к отзывам
    @FXML
    private Button button_add_review; // Кнопка для добавления отзыва

    // Метод инициализации, вызывается при загрузке FXML
    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {
        // Скрытие кнопок, если текущая роль пользователя - Гость
        if (Objects.equals(current_user_role, "Гость")) {
            button_to_reviews.setVisible(false);
            button_add_review.setVisible(false);
        }
        fill_list(); // Заполнение списка достопримечательностей
    }

    // Метод для заполнения списка достопримечательностей из базы данных
    private void fill_list() throws SQLException, ClassNotFoundException {
        vboxMain.getChildren().clear(); // Очистка предыдущих элементов в контейнере
        db = new DB(); // Инициализация объекта базы данных
        ResultSet attracts = db.get_attracts(); // Получение достопримечательностей из базы данных

        // Цикл по всем полученным достопримечательностям
        while (attracts.next()) {
            HBox hbox = new HBox(); // Создание горизонтального контейнера для одной достопримечательности
            hbox.setPadding(new Insets(10, 10, 10, 5)); // Установка отступов внутри контейнера
            hbox.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT))); // Установка границы
            hbox.setSpacing(20); // Установка расстояния между элементами в контейнере

            hbox.setId(String.valueOf(attracts.getInt(1))); // Установка ID достопримечательности как идентификатор контейнера

            // Загрузка изображения достопримечательности
            ImageView image = new ImageView(new Image("file:src/main/resources/com/example/demonn/" + attracts.getString(3)));
            image.setFitHeight(150); // Установка высоты изображения
            image.setFitWidth(150); // Установка ширины изображения

            VBox vbox = new VBox(); // Создание вертикального контейнера для текста информации о достопримечательности
            vbox.setSpacing(10); // Установка расстояния между элементами в вертикальном контейнере

            Text name = new Text(attracts.getString(2)); // Название достопримечательности
            name.setFont(Font.font(20)); // Установка шрифта для названия

            Text category = new Text("Категория: " + db.get_category(attracts.getInt(1))); // Получение категории и создание текста
            category.setFont(Font.font(16)); // Установка шрифта для категории

            Text description = new Text(attracts.getString(4)); // Описание достопримечательности
            description.setWrappingWidth(300); // Установка ширины обертки текста для описания

            name.setWrappingWidth(300); // Установка ширины обертки текста для названия

            VBox vbox1 = new VBox(); // Создание дополнительного вертикального контейнера для лайков и дизлайков
            HBox hbox1 = new HBox(); // Создание горизонтального контейнера для отображения лайков и дизлайков

            Text likes = new Text("☝" + attracts.getInt(5)); // Текст с количеством лайков
            likes.setFont(Font.font(30)); // Установка шрифта для лайков

            Text dislikes = new Text("☟" + attracts.getInt(6)); // Текст с количеством дизлайков
            dislikes.setFont(Font.font(30)); // Установка шрифта для дизлайков

            // Обработчик клика по тексту лайков
            likes.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    String id = (((VBox) ((HBox) ((Text) mouseEvent.getSource()).getParent()).getParent()).getParent()).getId();
                    try {
                        db.setlikes(1, Integer.parseInt(id)); // Увеличение количества лайков в базе данных
                    } catch (ClassNotFoundException | SQLException e) {
                        throw new RuntimeException(e); // Обработка исключений при работе с базой данных
                    }
                    try {
                        ((Text) mouseEvent.getSource()).setText("☝" + db.getlikes(Integer.parseInt(id))); // Обновление текста с количеством лайков после клика
                    } catch (ClassNotFoundException | SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            // Обработчик клика по тексту дизлайков
            dislikes.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    String id = (((VBox) ((HBox) ((Text) mouseEvent.getSource()).getParent()).getParent()).getParent()).getId();
                    try {
                        db.setdislikes(1, Integer.parseInt(id)); // Увеличение количества дизлайков в базе данных
                    } catch (ClassNotFoundException | SQLException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        ((Text) mouseEvent.getSource()).setText("☟" + db.getdislikes(Integer.parseInt(id))); // Обновление текста с количеством дизлайков после клика
                    } catch (ClassNotFoundException | SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            vboxMain.getChildren().add(hbox); // Добавление контейнера с достопримечательностью в основной VBox

            hbox.getChildren().addAll(image, vbox); // Добавление изображения и текстового контейнера в горизонтальный контейнер

            vbox.getChildren().addAll(name, category, description); // Добавление названия, категории и описания в вертикальный контейнер

            hbox.getChildren().add(vbox1); // Добавление дополнительного вертикального контейнера в горизонтальный контейнер

            hbox1.getChildren().addAll(likes, dislikes); // Добавление текста лайков и дизлайков в горизонтальный контейнер

            vbox1.getChildren().add(hbox1); // Добавление горизонтального контейнера с лайками и дизлайками в дополнительный вертикальный контейнер
        }
    }

    // Метод для перехода к экрану отзывов при нажатии на кнопку
    public void to_reviews(ActionEvent event) throws IOException {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close(); // Закрытие текущего окна

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("reviews.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    // Метод для открытия окна добавления отзыва при нажатии на кнопку
    public void add_review(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add_review.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);

        stage.initModality(Modality.WINDOW_MODAL);  // Установка модальности окна (блокирует взаимодействие с родительским окном)
        stage.initOwner(((Node) event.getSource()).getScene().getWindow());  // Установка родительского окна

        stage.show();  // Отображение нового модального окна добавления отзыва
    }
}