
package com.litzysystems.menuarboles.clase;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree {

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
        Node newNode = new Node(value);

        if (root == null) {
            root = newNode;
            return;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current.left == null) {
                current.left = newNode;
                return;
            } else {
                queue.add(current.left);
            }

            if (current.right == null) {
                current.right = newNode;
                return;
            } else {
                queue.add(current.right);
            }
        }
    }

    public String display() {
        if (root == null) {
            return "El árbol está vacío.";
        }

        StringBuilder result = new StringBuilder();
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            result.append(current.value).append("  ");

            if (current.left != null) {
                queue.add(current.left);
            }

            if (current.right != null) {
                queue.add(current.right);
            }
        }

        return result.toString().trim();
    }

    // Obtiene la estructura real para dibujarla.
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
