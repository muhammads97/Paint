package eg.edu.alexu.csd.oop.draw.utilities;

public class CircularStack {
    private Object[] elements;
    private int position = 0;
    private int size = 0;
    
    public CircularStack(int size) {
        elements = new Object[size];
    }
    
    public Object peek() {
        if(size == 0) {
            return null;
        }
        int pos = position - 1;
        if(pos < 0) {
            pos = elements.length - 1;
        }
        return elements[pos];
    }
    
    public void add(Object object) {
        elements[position] = object;
        position++;
        size++;
        if(position >= elements.length) {
            position = 0;
        }
        if(size > elements.length) size = elements.length;
    }
    
    public Object pop() {
        if(size == 0) return null;
        position --;
        size--;
        if(position < 0) {
            position = elements.length - 1;
        }
        Object o = elements[position];
        elements[position] = null;
        return o;
    }
    
    public int size() {
        // TODO Auto-generated method stub
        return size;
    }
    public void clean() {
        int s = elements.length;
        elements = new Object[s];
        size = 0;
        position = 0;
    }
}
