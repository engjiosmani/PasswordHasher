package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class app extends Application {

    @Override
    public void start(Stage stage) throws Exception {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signup.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("JavaFX Application");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {

        launch(args);
    }
}
