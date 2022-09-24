import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private final ListNode firstList;
    private final ListNode lastList;
    private int cap;

    public Deque() {
        firstList = new ListNode(null);
        lastList = new ListNode(null);
        firstList.next = lastList;
        lastList.previous = firstList;
        cap = 0;
    }

    public int size() {
        return cap;
    }

    public boolean isEmpty() {
        return cap == 0;
    }

    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        cap++;
        ListNode newNode = new ListNode(item);
        newNode.next = firstList.next;
        firstList.next = newNode;
        newNode.previous = firstList;
        newNode.next.previous = newNode;
    }

    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        cap++;
        ListNode newNode = new ListNode(item);
        newNode.previous = lastList.previous;
        lastList.previous = newNode;
        newNode.next = lastList;
        newNode.previous.next = newNode;
    }

    public Item removeFirst() {
        if (cap == 0)
            throw new NoSuchElementException();
        cap--;
        Item removedItem = firstList.next.data;
        firstList.next = firstList.next.next;
        firstList.next.previous = firstList;
        return removedItem;
    }

    public Item removeLast() {
        if (cap == 0)
            throw new NoSuchElementException();
        cap--;
        Item removedItem = lastList.previous.data;
        lastList.previous = lastList.previous.previous;
        lastList.previous.next = lastList;
        return removedItem;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator(firstList.next);
    }

    private class ListNode {
        public Item data;
        public ListNode next;
        public ListNode previous;

        ListNode(Item data) {
            this.data = data;
            next = null;
            previous = null;
        }
    }

    private class ListIterator implements Iterator<Item> {
        private ListNode currNode;

        public ListIterator(ListNode currNode) {
            this.currNode = currNode;
        }

        @Override
        public boolean hasNext() {
            return (currNode.next != null);
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = currNode.data;
            currNode = currNode.next;
            return item;
        }
    }

    public static void main(String[] args) {

    }
}


