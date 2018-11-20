package eg.edu.alexu.csd.oop.draw.cs41.utilities;

/**
 * @author Muhammad Salah
 * a stack data structure that has a limited size and unlimited number of adding operations
 * implemented using a circular array concept
 */
/**
 * @author Muhammad Salah
 *
 */
/**
 * @author Muhammad Salah
 *
 */
/**
 * @author Muhammad Salah
 *
 */
public class CircularStack {
    /**
     * Object array to hold the stack elements
     */
    private Object[] elements;
    /**
     * iterator position
     */
    private int position = 0;
    /**
     * current size
     */
    private int size = 0;
    
    public CircularStack(int size) {
        elements = new Object[size];
    }
    
    /**
     * @return the object on top of the stack
     */
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
    
    /**
     * @param object adds an object to the stack
     */
    public void add(Object object) {
        elements[position] = object;
        position++;
        size++;
        //if position reached the limit go to position zero and start over
        if(position >= elements.length) {
            position = 0;
        }
        //size can't exceed stack size
        if(size > elements.length) size = elements.length;
    }
    
    /**
     * @return removes and returns the top object of the stack
     */
    public Object pop() {
        //stack is empty?
        if(size == 0) return null;
        position --;
        size--;
        //if position reached zero go to the last position of the stack
        if(position < 0) {
            position = elements.length - 1;
        }
        Object o = elements[position];
        elements[position] = null;
        return o;
    }
    
    
    /**
     * @return stack current size
     */
    public int size() {
        return size;
    }
    
    
    /**
     * empty the stack
     */
    public void clean() {
        int s = elements.length;
        elements = new Object[s];
        size = 0;
        position = 0;
    }
}
