package eg.edu.alexu.csd.oop.draw.cs41;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.cs41.command.AddShapeCommand;
import eg.edu.alexu.csd.oop.draw.cs41.command.Command;
import eg.edu.alexu.csd.oop.draw.cs41.command.RemoveShapeCommand;
import eg.edu.alexu.csd.oop.draw.cs41.command.UpdateShapeCommand;
import eg.edu.alexu.csd.oop.draw.utilities.CircularStack;
import eg.edu.alexu.csd.oop.draw.utilities.Helper;

public class Engine implements DrawingEngine {
    private List<Shape> shapes;
    private List<Class <? extends Shape>> supportedShapes;
    CircularStack undoStack;
    CircularStack redoStack;
    
    public Engine() {
        shapes = new ArrayList<Shape>();
        undoStack = new CircularStack(20);
        redoStack = new CircularStack(20);
        fetchSupported();
    }
    @Override
    public void refresh(Graphics canvas) {
        for (Shape shape: shapes) {
            shape.draw(canvas);
        }
    }

    @Override
    public void addShape(Shape shape) {
        AddShapeCommand add = new AddShapeCommand(shapes, shape);
        add.execute();
        undoStack.add(add);
        redoStack.clean();
    }

    @Override
    public void removeShape(Shape shape) {
        RemoveShapeCommand cmd = new RemoveShapeCommand(shapes, shape);
        cmd.execute();
        undoStack.add(cmd);
        redoStack.clean();
    }

    @Override
    public void updateShape(Shape oldShape, Shape newShape) {
        UpdateShapeCommand cmd = new UpdateShapeCommand(shapes, oldShape, newShape);
        cmd.execute();
        undoStack.add(cmd);
        redoStack.clean();
    }

    @Override
    public Shape[] getShapes() {
        Shape[] a = new Shape[shapes.size()];
        return shapes.toArray(a);
    }

    @Override
    public List<Class<? extends Shape>> getSupportedShapes() {
        return supportedShapes;
    }

    @Override
    public void undo() {
        if(undoStack.size() == 0) return;
        Command addCmd = (Command) undoStack.pop();
        addCmd.undo();
        redoStack.add(addCmd);
    }

    @Override
    public void redo() {
        if(redoStack.size() == 0) return;
        Command addCmd = (Command) redoStack.pop();
        addCmd.redo();
        undoStack.add(addCmd);
    }

    @Override
    public void save(String path) {
        // TODO Auto-generated method stub

    }

    @Override
    public void load(String path) {
        // TODO Auto-generated method stub

    }
    @Override
    public void installPluginShape(String jarPath) {
        Helper.loadShapesFromJar(jarPath, supportedShapes);
    }
    
    private void fetchSupported() {
        Class<?> interfaceToFind = Shape.class;
        List<Class<?>> l = Helper.findClassesImpmenenting(interfaceToFind, interfaceToFind.getPackage());
        supportedShapes = new ArrayList<Class<? extends Shape>>();
        for(Class<?> c : l) {
            supportedShapes.add((Class<? extends Shape>)c);
        }
    }

}
