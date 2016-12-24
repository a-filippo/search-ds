package ru.mail.polis;

import java.util.ArrayList;
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
        if (value == null){
            throw new NullPointerException();
        }
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
        if (value == null){
            throw new NullPointerException();
        }
        booleanAdd = true;
        root = add(root, value);
        return booleanAdd;
    }

    private boolean booleanAdd;

    /*private Node add(Node node, E value){
        if (node == null){
            return new Node(value);
        }
        int cmp = value.compareTo(node.value);
        if (cmp < 0) {
            node.left = add(node.left, value);
        } else if (cmp > 0) {
            node.right = add(node.right, value);
        } else {
            node.value = value;
            return node;
        }
        node.height = 1 + Math.max(height(node.left), height(node.right));
        return balance(node);
    }*/
    private Node add(Node node, E value){
        if (node == null){
            node = new Node(value);
        } else if (value.compareTo(node.value) < 0){
            node.left = add(node.left, value);
            if (height(node.left) - height(node.right) == 2){
                if (value.compareTo(node.left.value) < 0){
                    node = littleLeftRotate(node);
                } else {
                    node = bigLeftRotate(node);
                }
            }
        } else if (value.compareTo(node.value) > 0){
            node.right = add(node.right, value);
            if (height(node.right) - height(node.left) == 2){
                if (value.compareTo(node.right.value) > 0){
                    node = littleRightRotate(node);
                } else {
                    node = bigRightRotate(node);
                }
            }
        } else {
            booleanAdd = false;
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

    @Override
    public boolean remove(E value) {
        if (value == null){
            throw new NullPointerException();
        }
        booleanRemove = false;
        remove(root, value);
        return booleanRemove;
    }

    private boolean booleanRemove;

    /*private Node remove(Node node, E value) {
        int cmp = value.compareTo(node.value);
        if (cmp < 0) {
            node.left = remove(node.left, value);
        } else if (cmp > 0) {
            node.right = remove(node.right, value);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                Node y = node;
                node = min(y.right);
                node.right = deleteMin(y.right);
                node.left = y.left;
            }
        }
        node.height = 1 + Math.max(height(node.left), height(node.right));
        return balance(node);
    }

    private Node deleteMin(Node node) {
        if (node.left == null){
            return node.right;
        }
        node.left = deleteMin(node.left);
        node.height = 1 + Math.max(height(node.left), height(node.right));
        return balance(node);
    }*/

    public Node remove(Node node, E value) {
        if (node == null){
            return null;
        }
        if (value.compareTo(node.value) < 0) {
            node.left = remove(node.left, value);
            int l = node.left != null ? node.left.height : 0;

            if((node.right != null) && (node.right.height - l >= 2)) {
                int rightHeight = node.right.right != null ? node.right.right.height : 0;
                int leftHeight = node.right.left != null ? node.right.left.height : 0;

                if (rightHeight >= leftHeight) {
                    node = littleLeftRotate(node);
                } else {
                    node = bigRightRotate(node);
                }
            }
        } else if (value.compareTo(node.value) > 0) {
            node.right = remove(node.right, value);
            int r = node.right != null ? node.right.height : 0;
            if ((node.left != null) && (node.left.height - r >= 2)) {
                int leftHeight = node.left.left != null ? node.left.left.height : 0;
                int rightHeight = node.left.right != null ? node.left.right.height : 0;
                if (leftHeight >= rightHeight) {
                    node = littleRightRotate(node);
                } else {
                    node = bigLeftRotate(node);
                }
            }
        } else {
            if (node.left != null) {
                node.value = max(node.left).value;
                remove(node.left, node.value);

                if ((node.right != null) && (node.right.height - node.left.height >= 2)) {
                    int rightHeight = node.right.right != null ? node.right.right.height : 0;
                    int leftHeight = node.right.left != null ? node.right.left.height : 0;

                    if (rightHeight >= leftHeight) {
                        node = littleLeftRotate(node);
                    } else {
                        node = bigRightRotate(node);
                    }
                }
            } else {
                node = node.right;
            }

            booleanRemove = true;
        }

        if (node != null) {
            int leftHeight = node.left != null ? node.left.height : 0;
            int rightHeight = node.right!= null ? node.right.height : 0;
            node.height = Math.max(leftHeight, rightHeight) + 1;
        }

        return node;
    }

    /*private int balanceFactor(Node node) {
        return height(node.left) - height(node.right);
    }

    private Node balance(Node node) {
        if (balanceFactor(node) < -1) {
            if (balanceFactor(node.right) > 0) {
                node.right = rightRotate(node.right);
            }
            node = leftRotate(node);
        }
        else if (balanceFactor(node) > 1) {
            if (balanceFactor(node.left) < 0) {
                node.left = leftRotate(node.left);
            }
            node = rightRotate(node);
        }
        return node;
    }*/

    private int compare(E v1, E v2) {
        return comparator == null ? v1.compareTo(v2) : comparator.compare(v1, v2);
    }
}
