
package com.litzysystems.menuarboles.clase;

public class BinarySearchTree {

    public void clear() {
    }

    private static class Node {
        int value;
        Node left, right;

        Node(int value) {
            this.value = value;
        }
    }

    private Node root;

    public void insert(int value) {
        root = insertNode(root, value);
    }

    private Node insertNode(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }

        if (value < node.value) {
            node.left = insertNode(node.left, value);
        } else if (value > node.value) {
            node.right = insertNode(node.right, value);
        }

        // No se insertan números repetidos.
        return node;
    }

    public String inorder() {
        StringBuilder result = new StringBuilder();
        inorderNode(root, result);

        return result.length() == 0
                ? "El árbol está vacío."
                : result.toString().trim();
    }

    private void inorderNode(Node node, StringBuilder result) {
        if (node == null) {
            return;
        }

        inorderNode(node.left, result);
        result.append(node.value).append("  ");
        inorderNode(node.right, result);
    }

    // Convierte el árbol en una estructura para dibujarlo.
    public TreeNodeData getVisualRoot() {
        return copyNode(root);
    }

    private TreeNodeData copyNode(Node node) {
        if (node == null) {
            return null;
        }

        TreeNodeData copy = new TreeNodeData(
                String.valueOf(node.value)
        );

        copy.setLeft(copyNode(node.left));
        copy.setRight(copyNode(node.right));

        return copy;
    }
}
