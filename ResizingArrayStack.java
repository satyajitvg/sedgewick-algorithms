import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Satyajit
 */
public class ResizingArrayStack<Item> implements Iterable<Item>{
    private Item[] items;
    private int n;

    public ResizingArrayStack() {
        // this ugly cast is required since java does not allow generic array creation
        items = (Item[]) new Object[2];
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    // resize underlying array holding items
    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i<n; i++) {
            temp[i] = items[i];
        }
        items = temp;

    }

    public void push(Item item) {
        if (n == items.length) resize(2*items.length);
        items[n++] = item;
    }

    public Item pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack empty");
        Item item = items[n-1];
        items[n-1] = null; // to avoid loitering, allow garbage collection
        n--;
        if (n > 0 && n == items.length/4) resize(items.length/2);
        return item;
    }

    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack empty");
        return items[n-1];
    }

    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    private class ReverseArrayIterator implements Iterator<Item> {
        private int i;

        public ReverseArrayIterator() {
            i = n - 1;
        }

        public boolean hasNext() {
            return i >= 0;
        }

        public Item next(){
            if (!hasNext()) throw new NoSuchElementException();
            return items[i--];
        }
    }

    public static void main(String[] args) {
        ResizingArrayStack<String> stack = new ResizingArrayStack<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                stack.push(item);
            }
            else if (!stack.isEmpty()) {
                StdOut.print(stack.pop() + " ");
            }
        }
    }



}
