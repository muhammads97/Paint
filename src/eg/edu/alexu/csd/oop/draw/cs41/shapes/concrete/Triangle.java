package eg.edu.alexu.csd.oop.draw.cs41.shapes.concrete;

import eg.edu.alexu.csd.oop.draw.cs41.shapes.Polygon;
import eg.edu.alexu.csd.oop.draw.cs41.shapes.interfaces.PointsShape;

public class Triangle extends Polygon implements PointsShape{
    public Triangle() {
        super(3);
    }
    
    public Triangle(Triangle p) {
        super(p);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return new Triangle(this);
    }
}
