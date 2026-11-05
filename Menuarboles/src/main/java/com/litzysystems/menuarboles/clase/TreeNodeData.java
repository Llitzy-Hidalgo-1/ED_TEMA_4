
package com.litzysystems.menuarboles.clase;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un nodo que puede utilizarse para dibujar un árbol.
 * Los comentarios explican su funcionamiento.
 */
public class TreeNodeData {

    private final String value;
    private TreeNodeData left;
    private TreeNodeData right;
    private final List<TreeNodeData> children = new ArrayList<>();

    public TreeNodeData(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public TreeNodeData getLeft() {
        return left;
    }

    public void setLeft(TreeNodeData left) {
        this.left = left;
    }

    public TreeNodeData getRight() {
        return right;
    }

    public void setRight(TreeNodeData right) {
        this.right = right;
    }

    public List<TreeNodeData> getChildren() {
        return children;
    }
}
