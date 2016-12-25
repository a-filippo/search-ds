package ru.mail.polis;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class OpenHashTable<E extends Comparable<E>> implements ISet<E> {

    private Comparator<E> comparator;

    private class HashCell<E>{
        private E value;
        private boolean deleted = false;

        public HashCell(E value){
            this.value = value;
        }

        public boolean isDeleted(){
            return deleted;
        }

        public void delete(){
            deleted = true;
        }
    }

    private Function<E, Integer> hashFunction;

    private int size = 0;
    private int tableSize = 8;

    private HashCell<E>[] table;

    public OpenHashTable(Comparator<E> comparator) {
        this.comparator = comparator;
        table = new HashCell[tableSize];
    }

    public void setHashFunction(Function<E, Integer> func){
        hashFunction = func;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(E value) {
        if (value == null){
            throw new NullPointerException();
        }

        int x = h1(value);
        int y = h2(value);
        while (table[x] != null){
            if (!table[x].isDeleted() && comparator.compare(table[x].value, value) == 0){
                return true;
            }

            x += y;
            x %= tableSize;
        }

        return false;
    }

    @Override
    public boolean add(E value) {
        if (value == null){
            throw new NullPointerException();
        }

        int x = h1(value);
        int y = h2(value);
        while (table[x] != null && !table[x].isDeleted()){
            if (comparator.compare(table[x].value, value) == 0){
                return false;
            }

            x += y;
            x %= tableSize;
        }
        table[x] = new HashCell<>(value);
        size++;
        rehashing();
        return true;
    }

    @Override
    public boolean remove(E value) {
        if (value == null){
            throw new NullPointerException();
        }

        int x = h1(value);
        int y = h2(value);
        while (table[x] != null && !table[x].isDeleted()){
            if (comparator.compare(table[x].value, value) == 0){
                table[x].delete();
                size--;
                return true;
            }

            x += y;
            x %= tableSize;
        }
        return false;
    }

    private void rehashing(){
        if ((double) size / tableSize >= 0.5){
            List<E> values = toList();
            tableSize *= 2;
            size = 0;
            table = new HashCell[tableSize];
            for (E value : values){
                add(value);
            }
        }
    }

    private int h1(E value){
        return Math.abs(value.hashCode()) % tableSize;
    }

    private int h2(E key){
        int hash = hashFunction.apply(key);
        return (hash * 2 + 3) % tableSize;
    }

    public List<E> toList(){
        List<E> list = new ArrayList<E>();
        for (int i = 0; i < tableSize; i++){
            if (table[i] != null && !table[i].isDeleted()){
                list.add(table[i].value);
            }
        }
        return list;
    }
}
