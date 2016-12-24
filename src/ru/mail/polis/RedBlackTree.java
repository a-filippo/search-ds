package ru.mail.polis;

import java.util.Comparator;
import java.util.List;

//TODO: write code here
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

    private int size;
    private final Comparator<E> comparator;



    public RedBlackTree() {
        this.comparator = null;
    }

    public RedBlackTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public E first() {
        return null;
    }

    @Override
    public E last() {
        return null;
    }

    @Override
    public List<E> inorderTraverse() {
        return null;
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
        return false;
    }

    @Override
    public boolean add(E value) {
        return false;
    }

    @Override
    public boolean remove(E value) {
        return false;
    }

    private int compare(E v1, E v2) {
        return comparator == null ? v1.compareTo(v2) : comparator.compare(v1, v2);
    }
}
