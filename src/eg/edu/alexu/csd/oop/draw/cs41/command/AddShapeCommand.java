package eg.edu.alexu.csd.oop.draw.cs41.command;

import java.util.List;

import eg.edu.alexu.csd.oop.draw.Shape;

public class AddShapeCommand extends Command{
    private Shape shape;
    public AddShapeCommand(List<Shape> shapes, Shape shape) {
        super(shapes);
        this.shape = shape;
    }
    @Override
    public void execute() {
        this.shapes.add(shape);
    }

    @Override
    public void undo() {
        this.shapes.remove(shape);
    }

    @Override
    public void redo() {
        this.shapes.add(shape);
    }

}
