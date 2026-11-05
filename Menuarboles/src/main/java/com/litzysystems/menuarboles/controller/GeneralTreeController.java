
package com.litzysystems.menuarboles.controller;

import com.litzysystems.menuarboles.clase.GeneralTree;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

// Controlador del árbol general.
public class GeneralTreeController {

    private final Stage stage;
    private final GeneralTree tree = new GeneralTree();
    private final List<String> children = new ArrayList<>();

    private final VBox root = UI.page(
            "ÁRBOL GENERAL",
            "Agrega varios hijos al nodo raíz y observa su estructura."
    );

    private final TextField input =
            UI.input("Escribe el nombre de un hijo");

    private final Label status =
            new Label("Estado: agrega el primer hijo.");

    private final VBox graphic = new VBox();

    public GeneralTreeController(Stage stage) {
        this.stage = stage;
    }

    public Parent createView() {
        Button add = UI.button(" Agregar hijo", UI.PURPLE);
        add.setOnAction(e -> addChild());

        Button example = UI.button("Cargar ejemplo", UI.BLUE);
        example.setOnAction(e -> loadExample());

        Button information = UI.button("ℹ Información", UI.GREEN);
        information.setOnAction(e -> showInformation());

        Button clear = UI.button("🗑 Limpiar árbol", UI.RED);
        clear.setOnAction(e -> clearTree());

        Button back = UI.button("⬅ Volver al menú", UI.NAVY);
        back.setOnAction(e -> backToMenu());

        status.setWrapText(true);
        graphic.setMinHeight(300);

        refreshGraphic();

        root.getChildren().addAll(
                input,
                UI.buttonRow(add, example),
                UI.buttonRow(information, clear),
                status,
                new Label("Representación gráfica del árbol"),
                graphic,
                back
        );

        return root;
    }

    @FXML
    private void addChild() {
        String name = input.getText().trim();

        if (name.isEmpty()) {
            showMessage(
                    Alert.AlertType.WARNING,
                    "Campo vacío",
                    "Escribe el nombre del hijo."
            );
            input.requestFocus();
            return;
        }

        try {
            tree.addChild(name);
            children.add(name);

            input.clear();
            refreshGraphic();

            status.setText("Se agregó correctamente: " + name);

        } catch (IllegalArgumentException ex) {
            showMessage(
                    Alert.AlertType.ERROR,
                    "Error",
                    ex.getMessage()
            );
        }
    }

    @FXML
    private void loadExample() {
        if (!children.isEmpty()) {
            UI.info("Limpia el árbol antes de cargar el ejemplo.");
            return;
        }

        String[] names = {
                "Documentos",
                "Imágenes",
                "Videos",
                "Música"
        };

        for (String name : names) {
            tree.addChild(name);
            children.add(name);
        }

        refreshGraphic();
        status.setText("Ejemplo cargado correctamente.");
    }

    private void showInformation() {
        showMessage(
                Alert.AlertType.INFORMATION,
                "Árbol general",
                "Un árbol general es una estructura donde un nodo " +
                        "puede tener varios hijos.\n\n" +
                        "• La raíz es el nodo principal.\n" +
                        "• Los hijos representan elementos relacionados.\n" +
                        "• Se utiliza para organizar información jerárquica.\n\n" +
                        "Cantidad de hijos: " + children.size()
        );
    }

    @FXML
    private void clearTree() {
        tree.clear();
        children.clear();

        input.clear();
        refreshGraphic();

        status.setText("El árbol está vacío.");
    }

    private void refreshGraphic() {
        graphic.getChildren().setAll(
                UI.generalTreeGraphic(children)
        );
    }

    @FXML
    private void backToMenu() {
        MainController controller = new MainController(stage);
        stage.setScene(new Scene(controller.createView(), 900, 650));
        stage.setTitle("Estructuras de Datos - Árboles");
        stage.show();
    }

    private void showMessage(
            Alert.AlertType type,
            String title,
            String message
    ) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
