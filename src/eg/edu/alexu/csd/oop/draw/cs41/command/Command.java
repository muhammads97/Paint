package eg.edu.alexu.csd.oop.draw.cs41.command;

import java.util.List;

import eg.edu.alexu.csd.oop.draw.Shape;

public abstract class Command {
    public List<Shape> shapes;
    
    public Command(List<Shape> shapes) {
        this.shapes = shapes;
    }
    
    abstract public void execute();
    abstract public void undo();
    abstract public void redo();
}
