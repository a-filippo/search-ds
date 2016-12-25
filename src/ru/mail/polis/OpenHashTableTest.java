package ru.mail.polis;

import java.util.Comparator;
import java.util.Scanner;

public class OpenHashTableTest {
    public static void main(String[] args) {

        Comparator<String> comparator = (String o1, String o2) -> {
            if (o1.equals(o2)){
                return 0;
            }
            return 1;
        };

        OpenHashTable<String> table = new OpenHashTable<>(comparator);

        table.setHashFunction((String key) -> {
            int p = 26;
            int hash = 0, p_pow = 1;
            for (int i = 0; i < key.length(); i++) {
                hash += (key.charAt(i) - 'a' + 1) * p_pow;
                p_pow *= p;
            }
            return hash;
        });

        table.add("q");
        table.add("w");
        table.add("e");
        table.add("r");
        table.add("t");
        table.add("y");
        table.add("u");
        table.add("i");
        table.add("o");
        table.add("p");

        Scanner in = new Scanner(System.in);

        while (true){
            String line = in.nextLine();
            String[] lines = line.split("\\s+");

            String answer = "OK";
            switch (lines[0]){
                case "add":
                    answer = table.add(lines[1]) ? "true" : "false";
                    break;
                case "find":
                    answer = table.contains(lines[1]) ? "found" : "not found";
                    break;
                case "remove":
                    answer = table.remove(lines[1]) ? "true" : "false";
                    break;
                case "size":
                    answer = String.valueOf(table.size());
                    break;
                case "write":
                    answer = table.toList().toString();
                    break;
            }
            System.out.println(answer);
        }
    }
}
