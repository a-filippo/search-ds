package ru.mail.polis;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AVLTree<E extends Comparable<E>> implements ISortedSet<E> {

    private class Node{
        public Node left = null;
        public Node right = null;
        public E value;
        public int height = 0;

        public Node(E value){
            this.value = value;
        }
    }

    private Node root;

    private final Comparator<E> comparator;

    public AVLTree() {
        this.comparator = null;
    }

    public AVLTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    private int height(Node node){
        return node == null ? -1 : node.height;
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
        return size(root);
    }

    private int size(Node node){
        if (node == null){
            return 0;
        } else {
            return (int) Math.min((long) (1 + size(node.left)) + size(node.right), Integer.MAX_VALUE);
        }
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
        return contains(value, root);
    }

    private boolean contains(E value, Node node){
        boolean found = false;
        while ((node != null) && !found){
            E nodeval = node.value;
            if (compare(value, nodeval) < 0){
                node = node.left;
            } else if (compare(value, nodeval) > 0){
                node = node.right;
            } else {
                found = true;
                break;
            }
            found = contains(value, node);
        }
        return found;
    }

    @Override
    public boolean add(E value) {
        if (value == null){
            throw new NullPointerException();
        }
        booleanAdd = true;
        root = add(root, value);
        return booleanAdd;
    }

    private boolean booleanAdd;

    private Node add(Node node, E value) {
        if (node == null){
            return new Node(value);
        }
        int cmp = compare(value, node.value);
        if (cmp < 0) {
            node.left = add(node.left, value);
        } else if (cmp > 0) {
            node.right = add(node.right, value);
        } else {
            booleanAdd = false;
        }
        node.height = 1 + Math.max(height(node.left), height(node.right));
        return balance(node);
    }

    @Override
    public boolean remove(E value) {
        if (value == null){
            throw new NullPointerException();
        }
        booleanRemove = false;
        root = remove(root, value);
        return booleanRemove;
    }

    private boolean booleanRemove;

    private Node remove(Node node, E value) {
        if (node == null){
            return null;
        }
        int cmp = compare(value, node.value);
        if (cmp < 0) {
            node.left = remove(node.left, value);
        } else if (cmp > 0) {
            node.right = remove(node.right, value);
        } else {
            booleanRemove = true;
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                Node node2 = node;
                node = min(node2.right);
                node.right = deleteMin(node2.right);
                node.left = node2.left;
            }
        }
        node.height = 1 + Math.max(height(node.left), height(node.right));
        return balance(node);
    }


    private Node balance(Node node) {
        if (balanceFactor(node) < -1) {
            if (balanceFactor(node.right) > 0) {
                node.right = rotateRight(node.right);
            }
            node = rotateLeft(node);
        } else if (balanceFactor(node) > 1) {
            if (balanceFactor(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            node = rotateRight(node);
        }
        return node;
    }

    private int balanceFactor(Node node) {
        return height(node.left) - height(node.right);
    }

    private Node rotateRight(Node node) {
        Node node2 = node.left;
        node.left = node2.right;
        node2.right = node;
        node.height = 1 + Math.max(height(node.left), height(node.right));
        node2.height = 1 + Math.max(height(node2.left), height(node2.right));
        return node2;
    }

    private Node rotateLeft(Node node) {
        Node node2 = node.right;
        node.right = node2.left;
        node2.left = node;
        node.height = 1 + Math.max(height(node.left), height(node.right));
        node2.height = 1 + Math.max(height(node2.left), height(node2.right));
        return node2;
    }

    private Node deleteMin(Node node) {
        if (node.left == null) {
            return node.right;
        }
        node.left = deleteMin(node.left);
        node.height = 1 + Math.max(height(node.left), height(node.right));
        return balance(node);
    }

    private Node min(Node node) {
        return (node.left == null) ? node : min(node.left);
    }

    private Node max(Node node) {
        return (node.right == null) ? node : max(node.right);
    }

    private int compare(E v1, E v2) {
        return comparator == null ? v1.compareTo(v2) : comparator.compare(v1, v2);
    }
}
