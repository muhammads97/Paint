package eg.edu.alexu.csd.oop.draw.cs41.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;

public abstract class ShapeStructure implements Shape{
    protected Point position;
    protected Map<String, Double> properties;
    protected Color color;
    protected Color fill;
    
    public void setPosition(Point position) {
        this.position = position;
    }
    public Point getPosition() {
        return position;
    }

    public void setProperties(Map<String, Double> properties) {
        this.properties = properties;
    }
    
    public Map<String, Double> getProperties() {
        return properties;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    public Color getColor() {
        return color;
    }

    public void setFillColor(Color color) {
        this.fill = color;
    }
    public Color getFillColor() {
        return fill;
    }

    public abstract void draw(Graphics canvas); // redraw the shape on the canvas

    public abstract Object clone() throws CloneNotSupportedException;
}
