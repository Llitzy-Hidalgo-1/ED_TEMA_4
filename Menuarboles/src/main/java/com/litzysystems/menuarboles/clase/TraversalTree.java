
package com.litzysystems.menuarboles.clase;

public class TraversalTree {

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

        return node;
    }

    public String preorder() {
        StringBuilder result = new StringBuilder();
        preorderNode(root, result);
        return emptyOrResult(result);
    }

    private void preorderNode(Node node, StringBuilder result) {
        if (node == null) {
            return;
        }

        result.append(node.value).append(" ");
        preorderNode(node.left, result);
        preorderNode(node.right, result);
    }

    public String inorder() {
        StringBuilder result = new StringBuilder();
        inorderNode(root, result);
        return emptyOrResult(result);
    }

    private void inorderNode(Node node, StringBuilder result) {
        if (node == null) {
            return;
        }

        inorderNode(node.left, result);
        result.append(node.value).append(" ");
        inorderNode(node.right, result);
    }

    public String postorder() {
        StringBuilder result = new StringBuilder();
        postorderNode(root, result);
        return emptyOrResult(result);
    }

    private void postorderNode(Node node, StringBuilder result) {
        if (node == null) {
            return;
        }

        postorderNode(node.left, result);
        postorderNode(node.right, result);
        result.append(node.value).append(" ");
    }

    private String emptyOrResult(StringBuilder result) {
        return result.length() == 0
                ? "El árbol está vacío."
                : result.toString().trim();
    }

    // Obtiene el árbol real para mostrarlo gráficamente.
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
