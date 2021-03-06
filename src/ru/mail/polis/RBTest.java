package ru.mail.polis;

import java.util.Scanner;

public class RBTest {
    public static void main(String[] args) {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        Scanner in = new Scanner(System.in);

        /*for (int i = 0; i < 6000; i++){
            if (!tree.add(i)){
                System.out.println("error");
            }
        }*/

        while (true){
            String line = in.nextLine();
            String[] lines = line.split("\\s+");
            int a = 0;
            if (lines.length > 1){
                a = Integer.parseInt(lines[1]);
            }

            String answer = "OK";
            switch (lines[0]){
                case "add":
                    answer = tree.add(a) ? "true" : "false";
                    break;
                case "find":
                    answer = tree.contains(a) ? "found" : "not found";
                    break;
                case "remove":
                    answer = tree.remove(a) ? "true" : "false";
                    break;
                case "size":
                    answer = String.valueOf(tree.size());
                    break;
                case "first":
                    answer = tree.first().toString();
                    break;
                case "last":
                    answer = tree.last().toString();
                    break;
                case "write":
                    answer = tree.inorderTraverse().toString();
                    break;
            }
            System.out.println(answer);
        }
    }
}