package eg.edu.alexu.csd.oop.draw.cs41.shapes.concrete;

import eg.edu.alexu.csd.oop.draw.cs41.shapes.Polygon;
import eg.edu.alexu.csd.oop.draw.cs41.shapes.interfaces.PointsShape;

/**
 * @author Muhammad Salah
 *
 */
public class Line extends Polygon implements PointsShape{

    public Line() {
        super(2);
    }
    
    public Line(Line p) {
        super(p);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Line(this);
    }

}
