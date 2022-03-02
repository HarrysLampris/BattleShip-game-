package practice;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import practice.BattleshipMain;



public class ConfirmBox extends BattleshipMain {

    static boolean answer;

    public static boolean display(String title, String message){
        Stage window = new Stage();
        window.setTitle(title);
        window.setMinHeight(100);
        window.setMinWidth(300);
        Label label = new Label();
        label.setText(message);

        Button OKButton = new Button("OK");

        OKButton.setOnAction(event -> {
            answer = true;
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,OKButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}