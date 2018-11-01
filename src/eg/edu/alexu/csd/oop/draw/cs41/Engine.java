package eg.edu.alexu.csd.oop.draw.cs41;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.test.DummyShape;
import eg.edu.alexu.csd.oop.draw.utilities.ReflectionHelper;

public class Engine implements DrawingEngine {
    private List<Shape> shapes;
    
    public Engine() {
        shapes = new ArrayList<Shape>();
    }
    @Override
    public void refresh(Graphics canvas) {
        for (Shape shape: shapes) {
            shape.draw(canvas);
        }
    }

    @Override
    public void addShape(Shape shape) {
        // TODO Auto-generated method stub
        shapes.add(shape);
    }

    @Override
    public void removeShape(Shape shape) {
        shapes.remove(shape);
    }

    @Override
    public void updateShape(Shape oldShape, Shape newShape) {
        shapes.set(shapes.indexOf(oldShape), newShape);
    }

    @Override
    public Shape[] getShapes() {
        // TODO Auto-generated method stub
        return (Shape[])shapes.toArray();
    }

    @Override
    public List<Class<? extends Shape>> getSupportedShapes() {
        Class<?> interfaceToFind = Shape.class;
        List<Class<?>> l = ReflectionHelper.findClassesImpmenenting(interfaceToFind, interfaceToFind.getPackage());
        l.add(DummyShape.class);
        List<Class <? extends Shape>> classes = new ArrayList<Class<? extends Shape>>();
        for(Class<?> c : l) {
            classes.add((Class<? extends Shape>)c);
        }
        return classes;
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub

    }

    @Override
    public void redo() {
        // TODO Auto-generated method stub

    }

    @Override
    public void save(String path) {
        // TODO Auto-generated method stub

    }

    @Override
    public void load(String path) {
        // TODO Auto-generated method stub

    }

}
