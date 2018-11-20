package eg.edu.alexu.csd.oop.draw.cs41.command;

import java.util.List;

import eg.edu.alexu.csd.oop.draw.Shape;

/**
 * @author Muhammad Salah
 * replace the old shape with updated one
 */
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
        if(this.shapes.remove(oldShape)) {
            this.shapes.add(newShape);
        }
        
    }

    @Override
    public void undo() {
        if(this.shapes.remove(newShape)) {
            this.shapes.add(oldShape);
        }
        
    }

    @Override
    public void redo() {
        if(this.shapes.remove(oldShape)) {
            this.shapes.add(newShape);
        }
    }

}
