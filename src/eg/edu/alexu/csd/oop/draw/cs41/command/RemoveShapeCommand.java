package eg.edu.alexu.csd.oop.draw.cs41.command;

import java.util.List;

import eg.edu.alexu.csd.oop.draw.Shape;

/**
 * @author Muhammad Salah
 * delete shape from the canvas
 */
public class RemoveShapeCommand extends Command {
    private Shape shape;
    
    public RemoveShapeCommand(List<Shape> shapes, Shape shape) {
        super(shapes);
        this.shape = shape;
    }

    @Override
    public void execute() {
        this.shapes.remove(shape);
    }

    @Override
    public void undo() {
        this.shapes.add(shape);
    }

    @Override
    public void redo() {
        this.shapes.remove(shape);
    }

}
