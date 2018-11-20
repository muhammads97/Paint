package eg.edu.alexu.csd.oop.draw.cs41.shapes.concrete;

import eg.edu.alexu.csd.oop.draw.cs41.shapes.Polygon;
import eg.edu.alexu.csd.oop.draw.cs41.shapes.interfaces.PointsShape;

/**
 * @author Muhammad Salah
 * a triangle is a polygon shape with three points
 */
public class Triangle extends Polygon implements PointsShape{
    public Triangle() {
        super(3);
    }
    
    public Triangle(Triangle p) {
        super(p);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Triangle(this);
    }
}
