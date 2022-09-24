import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static int MAX_LEVEL = 32;
    private int[] sizeLevel;

    private Item[] arr;
    private int cap;
    private int currLevel;

    public RandomizedQueue() {
        sizeLevel = new int[MAX_LEVEL];
        sizeLevel[0] = 2;
        for (int i = 1; i < MAX_LEVEL; i++)
            sizeLevel[i] = sizeLevel[i - 1] * 2;
        currLevel = 0;
        cap = 0;
        arr = (Item[]) new Object[sizeLevel[currLevel]];
    }

    private void upSize() {
        if (currLevel == MAX_LEVEL)
            return;
        Item[] newArr = (Item[]) new Object[sizeLevel[++currLevel]];
        for (int i = 0; i < cap; i++) {
            newArr[i] = arr[i];
        }
        arr = newArr;
    }

    private void downSize() {
        if (currLevel == 0)
            return;
        Item[] newArr = (Item[]) new Object[sizeLevel[--currLevel]];
        for (int i = 0; i < cap; i++) {
            newArr[i] = arr[i];
        }
        arr = newArr;
    }

    public int size() {
        return cap;
    }

    public boolean isEmpty() {
        return cap == 0;
    }

    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (cap + 1 > sizeLevel[currLevel])
            upSize();
        cap++;
        int pos = StdRandom.uniformInt(cap);
        arr[cap - 1] = arr[pos];
        arr[pos] = item;
    }

    public Item dequeue() {
        if (cap == 0)
            throw new NoSuchElementException();
        cap--;
        Item item = arr[cap];
        if (cap < sizeLevel[currLevel] / 4)
            downSize();
        return item;
    }

    public Item sample() {
        if (cap == 0)
            throw new NoSuchElementException();
        int pos = StdRandom.uniformInt(cap);
        return arr[pos];
    }

    @Override
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int currIndex;
        private int[] randomPermultaion;

        public ArrayIterator() {
            this.currIndex = 0;
            randomPermultaion = new int[cap];
            for (int i = 0; i < cap; i++)
                randomPermultaion[i] = i;
            StdRandom.shuffle(randomPermultaion);
        }

        @Override
        public boolean hasNext() {
            return (currIndex < cap);
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return arr[randomPermultaion[currIndex++]];
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        queue.size();
        queue.isEmpty();
        queue.size();
        queue.size();
        queue.isEmpty();
        queue.isEmpty();
        queue.enqueue(908);
        queue.dequeue();
    }
}
