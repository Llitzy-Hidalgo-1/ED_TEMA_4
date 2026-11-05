
package com.litzysystems.menuarboles.controller;

import com.litzysystems.menuarboles.clase.BinaryTree;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class BinaryTreeController {

    private final Stage stage;
    private final BinaryTree tree = new BinaryTree();
    private final List<Integer> values = new ArrayList<>();

    private final VBox root = UI.page(
            "ÁRBOL BINARIO",
            "Agrega números y visualiza la estructura del árbol."
    );

    private final TextField input = UI.input("Escribe un número entero");
    private final Label status = new Label("Ingresa un número para comenzar.");
    private final VBox graphic = new VBox();
    public TextArea outputArea;

    public BinaryTreeController(Stage stage) {
        this.stage = stage;
    }

    public Parent createView() {
        Button insertButton = UI.button("Insertar número", UI.BLUE);
        Button exampleButton = UI.button("Cargar ejemplo", UI.PURPLE);
        Button showButton = UI.button("Mostrar valores", UI.GREEN);
        Button clearButton = UI.button("Limpiar árbol", UI.RED);
        Button backButton = UI.button("← Volver al menú principal", UI.NAVY);

        insertButton.setOnAction(e -> insertNode());
        exampleButton.setOnAction(e -> loadExample());
        showButton.setOnAction(e -> showValues());
        clearButton.setOnAction(e -> clearTree());
        backButton.setOnAction(e -> backToMenu());

        status.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-text-fill: " + UI.NAVY + ";"
        );

        graphic.setPadding(new Insets(10));
        graphic.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 12px;"
        );

        root.getChildren().addAll(
                input,
                UI.buttonRow(insertButton, exampleButton),
                UI.buttonRow(showButton, clearButton),
                status,
                graphic,
                backButton
        );

        refreshGraphic();
        return root;
    }

    @FXML
    private void insertNode() {
        Integer value = UI.parseInteger(input);

        if (value == null) {
            return;
        }

        tree.insert(value);
        values.add(value);
        input.clear();

        status.setText("Número " + value + " agregado correctamente.");
        refreshGraphic();
    }

    private void loadExample() {
        clearTree();

        int[] example = {50, 30, 70, 20, 40, 60, 80};

        for (int value : example) {
            tree.insert(value);
            values.add(value);
        }

        status.setText("Ejemplo cargado correctamente.");
        refreshGraphic();
    }

    private void showValues() {
        UI.info("Valores ingresados:\n" + values);
    }

    @FXML
    private void clearTree() {
        tree.clear();
        values.clear();
        graphic.getChildren().clear();
        status.setText("El árbol está vacío.");
        refreshGraphic();
    }

    private void refreshGraphic() {
        graphic.getChildren().clear();
        graphic.getChildren().add(
                UI.treeGraphic(values, "BINARY")
        );
    }

    @FXML
    private void backToMenu() {
        MainController controller = new MainController(stage);
        Scene scene = new Scene(controller.createView(), 500, 900);

        stage.setTitle("Menú principal - Estructuras de árboles");
        stage.setScene(scene);
        stage.show();
    }
}
