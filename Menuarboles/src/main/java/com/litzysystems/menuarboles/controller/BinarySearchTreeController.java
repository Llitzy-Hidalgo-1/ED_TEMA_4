
package com.litzysystems.menuarboles.controller;

import com.litzysystems.menuarboles.clase.BinarySearchTree;

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

// Controlador del árbol binario de búsqueda.
public class BinarySearchTreeController {

    private final Stage stage;
    private final BinarySearchTree tree = new BinarySearchTree();
    private final List<Integer> values = new ArrayList<>();

    private final VBox root = UI.page(
            "ÁRBOL BINARIO DE BÚSQUEDA",
            "Los valores menores van a la izquierda y los mayores a la derecha."
    );

    private final TextField input =
            UI.input("Escribe un número entero");

    private final Label status =
            new Label("Inserta un número para comenzar.");

    private final VBox graphic = new VBox();

    public BinarySearchTreeController(Stage stage) {
        this.stage = stage;
    }

    public Parent createView() {
        Button insert = UI.button(" Insertar", UI.BLUE);
        insert.setOnAction(e -> insertNode());

        Button search = UI.button(" Buscar", UI.PURPLE);
        search.setOnAction(e -> searchNode());

        Button inorder = UI.button(" Mostrar en orden", UI.GREEN);
        inorder.setOnAction(e -> showInorder());

        Button clear = UI.button("🗑 Limpiar", UI.RED);
        clear.setOnAction(e -> clearTree());

        Button back = UI.button("⬅ Volver al menú", UI.NAVY);
        back.setOnAction(e -> backToMenu());

        status.setWrapText(true);
        graphic.setMinHeight(300);

        refreshGraphic();

        root.getChildren().addAll(
                input,
                UI.buttonRow(insert, search),
                UI.buttonRow(inorder, clear),
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
    private void searchNode() {
        Integer value = UI.parseInteger(input);

        if (value == null) {
            return;
        }

        if (values.contains(value)) {
            status.setText("El valor " + value + " sí existe en el árbol.");
            UI.info("El valor " + value + " fue encontrado.");
        } else {
            status.setText("El valor " + value + " no existe en el árbol.");
            UI.info("No se encontró el valor " + value + ".");
        }
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
