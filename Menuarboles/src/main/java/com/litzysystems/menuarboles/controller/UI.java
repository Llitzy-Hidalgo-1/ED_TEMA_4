
package com.litzysystems.menuarboles.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class UI {

    private UI() {
        // Evita crear objetos de esta clase auxiliar.
    }

    // Colores principales de la interfaz.
    public static final String PURPLE = "#9333EA";
    public static final String NAVY = "#172554";
    public static final String BLUE = "#2563EB";
    public static final String CYAN = "#06B6D4";
    public static final String BG = "#F1F5F9";
    public static final String GREEN = "#059669";
    public static final String RED = "#DC2626";
    public static final String ORANGE = "#EA580C";

    // Crea la pantalla principal de cada estructura.
    public static VBox page(String titleText, String subtitleText) {
        VBox root = new VBox(16);
        root.setPadding(new Insets(24));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle(
                "-fx-background-color: " + BG + ";"
        );

        Label title = new Label(titleText);
        title.setStyle(
                "-fx-font-size: 27px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: " + NAVY + ";"
        );

        Label subtitle = new Label(subtitleText);
        subtitle.setWrapText(true);
        subtitle.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-text-fill: #475569;"
        );

        root.getChildren().addAll(title, subtitle);
        return root;
    }

    // Crea botones con colores personalizados.
    public static Button button(String text, String color) {
        Button button = new Button(text);

        button.setMinHeight(40);
        button.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-padding: 10px 16px;" +
                        "-fx-cursor: hand;"
        );

        button.setMaxWidth(Double.MAX_VALUE);
        return button;
    }

    // Crea un campo para introducir números o texto.
    public static TextField input(String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setPrefHeight(40);
        field.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: #CBD5E1;" +
                        "-fx-border-radius: 8px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-padding: 8px;"
        );

        return field;
    }

    // Crea un área para mostrar resultados.
    public static TextArea output(String prompt) {
        TextArea area = new TextArea();
        area.setPromptText(prompt);
        area.setEditable(false);
        area.setWrapText(true);
        area.setPrefRowCount(5);
        area.setStyle(
                "-fx-control-inner-background: white;" +
                        "-fx-border-color: #CBD5E1;" +
                        "-fx-border-radius: 8px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-font-size: 13px;"
        );

        return area;
    }

    // Organiza varios botones horizontalmente.
    public static HBox buttonRow(Button... buttons) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER);
        row.setPadding(new Insets(5, 0, 5, 0));

        for (Button button : buttons) {
            HBox.setHgrow(button, Priority.ALWAYS);
            row.getChildren().add(button);
        }

        return row;
    }

    // Muestra mensajes de error.
    public static void error(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("No se pudo realizar la operación");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Muestra mensajes informativos.
    public static void info(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText("Resultado");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Convierte el contenido del campo a un número entero.
    public static Integer parseInteger(TextField field) {
        String text = field.getText();

        if (text == null || text.trim().isEmpty()) {
            error("Por favor, escribe un número entero.");
            return null;
        }

        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            error("El dato ingresado no es un número entero válido.");
            return null;
        }
    }

    // Dibuja un árbol a partir de una lista de valores.
    // BINARY: inserción por niveles.
    // AVL: representación balanceada de los valores ordenados.
    // Otros tipos: forma de árbol binario de búsqueda.
    public static Canvas treeGraphic(List<Integer> values, String type) {
        int count = values == null ? 0 : values.size();

        int levels = 1;
        while (Math.pow(2, levels) - 1 < count) {
            levels++;
        }

        double width = Math.max(650, Math.pow(2, levels) * 55);
        double height = Math.max(300, levels * 100 + 70);

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.web("#FFFFFF"));
        gc.fillRoundRect(0, 0, width, height, 18, 18);

        if (values == null || values.isEmpty()) {
            gc.setFill(Color.web("#64748B"));
            gc.setFont(javafx.scene.text.Font.font(17));
            gc.fillText("El árbol está vacío", 30, 45);
            return canvas;
        }

        List<Integer> ordered = new ArrayList<>(values);

        DrawNode root;

        if ("AVL".equalsIgnoreCase(type)) {
            Collections.sort(ordered);
            root = buildBalanced(ordered, 0, ordered.size() - 1);
        } else if ("BINARY".equalsIgnoreCase(type)) {
            root = buildLevelOrder(ordered);
        } else {
            root = null;
            for (Integer value : ordered) {
                root = insertBST(root, value);
            }
        }

        double horizontalGap = width / (Math.pow(2, levels) + 1);
        drawNode(gc, root, width / 2, 45, horizontalGap, 80);

        return canvas;
    }

    // Nodo auxiliar usado únicamente para dibujar.
    private static class DrawNode {
        int value;
        DrawNode left;
        DrawNode right;

        DrawNode(int value) {
            this.value = value;
        }
    }

    // Construye una representación balanceada usando la lista ordenada.
    private static DrawNode buildBalanced(
            List<Integer> values, int start, int end) {

        if (start > end) {
            return null;
        }

        int middle = (start + end) / 2;
        DrawNode node = new DrawNode(values.get(middle));

        node.left = buildBalanced(values, start, middle - 1);
        node.right = buildBalanced(values, middle + 1, end);

        return node;
    }

    // Inserta los valores en orden por niveles para un árbol binario.
    private static DrawNode buildLevelOrder(List<Integer> values) {
        if (values.isEmpty()) {
            return null;
        }

        List<DrawNode> nodes = new ArrayList<>();

        for (Integer value : values) {
            nodes.add(new DrawNode(value));
        }

        for (int i = 0; i < nodes.size(); i++) {
            int leftIndex = 2 * i + 1;
            int rightIndex = 2 * i + 2;

            if (leftIndex < nodes.size()) {
                nodes.get(i).left = nodes.get(leftIndex);
            }

            if (rightIndex < nodes.size()) {
                nodes.get(i).right = nodes.get(rightIndex);
            }
        }

        return nodes.get(0);
    }

    // Inserta un valor respetando las reglas de un árbol de búsqueda.
    private static DrawNode insertBST(DrawNode node, int value) {
        if (node == null) {
            return new DrawNode(value);
        }

        if (value < node.value) {
            node.left = insertBST(node.left, value);
        } else if (value > node.value) {
            node.right = insertBST(node.right, value);
        }

        return node;
    }

    // Dibuja las conexiones y los círculos de los nodos.
    private static void drawNode(
            GraphicsContext gc,
            DrawNode node,
            double x,
            double y,
            double gap,
            double verticalGap) {

        if (node == null) {
            return;
        }

        double childY = y + verticalGap;

        if (node.left != null) {
            double childX = x - gap;

            gc.setStroke(Color.web("#94A3B8"));
            gc.setLineWidth(2.5);
            gc.strokeLine(x, y + 20, childX, childY);

            drawNode(
                    gc, node.left, childX, childY,
                    Math.max(25, gap / 2), verticalGap
            );
        }

        if (node.right != null) {
            double childX = x + gap;

            gc.setStroke(Color.web("#94A3B8"));
            gc.setLineWidth(2.5);
            gc.strokeLine(x, y + 20, childX, childY);

            drawNode(
                    gc, node.right, childX, childY,
                    Math.max(25, gap / 2), verticalGap
            );
        }

        gc.setFill(Color.web(PURPLE));
        gc.fillOval(x - 21, y - 1, 42, 42);

        gc.setStroke(Color.web("#7E22CE"));
        gc.setLineWidth(2);
        gc.strokeOval(x - 21, y - 1, 42, 42);

        gc.setFill(Color.WHITE);
        gc.setFont(javafx.scene.text.Font.font(
                "System", javafx.scene.text.FontWeight.BOLD, 13
        ));

        String value = String.valueOf(node.value);
        double textWidth = value.length() * 7.5;

        gc.fillText(value, x - textWidth / 2, y + 24);
    }

    // Dibuja un árbol general con una raíz y sus hijos directos.
    public static Canvas generalTreeGraphic(List<String> children) {
        int count = children == null ? 0 : children.size();

        double width = Math.max(650, count * 150 + 100);
        double height = 300;

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.WHITE);
        gc.fillRoundRect(0, 0, width, height, 18, 18);

        double rootX = width / 2;
        double rootY = 45;

        gc.setStroke(Color.web("#94A3B8"));
        gc.setLineWidth(2.5);

        gc.setFill(Color.web(NAVY));
        gc.fillRoundRect(rootX - 55, rootY, 110, 42, 12, 12);

        gc.setFill(Color.WHITE);
        gc.setFont(javafx.scene.text.Font.font(
                "System", javafx.scene.text.FontWeight.BOLD, 14
        ));
        gc.fillText("RAÍZ", rootX - 19, rootY + 26);

        if (children == null || children.isEmpty()) {
            gc.setFill(Color.web("#64748B"));
            gc.setFont(javafx.scene.text.Font.font(14));
            gc.fillText("Agrega elementos para visualizar el árbol",
                    20, 150);
            return canvas;
        }

        double spacing = width / (count + 1);

        for (int i = 0; i < count; i++) {
            double childX = spacing * (i + 1);
            double childY = 175;

            gc.setStroke(Color.web("#94A3B8"));
            gc.strokeLine(rootX, rootY + 42, childX, childY);

            gc.setFill(Color.web(BLUE));
            gc.fillRoundRect(childX - 55, childY, 110, 44, 12, 12);

            gc.setFill(Color.WHITE);
            gc.setFont(javafx.scene.text.Font.font(12));

            String name = children.get(i);
            if (name.length() > 13) {
                name = name.substring(0, 12) + "...";
            }

            double textWidth = name.length() * 7;
            gc.fillText(name, childX - textWidth / 2, childY + 27);
        }

        return canvas;
    }
}
