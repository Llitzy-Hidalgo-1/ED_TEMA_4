
package com.litzysystems.menuarboles.clase;

import java.util.ArrayList;
import java.util.List;

public class GeneralTree {

    public void clear() {
    }

    public static class Node {
        private final String value;
        private final List<Node> children = new ArrayList<>();

        public Node(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public List<Node> getChildren() {
            return children;
        }
    }

    private final Node root = new Node("RAÍZ");

    public Node getRoot() {
        return root;
    }

    public void addChild(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre del nodo no puede estar vacío."
            );
        }

        root.getChildren().add(new Node(value.trim()));
    }

    public String display() {
        StringBuilder result = new StringBuilder();
        result.append(root.getValue()).append("\n");

        for (int i = 0; i < root.getChildren().size(); i++) {
            Node child = root.getChildren().get(i);

            boolean last =
                    i == root.getChildren().size() - 1;

            result.append(last ? "   └── " : "   ├── ");
            result.append(child.getValue()).append("\n");
        }

        return result.toString();
    }

    // Convierte la estructura en datos para el dibujo.
    public TreeNodeData getVisualRoot() {
        return copyNode(root);
    }

    private TreeNodeData copyNode(Node node) {
        TreeNodeData copy = new TreeNodeData(node.getValue());

        for (Node child : node.getChildren()) {
            copy.getChildren().add(copyNode(child));
        }

        return copy;
    }
}
