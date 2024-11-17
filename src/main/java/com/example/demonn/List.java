package com.example.demonn;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static com.example.demonn.DB.current_user_role;

public class List {
    DB db = null;

    @FXML
    private VBox vboxMain;
    @FXML
    private Button button_to_reviews;
    @FXML
    private Button button_add_review;

    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {
        if (Objects.equals(current_user_role, "Гость")){
            button_to_reviews.setVisible(false);
            button_add_review.setVisible(false);
        }
        fill_list();
    }


    private void fill_list() throws SQLException, ClassNotFoundException {
        vboxMain.getChildren().clear();
        db = new DB();
        ResultSet attracts = db.get_attracts();
        while (attracts.next()) {
            HBox hbox = new HBox();
            hbox.setPadding(new Insets(10, 10, 10, 5));
            hbox.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            hbox.setSpacing(20);

            hbox.setId(String.valueOf(attracts.getInt(1)));
            ImageView image = new ImageView(new Image("file:src/main/resources/com/example/demonn/"+attracts.getString(3)));
            image.setFitHeight(150);
            image.setFitWidth(150);

            VBox vbox = new VBox();
            vbox.setSpacing(10);
            Text name = new Text(attracts.getString(2));
            name.setFont(Font.font(20));
            Text category = new Text("Категория: "+db.get_category(attracts.getInt(1)));
            category.setFont(Font.font(16));
            Text description = new Text(attracts.getString(4));
            description.setWrappingWidth(300);
            name.setWrappingWidth(300);

            VBox vbox1 = new VBox();
            HBox hbox1 = new HBox();
            Text likes = new Text("☝"+attracts.getInt(5));
            likes.setFont(Font.font(30));
            Text dislikes = new Text("☟"+attracts.getInt(6));
            dislikes.setFont(Font.font(30));
            likes.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    String id = (((VBox) ((HBox) ((Text) mouseEvent.getSource()).getParent()).getParent()).getParent()).getId();
                    try {
                        db.setlikes(1, Integer.parseInt(id));
                    } catch (ClassNotFoundException | SQLException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        ((Text) mouseEvent.getSource()).setText("☝" + db.getlikes(Integer.parseInt(id)));
                    } catch (ClassNotFoundException | SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            dislikes.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    String id = (((VBox) ((HBox) ((Text) mouseEvent.getSource()).getParent()).getParent()).getParent()).getId();
                    try {
                        db.setdislikes(1, Integer.parseInt(id));
                    } catch (ClassNotFoundException | SQLException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        ((Text) mouseEvent.getSource()).setText("☟" + db.getdislikes(Integer.parseInt(id)));
                    } catch (ClassNotFoundException | SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            

            vboxMain.getChildren().add(hbox);
            hbox.getChildren().addAll(image, vbox);
            vbox.getChildren().addAll(name, category, description);
            hbox.getChildren().add(vbox1);
            hbox1.getChildren().addAll(likes, dislikes);
            vbox1.getChildren().add(hbox1);
         
            
        }


    }

    public void to_reviews(ActionEvent event) throws IOException {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("reviews.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void add_review(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add_review.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node)event.getSource()).getScene().getWindow() );
        stage.show();
    }
}
