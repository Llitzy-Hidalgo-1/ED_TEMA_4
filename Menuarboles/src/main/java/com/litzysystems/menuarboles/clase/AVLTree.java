
package com.litzysystems.menuarboles.clase;

public class AVLTree {

    public void clear() {
    }

    private static class Node {
        int value;
        int height = 1;
        Node left, right;

        Node(int value) {
            this.value = value;
        }
    }

    private Node root;

    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    private int balance(Node node) {
        return node == null
                ? 0
                : height(node.left) - height(node.right);
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node temp = x.right;

        x.right = y;
        y.left = temp;

        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));

        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node temp = y.left;

        y.left = x;
        x.right = temp;

        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));

        return y;
    }

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
        } else {
            return node;
        }

        node.height = 1 + Math.max(
                height(node.left),
                height(node.right)
        );

        int b = balance(node);

        // Caso izquierda-izquierda
        if (b > 1 && value < node.left.value) {
            return rotateRight(node);
        }

        // Caso derecha-derecha
        if (b < -1 && value > node.right.value) {
            return rotateLeft(node);
        }

        // Caso izquierda-derecha
        if (b > 1 && value > node.left.value) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // Caso derecha-izquierda
        if (b < -1 && value < node.right.value) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

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

    // Obtiene la estructura real para dibujar el árbol.
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
