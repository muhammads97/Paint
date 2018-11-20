package eg.edu.alexu.csd.oop.draw.cs41.command;

import java.util.List;

import eg.edu.alexu.csd.oop.draw.Shape;

/**
 * @author Muhammad Salah
 * command structure 
 */
public abstract class Command {
    /**
     * shapes to operate on
     */
    public List<Shape> shapes;
    
    public Command(List<Shape> shapes) {
        this.shapes = shapes;
    }
    
    /**
     * execute the operation
     */
    abstract public void execute();
    /**
     * undo the operation
     */
    abstract public void undo();
    /**
     * redo the operation
     */
    abstract public void redo();
}
