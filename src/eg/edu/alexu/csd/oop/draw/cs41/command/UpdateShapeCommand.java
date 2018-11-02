package eg.edu.alexu.csd.oop.draw.cs41.command;

import java.util.List;

import eg.edu.alexu.csd.oop.draw.Shape;

public class UpdateShapeCommand extends Command {
    private Shape oldShape;
    private Shape newShape;
    public UpdateShapeCommand(List<Shape> shapes, Shape oldShape, Shape newShape) {
        super(shapes);
        this.oldShape = oldShape;
        this.newShape = newShape;
    }

    @Override
    public void execute() {
        this.shapes.remove(oldShape);
        this.shapes.add(newShape);
    }

    @Override
    public void undo() {
        this.shapes.remove(newShape);
        this.shapes.add(oldShape);
    }

    @Override
    public void redo() {
        this.shapes.remove(oldShape);
        this.shapes.add(newShape);
    }

}
