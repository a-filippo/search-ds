package ru.mail.polis;

import java.util.Comparator;
import java.util.List;

//TODO: write code here
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
    private int size;
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
            int size = 1;
            size += size(node.left);
            size += size(node.right);
            return size;
        }
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public boolean contains(E value) {
        return contains(value, root);
    }

    private boolean contains(E value, Node node){
        boolean found = false;
        while ((node != null) && !found){
            E nodeval = node.value;
            if (value.compareTo(nodeval) < 0){
                node = node.left;
            } else if (value.compareTo(nodeval) > 0){
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
        return false;
    }

    private Node add(E value, Node node){
        if (node == null){
            node = new Node(value);
        } else if (value.compareTo(node.value) < 0){
            node.left = add(value, node.left);
            if (height(node.left) - height(node.right) == 2){
                if (value.compareTo(node.left.value) < 0){
                    node = littleLeftRotate(node);
                } else {
                    node = bigLeftRotate(node);
                }
            }
        } else {// if (value > node.value){
            node.right = add(value, node.right);
            if (height(node.right) - height(node.left) == 2){
                if (value.compareTo(node.right.value) > 0){
                    node = littleRightRotate(node);
                } else {
                    node = bigRightRotate(node);
                }
            }
        }
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        return node;
    }

    private Node littleLeftRotate(Node node1){
        Node node2 = node1.left;
        node1.left = node2.right;
        node2.right = node1;
        node1.height = Math.max(height(node1.left), height(node1.right)) + 1;
        node2.height = Math.max(height(node2.left), node1.height) + 1;
        return node2;
    }

    private Node littleRightRotate(Node node1){
        Node node2 = node1.right;
        node1.right = node2.left;
        node2.left = node1;
        node1.height = Math.max(height(node1.left), height(node1.right)) + 1;
        node2.height = Math.max(height(node2.right), node1.height) + 1;
        return node2;
    }

    private Node bigLeftRotate(Node node){
        node.left = littleRightRotate(node.left);
        return littleLeftRotate(node);
    }

    private Node bigRightRotate(Node node){
        node.right = littleLeftRotate(node.right);
        return littleRightRotate(node);
    }

    @Override
    public boolean remove(E value) {
        return false;
    }

    private int compare(E v1, E v2) {
        return comparator == null ? v1.compareTo(v2) : comparator.compare(v1, v2);
    }
}
