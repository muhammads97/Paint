package eg.edu.alexu.csd.oop.draw.cs41.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;

/**
 * @author Muhammad Salah
 * an apstract class that implements the shape interface
 * the class implements the main functionalities for all shapes
 */
public abstract class ShapeStructure implements Shape{
    protected Point position;
    protected Map<String, Double> properties;
    protected Color color;
    protected Color fill;
    
    @Override
    public void setPosition(Point position) {
        this.position = position;
    }
    
    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void setProperties(Map<String, Double> properties) {
        this.properties = properties;
    }
    
    @Override
    public Map<String, Double> getProperties() {
        return properties;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }
    
    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setFillColor(Color color) {
        this.fill = color;
    }
    
    @Override
    public Color getFillColor() {
        return fill;
    }

    public abstract void draw(Graphics canvas); // redraw the shape on the canvas

    public abstract Object clone() throws CloneNotSupportedException;
    
    @Override
    public boolean equals(Object shape) {
        Shape s = (Shape) shape;
        return ((this.color.equals(s.getColor())) && this.fill.equals(s.getFillColor()) && this.position.equals(s.getPosition()) && this.properties.equals(s.getProperties()));
       // return super.equals(shape);
    }
}
