package eg.edu.alexu.csd.oop.draw.cs41;

import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.cs41.command.AddShapeCommand;
import eg.edu.alexu.csd.oop.draw.cs41.command.Command;
import eg.edu.alexu.csd.oop.draw.cs41.command.RemoveShapeCommand;
import eg.edu.alexu.csd.oop.draw.cs41.command.UpdateShapeCommand;
import eg.edu.alexu.csd.oop.draw.cs41.utilities.CircularStack;
import eg.edu.alexu.csd.oop.draw.cs41.utilities.Helper;
import eg.edu.alexu.csd.oop.draw.cs41.utilities.XMLHandler;
import eg.edu.alexu.csd.oop.draw.cs41.utilities.json.JSONHandler;
import eg.edu.alexu.csd.oop.test.DummyShape;
import javafx.print.JobSettings;

/**
 * @author Muhammad Salah
 * the class provides an implementation of the drawing engine
 */
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
        installAvailableJars();
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
        path = path.toLowerCase();
        if(path.endsWith(".xml")) {
            XMLHandler xml = new XMLHandler(path, supportedShapes);
            xml.setShapes(shapes);
            xml.saveShapes();
        } else if (path.endsWith(".json")) {
            JSONHandler json = new JSONHandler(path, supportedShapes);
            json.setShapes(shapes);
            json.saveShapes();
        }
        
    }

    @Override
    public void load(String path) {
        shapes.clear();
        path = path.toLowerCase();
        if(path.endsWith(".xml")) {
            XMLHandler xml = new XMLHandler(path, supportedShapes);
            xml.loadShapes();
            shapes = xml.getShapes();
        } else if (path.endsWith(".json")) {
            JSONHandler json = new JSONHandler(path, supportedShapes);
            json.loadShapes();
            shapes = json.getShapes();
        }
    }
    @Override
    public void installPluginShape(String jarPath) {
        Helper.loadShapesFromJar(jarPath, supportedShapes);
    }
    
    /**
     * the function uses Helper class to find the classes implementing SHape interface
     * then the function appends the classes to the supported shapes list
     */
    private void fetchSupported() {
        Class<?> interfaceToFind = Shape.class;
        List<Class<?>> l = Helper.findClassesImpmenenting(interfaceToFind, interfaceToFind.getPackage());
        supportedShapes = new ArrayList<Class<? extends Shape>>();
        for(Class<?> c : l) {
            supportedShapes.add((Class<? extends Shape>)c);
        }
    }
    
    /**
     * the method uses Helper class to find the jars in the external shapes folder
     * then it uses installPluginShape to automatically install the shape class
     */
    private void installAvailableJars() {
        List<File> jars = Helper.getJars("./external shapes/");
        for(File f : jars) {
            installPluginShape(f.getPath());
        }
    }
}
