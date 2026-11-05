
package com.litzysystems.menuarboles;

import com.litzysystems.menuarboles.controller.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) {

        // Crear el controlador del menú principal
        MainController controller = new MainController(stage);

        // Crear la escena con la vista del menú
        Scene scene = new Scene(controller.createView(), 850, 600);

        // Configurar la ventana principal
        stage.setTitle("Estructuras de Datos - Árboles");
        stage.setMinWidth(700);
        stage.setMinHeight(500);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
