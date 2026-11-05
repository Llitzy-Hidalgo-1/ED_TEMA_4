
package com.litzysystems.menuarboles.controller;

import com.litzysystems.menuarboles.clase.AVLTree;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

// Controlador del árbol AVL.
public class AVLTreeController {

    private final Stage stage;
    private final AVLTree tree = new AVLTree();
    private final List<Integer> values = new ArrayList<>();

    private final VBox root = UI.page(
            "ÁRBOL AVL",
            "Árbol de búsqueda que se mantiene equilibrado al insertar."
    );

    private final TextField input =
            UI.input("Ingresa un número entero");

    private final Label status =
            new Label("Inserta un número para comenzar.");

    private final VBox graphic = new VBox();

    public AVLTreeController(Stage stage) {
        this.stage = stage;
    }

    public Parent createView() {
        Button insert = UI.button(" Insertar número", UI.GREEN);
        insert.setOnAction(e -> insertNode());

        Button example = UI.button(" Cargar ejemplo", UI.BLUE);
        example.setOnAction(e -> loadExample());

        Button inorder = UI.button(" Recorrido en orden", UI.PURPLE);
        inorder.setOnAction(e -> showInorder());

        Button clear = UI.button("🗑 Limpiar árbol", UI.RED);
        clear.setOnAction(e -> clearTree());

        Button back = UI.button("⬅ Volver al menú", UI.NAVY);
        back.setOnAction(e -> backToMenu());

        status.setWrapText(true);
        graphic.setMinHeight(300);

        refreshGraphic();

        root.getChildren().addAll(
                input,
                UI.buttonRow(insert, example),
                UI.buttonRow(inorder, clear),
                status,
                new Label("Representación gráfica del árbol AVL"),
                graphic,
                back
        );

        return root;
    }

    @FXML
    private void insertNode() {
        Integer value = UI.parseInteger(input);

        if (value == null) {
            return;
        }

        if (values.contains(value)) {
            UI.info("Ese número ya existe en el árbol.");
            return;
        }

        tree.insert(value);
        values.add(value);

        input.clear();
        refreshGraphic();

        status.setText("Se insertó " + value + " en el árbol AVL.");
    }

    private void loadExample() {
        if (!values.isEmpty()) {
            UI.info("Limpia el árbol antes de cargar el ejemplo.");
            return;
        }

        int[] example = {30, 20, 10, 25, 28, 40, 50};

        for (int value : example) {
            tree.insert(value);
            values.add(value);
        }

        refreshGraphic();
        status.setText("Ejemplo AVL cargado correctamente.");
    }

    private void showInorder() {
        UI.info("Recorrido en orden:\n" + tree.inorder());
    }

    @FXML
    private void clearTree() {
        tree.clear();
        values.clear();

        input.clear();
        refreshGraphic();

        status.setText("El árbol AVL está vacío.");
    }

    private void refreshGraphic() {
        graphic.getChildren().setAll(
                UI.treeGraphic(values, "AVL")
        );
    }

    @FXML
    private void backToMenu() {
        MainController controller = new MainController(stage);
        stage.setScene(new Scene(controller.createView(), 900, 650));
        stage.setTitle("Estructuras de Datos - Árboles");
        stage.show();
    }
}
