
package com.litzysystems.menuarboles.controller;

import com.litzysystems.menuarboles.clase.TraversalTree;

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

// Controlador de los recorridos de un árbol.
public class TraversalTreeController {

    private final Stage stage;
    private final TraversalTree tree = new TraversalTree();
    private final List<Integer> values = new ArrayList<>();

    private final VBox root = UI.page(
            "RECORRIDOS DEL ÁRBOL",
            "Inserta valores y compara los distintos recorridos."
    );

    private final TextField input =
            UI.input("Escribe un número entero");

    private final Label status =
            new Label("Inserta números para comenzar.");

    private final VBox graphic = new VBox();

    public TraversalTreeController(Stage stage) {
        this.stage = stage;
    }

    public Parent createView() {
        Button insert = UI.button(" Insertar", UI.BLUE);
        insert.setOnAction(e -> insertNode());

        Button preorder = UI.button("Preorden", UI.PURPLE);
        preorder.setOnAction(e -> showPreorder());

        Button inorder = UI.button("Inorden", UI.GREEN);
        inorder.setOnAction(e -> showInorder());

        Button postorder = UI.button("Postorden", UI.CYAN);
        postorder.setOnAction(e -> showPostorder());

        Button example = UI.button(" Cargar ejemplo", UI.ORANGE);
        example.setOnAction(e -> loadExample());

        Button clear = UI.button("🗑 Limpiar", UI.RED);
        clear.setOnAction(e -> clearTree());

        Button back = UI.button("⬅ Volver al menú", UI.NAVY);
        back.setOnAction(e -> backToMenu());

        status.setWrapText(true);
        graphic.setMinHeight(300);

        refreshGraphic();

        root.getChildren().addAll(
                input,
                UI.buttonRow(insert, example),
                UI.buttonRow(preorder, inorder, postorder),
                UI.buttonRow(clear),
                status,
                new Label("Representación gráfica"),
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

        status.setText("Se insertó el valor: " + value);
    }

    @FXML
    private void showPreorder() {
        if (values.isEmpty()) {
            UI.info("Primero inserta valores en el árbol.");
            return;
        }

        UI.info("Recorrido preorden:\n" + tree.preorder());
    }

    @FXML
    private void showInorder() {
        if (values.isEmpty()) {
            UI.info("Primero inserta valores en el árbol.");
            return;
        }

        UI.info("Recorrido inorden:\n" + tree.inorder());
    }

    @FXML
    private void showPostorder() {
        if (values.isEmpty()) {
            UI.info("Primero inserta valores en el árbol.");
            return;
        }

        UI.info("Recorrido postorden:\n" + tree.postorder());
    }

    @FXML
    private void loadExample() {
        if (!values.isEmpty()) {
            UI.info("Limpia el árbol antes de cargar el ejemplo.");
            return;
        }

        int[] example = {50, 30, 70, 20, 40, 60, 80};

        for (int value : example) {
            tree.insert(value);
            values.add(value);
        }

        refreshGraphic();
        status.setText("Ejemplo cargado correctamente.");
    }

    @FXML
    private void clearTree() {
        tree.clear();
        values.clear();

        input.clear();
        refreshGraphic();

        status.setText("El árbol está vacío.");
    }

    private void refreshGraphic() {
        graphic.getChildren().setAll(
                UI.treeGraphic(values, "BST")
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
