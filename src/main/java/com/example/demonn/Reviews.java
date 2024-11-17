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
    private Button add_order_btn;
    DB db = null;

    @FXML
    private VBox vboxmain;


    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {
        set_reviews();
        if (!Objects.equals(current_user_role, "Администратор")){
            add_order_btn.setVisible(false);
        }
    }

    private void set_reviews() throws SQLException, ClassNotFoundException {
        db = new DB();
        ResultSet reviews = db.get_reviews();
        while (reviews.next()) {
            HBox hbox = new HBox();
            Text review = new Text(reviews.getInt(1)+ ".  "+  reviews.getString(2));
            review.setWrappingWidth(550);
            review.setFont(Font.font(15));
            hbox.setPadding(new Insets(10, 10, 10, 5));
            hbox.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

            vboxmain.getChildren().add(hbox);
            hbox.getChildren().add(review);
        }
    }

    public void to_list(ActionEvent event) throws IOException {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("list.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void add_order(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add_order.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node)actionEvent.getSource()).getScene().getWindow() );
        stage.show();
    }
}
