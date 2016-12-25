package ru.mail.polis;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RedBlackTree<E extends Comparable<E>> implements ISortedSet<E> {

    private enum Color {RED, BLACK}

    private class Node {
        private Node left;
        private Node right;
        private E value;
        private Color color;
        private int size;

        public Node(E value, Color color, int size) {
            this.value = value;
            this.color = color;
            this.size = size;
        }
    }

    private Node root;

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
        return size(root);
    }

    private int size(Node node){
        if (node == null){
            return 0;
        } else {
            return node.size;
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
        booleanAdd = true;
        root = add(root, value);
        root.color = Color.BLACK;
        return booleanAdd;
    }

    private boolean booleanAdd;

    private Node add(Node node, E value){
        if (node == null){
            return new Node(value, Color.RED, 1);
        }

        int cmp = compare(value, node.value);
        if (cmp < 0){
            node.left = add(node.left, value);
        } else if (cmp > 0){
            node.right = add(node.right, value);
        } else {
            booleanAdd = false;
        }

        if (isRed(node.right) && !isRed(node.left)){
            node = rotateLeft(node);
        }
        if (isRed(node.left) && isRed(node.left.left)){
            node = rotateRight(node);
        }
        if (isRed(node.left) && isRed(node.right)){
            flipColors(node);
        }
        node.size = normalSize(node);

        return node;
    }

    @Override
    public boolean remove(E value){
        if (value == null){
            throw new NullPointerException();
        }
        if (isEmpty()){
            return false;
        }
        booleanRemove = false;
        if (!isRed(root.left) && !isRed(root.right)){
            root.color = Color.RED;
        }

        root = remove(root, value);
        if (!isEmpty()){
            root.color = Color.BLACK;
        }
        return booleanRemove;
    }

    private boolean booleanRemove;

    private Node remove(Node node, E value){
        if (node == null){
            return null;
        }
        if (compare(value, node.value) < 0)  {
            if (node.left != null && !isRed(node.left) && !isRed(node.left.left)) {
                node = moveRedLeft(node);
            }
            node.left = remove(node.left, value);
        } else {
            if (isRed(node.left)) {
                node = rotateRight(node);
            }
            if (compare(value, node.value) == 0 && (node.right == null)) {
                booleanRemove = true;
                return null;
            }
            if (node.right != null && !isRed(node.right) && !isRed(node.right.left)) {
                node = moveRedRight(node);
            }
            if (compare(value, node.value) == 0) {
                Node x = min(node.right);
                node.value = x.value;
                node.right = deleteMin(node.right);
                booleanRemove = true;
            } else {
                node.right = remove(node.right, value);
            }
        }
        return balance(node);
    }

    private boolean isRed(Node node) {
        return node != null && node.color == Color.RED;
    }

    private Node rotateRight(Node node) {
        Node x = node.left;
        node.left = x.right;
        x.right = node;
        x.color = x.right.color;
        x.right.color = Color.RED;
        x.size = node.size;
        node.size = normalSize(node);
        return x;
    }

    private Node rotateLeft(Node node) {
        Node x = node.right;
        node.right = x.left;
        x.left = node;
        x.color = x.left.color;
        x.left.color = Color.RED;
        x.size = node.size;
        node.size = normalSize(node);
        return x;
    }

    private void flipColors(Node node) {
        node.color = flipColor(node.color);
        node.left.color = flipColor(node.left.color);
        node.right.color = flipColor(node.right.color);
    }

    private Color flipColor(Color color){
        if (color == Color.RED){
            return Color.BLACK;
        } else {
            return Color.RED;
        }
    }

    private Node moveRedLeft(Node node) {
        flipColors(node);
        if (isRed(node.right.left)) {
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
            flipColors(node);
        }
        return node;
    }

    private Node moveRedRight(Node node) {
        flipColors(node);
        if (isRed(node.left.left)) {
            node = rotateRight(node);
            flipColors(node);
        }
        return node;
    }

    private Node balance(Node node) {
        if (isRed(node.right)){
            node = rotateLeft(node);
        }
        if (isRed(node.left) && isRed(node.left.left)){
            node = rotateRight(node);
        }
        if (isRed(node.left) && isRed(node.right)){
            flipColors(node);
        }

        node.size = normalSize(node);
        return node;
    }

    private int normalSize(Node node){
        return (int) Math.min((long) (size(node.left) + size(node.right) + 1), Integer.MAX_VALUE);
    }

    private Node deleteMin(Node node) {
        if (node.left == null) {
            return null;
        }

        if (!isRed(node.left) && !isRed(node.left.left)) {
            node = moveRedLeft(node);
        }

        node.left = deleteMin(node.left);
        return balance(node);
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
