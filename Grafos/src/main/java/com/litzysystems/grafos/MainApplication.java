package com.litzysystems.grafos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        URL fxml = getClass().getResource(
                "/com/litzysystems/grafos/graph-view.fxml"
        );

        if (fxml == null) {
            throw new IllegalStateException(
                    "No se encontro graph-view.fxml. Revisa la ruta del archivo."
            );
        }

        FXMLLoader loader = new FXMLLoader(fxml);
        Parent root = loader.load();

        Scene scene = new Scene(root, 2000, 1000);

        stage.setTitle("Graph Data Structures");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}