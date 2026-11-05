package com.litzysystems.grafos.controller;

import com.litzysystems.grafos.clases.Graph;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.*;

public class GraphController {

    @FXML private TextField vertexField;
    @FXML private TextField firstVertexField;
    @FXML private TextField secondVertexField;
    @FXML private TextField startField;

    @FXML private Pane graphPane;
    @FXML private Label resultLabel;
    @FXML private Label verticesLabel;

    private Graph graph = new Graph();

    @FXML
    public void initialize() {
        updateGraph();
    }

    private void showMessage(String title, String message,
                             Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // AGREGAR VERTICE
    @FXML
    private void handleAddVertex() {

        String vertex = vertexField.getText().trim();

        if (vertex.isEmpty()) {
            showMessage("Aviso", "Escribe un vertice.",
                    Alert.AlertType.WARNING);
            return;
        }

        if (graph.addVertex(vertex)) {
            vertexField.clear();
            resultLabel.setText("Vertice agregado: " + vertex);
            updateGraph();
        } else {
            showMessage("Error",
                    "El vertice ya existe o no es valido.",
                    Alert.AlertType.WARNING);
        }
    }

    // AGREGAR ARISTA
    @FXML
    private void handleAddEdge() {

        String a = firstVertexField.getText().trim();
        String b = secondVertexField.getText().trim();

        if (a.isEmpty() || b.isEmpty()) {
            showMessage("Aviso",
                    "Escribe los dos vertices.",
                    Alert.AlertType.WARNING);
            return;
        }

        if (graph.addEdge(a, b)) {
            firstVertexField.clear();
            secondVertexField.clear();

            resultLabel.setText("Conexion agregada: " + a + " - " + b);
            updateGraph();
        } else {
            showMessage("Error",
                    "Verifica que ambos vertices existan y no esten conectados.",
                    Alert.AlertType.WARNING);
        }
    }

    // RECORRIDO BFS
    @FXML
    private void handleBFS() {
        executeTraversal(true);
    }

    // RECORRIDO DFS
    @FXML
    private void handleDFS() {
        executeTraversal(false);
    }

    private void executeTraversal(boolean breadth) {

        String start = startField.getText().trim();

        if (start.isEmpty()) {
            showMessage("Aviso",
                    "Escribe el vertice inicial.",
                    Alert.AlertType.WARNING);
            return;
        }

        List<String> result = breadth
                ? graph.bfs(start)
                : graph.dfs(start);

        if (result.isEmpty()) {
            showMessage("Error",
                    "El vertice no existe.",
                    Alert.AlertType.WARNING);
            return;
        }

        String method = breadth ? "BFS - AMPLITUD" : "DFS - PROFUNDIDAD";

        resultLabel.setText(
                method + ": " + String.join(" -> ", result));

        updateGraph(result);
    }

    // DIBUJAR GRAFO
    private void updateGraph() {
        updateGraph(new ArrayList<>());
    }

    private void updateGraph(List<String> recorrido) {

        graphPane.getChildren().clear();

        List<String> vertices =
                new ArrayList<>(graph.getGraph().keySet());

        int total = vertices.size();

        verticesLabel.setText("Vertices registrados: " + total);

        if (total == 0) return;

        double centerX = graphPane.getPrefWidth() / 2;
        double centerY = graphPane.getPrefHeight() / 2;
        double radius = Math.min(115, 35 + total * 13);

        Map<String, double[]> positions = new LinkedHashMap<>();

        for (int i = 0; i < total; i++) {

            double angle = -Math.PI / 2
                    + (2 * Math.PI * i / total);

            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);

            positions.put(vertices.get(i), new double[]{x, y});
        }

        // Dibujar aristas
        for (String vertex : vertices) {

            for (String neighbor : graph.getGraph().get(vertex)) {

                if (vertices.indexOf(vertex)
                        < vertices.indexOf(neighbor)) {

                    double[] p1 = positions.get(vertex);
                    double[] p2 = positions.get(neighbor);

                    Line line = new Line(
                            p1[0], p1[1], p2[0], p2[1]);

                    line.setStroke(Color.web("#64748B"));
                    line.setStrokeWidth(4);

                    graphPane.getChildren().add(line);
                }
            }
        }

        // Dibujar vertices
        for (String vertex : vertices) {

            double[] p = positions.get(vertex);

            Circle circle = new Circle(p[0], p[1], 25);

            circle.setFill(
                    recorrido.contains(vertex)
                            ? Color.web("#F59E0B")
                            : Color.web("#2563EB"));

            circle.setStroke(Color.web("#172554"));
            circle.setStrokeWidth(2);

            Text text = new Text(vertex);

            text.setFont(Font.font(
                    "System", FontWeight.BOLD, 15));

            text.setFill(Color.WHITE);

            text.setX(p[0] -
                    text.getLayoutBounds().getWidth() / 2);
            text.setY(p[1] + 5);

            graphPane.getChildren().addAll(circle, text);
        }
    }

    // LIMPIAR
    @FXML
    private void handleClear() {

        graph.clear();

        vertexField.clear();
        firstVertexField.clear();
        secondVertexField.clear();
        startField.clear();

        resultLabel.setText("Grafo limpio. Ingresa nuevos datos.");

        updateGraph();
    }

    // SALIR
    @FXML
    private void handleExit() {
        Platform.exit();
    }
}
