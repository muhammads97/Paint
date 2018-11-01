package eg.edu.alexu.csd.oop.draw.cs41;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.test.DummyShape;

public class Engine implements DrawingEngine {
    private Shape shape;
    @Override
    public void refresh(Graphics canvas) {
        shape.draw(canvas);
    }

    @Override
    public void addShape(Shape shape) {
        // TODO Auto-generated method stub
        this.shape = shape;
    }

    @Override
    public void removeShape(Shape shape) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateShape(Shape oldShape, Shape newShape) {
        // TODO Auto-generated method stub

    }

    @Override
    public Shape[] getShapes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Class<? extends Shape>> getSupportedShapes() {
        // TODO Auto-generated method stub
        List<Class<? extends Shape>> l = new ArrayList<Class<? extends Shape>>();
        l.add(DummyShape.class);
        return l;
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
