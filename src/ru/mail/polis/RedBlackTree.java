package ru.mail.polis;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RedBlackTree<E extends Comparable<E>> implements ISortedSet<E> {

    private enum Color {RED, BLACK}

    private class Node {
        private Node left = null;
        private Node right = null;
        private Node parent = null;
        private E value;
        private Color color;

        private Node(E value, Node parent, Color color) {
            this.value = value;
            this.parent = parent;
            this.color = color;
        }
    }

    private Node root;

    private int size = 0;

    private final Comparator<E> comparator;


    public RedBlackTree() {
        this.comparator = null;
    }

    public RedBlackTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public E first() {
        if (isEmpty()){
            throw new java.util.NoSuchElementException();
        }
        return min(root).value;
    }

    @Override
    public E last() {
        if (isEmpty()){
            throw new java.util.NoSuchElementException();
        }
        return max(root).value;
    }

    @Override
    public List<E> inorderTraverse() {
        outList = new ArrayList<E>();
        inorderTraverse(root);
        return outList;
    }

    private List<E> outList;

    private void inorderTraverse(Node node){
        if (node != null){
            inorderTraverse(node.left);
            outList.add(node.value);
            inorderTraverse(node.right);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public boolean contains(E value) {
        if (value == null){
            throw new NullPointerException();
        }
        return contains(root, value);
    }

    private boolean contains(Node node, E value) {
        while (node != null) {
            int cmp = compare(value, node.value);
            if (cmp < 0){
                node = node.left;
            } else if (cmp > 0){
                node = node.right;
            } else {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean add(E value) {
        if (value == null){
            throw new NullPointerException();
        }

        Node temp = root;
        Node node;
        if (root == null) {
            root = new Node(value, null, Color.BLACK);
            size++;
        } else {
            while (true) {
                int cmp = compare(value, temp.value);
                if (cmp < 0) {
                    if (temp.left == null) {
                        temp.left = new Node(value, temp, Color.RED);
                        node = temp.left;
                        size++;
                        break;
                    } else {
                        temp = temp.left;
                    }
                } else if (cmp > 0) {
                    if (temp.right == null) {
                        temp.right = new Node(value, temp, Color.RED);
                        node = temp.right;
                        size++;
                        break;
                    } else {
                        temp = temp.right;
                    }
                } else {
                    return false;
                }
            }
            fixTree(node);
        }
        return true;
    }

    private void fixTree(Node node) {
        while (node.parent != null && node.parent.color == Color.RED) {
            Node uncle;
            if (node.parent == grandparent(node).left) {
                uncle = grandparent(node).right;
                if (uncle != null && uncle.color == Color.RED) {
                    node.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    grandparent(node).color = Color.RED;
                    node = grandparent(node);
                    continue;
                }
                if (node == node.parent.right) {
                    node = node.parent;
                    rotateLeft(node);
                }
                node.parent.color = Color.BLACK;
                grandparent(node).color = Color.RED;
                rotateRight(grandparent(node));
            } else {
                uncle = grandparent(node).left;
                if (uncle != null && uncle.color == Color.RED) {
                    node.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    grandparent(node).color = Color.RED;
                    node = grandparent(node);
                    continue;
                }
                if (node == node.parent.left){
                    node = node.parent;
                    rotateRight(node);
                }
                node.parent.color = Color.BLACK;
                grandparent(node).color = Color.RED;
                rotateLeft(grandparent(node));
            }
        }
        root.color = Color.BLACK;
    }


    @Override
    public boolean remove(E value){
        return false;
    }

    private Node grandparent(Node node){
        return (node != null && node.parent != null) ? node.parent.parent : null;
    }

    void rotateLeft(Node node) {
        if (node.parent != null) {
            if (node == node.parent.left) {
                node.parent.left = node.right;
            } else {
                node.parent.right = node.right;
            }
            node.right.parent = node.parent;
            node.parent = node.right;
            if (node.right.left != null) {
                node.right.left.parent = node;
            }
            node.right = node.right.left;
            node.parent.left = node;
        } else {
            Node right = root.right;
            root.right = right.left;
            if (right.left != null){
                right.left.parent = root;
            }
            root.parent = right;
            right.left = root;
            right.parent = null;
            root = right;
        }
    }

    void rotateRight(Node node) {
        if (node.parent != null) {
            if (node == node.parent.left) {
                node.parent.left = node.left;
            } else {
                node.parent.right = node.left;
            }

            node.left.parent = node.parent;
            node.parent = node.left;
            if (node.left.right != null) {
                node.left.right.parent = node;
            }
            node.left = node.left.right;
            node.parent.right = node;
        } else {
            Node left = root.left;
            root.left = root.left.right;
            if (left.right != null) {
                left.right.parent = root;
            }
            root.parent = left;
            left.right = root;
            left.parent = null;
            root = left;
        }
    }

    private Node min(Node node) {
        if (node.left == null){
            return node;
        } else {
            return min(node.left);
        }
    }

    private Node max(Node node) {
        if (node.right == null){
            return node;
        } else {
            return max(node.right);
        }
    }

    private int compare(E v1, E v2) {
        return comparator == null ? v1.compareTo(v2) : comparator.compare(v1, v2);
    }
}
