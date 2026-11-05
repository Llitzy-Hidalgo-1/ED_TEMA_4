
package com.litzysystems.menuarboles.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Controlador del menú principal.
public class MainController {

    private final Stage stage;

    public MainController(Stage stage) {
        this.stage = stage;
    }

    // Construye el menú principal.
    public Parent createView() {
        VBox root = UI.page(
                "ESTRUCTURAS DE ÁRBOLES",
                "Selecciona una estructura para explorar sus operaciones."
        );

        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);

        Label description = new Label(
                "Aprende a insertar, visualizar y recorrer árboles."
        );
        description.setWrapText(true);

        Button binary = UI.button(
                "Árbol binario",
                UI.BLUE
        );
        binary.setOnAction(e -> openBinaryTree());

        Button search = UI.button(
                "Árbol binario de búsqueda",
                UI.GREEN
        );
        search.setOnAction(e -> openBinarySearchTree());

        Button avl = UI.button(
                "Árbol AVL",
                UI.PURPLE
        );
        avl.setOnAction(e -> openAVLTree());

        Button general = UI.button(
                "Árbol general",
                UI.CYAN
        );
        general.setOnAction(e -> openGeneralTree());

        Button traversal = UI.button(
                "Recorridos del árbol",
                UI.ORANGE
        );
        traversal.setOnAction(e -> openTraversalTree());

        Button exit = UI.button(
                "Salir",
                UI.RED
        );
        exit.setOnAction(e -> Platform.exit());

        root.getChildren().addAll(
                description,
                binary,
                search,
                avl,
                general,
                traversal,
                exit
        );

        root.setMaxWidth(Double.MAX_VALUE);

        return root;
    }

    // Abre una pantalla en la ventana principal.
    private void open(Parent view, String title) {
        Scene scene = new Scene(view, 900, 650);

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void openBinaryTree() {
        BinaryTreeController controller =
                new BinaryTreeController(stage);

        open(controller.createView(), "Árbol binario");
    }

    @FXML
    private void openBinarySearchTree() {
        BinarySearchTreeController controller =
                new BinarySearchTreeController(stage);

        open(controller.createView(), "Árbol binario de búsqueda");
    }

    @FXML
    private void openAVLTree() {
        AVLTreeController controller =
                new AVLTreeController(stage);

        open(controller.createView(), "Árbol AVL");
    }

    @FXML
    private void openGeneralTree() {
        GeneralTreeController controller =
                new GeneralTreeController(stage);

        open(controller.createView(), "Árbol general");
    }

    @FXML
    private void openTraversalTree() {
        TraversalTreeController controller =
                new TraversalTreeController(stage);

        open(controller.createView(), "Recorridos del árbol");
    }

    public void exitApplication(ActionEvent actionEvent) {
    }
}
