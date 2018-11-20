package eg.edu.alexu.csd.oop.draw.cs41.shapes.concrete;

import java.awt.Graphics;

import eg.edu.alexu.csd.oop.draw.cs41.shapes.DragableTriangle;

public class RightTriangle extends DragableTriangle{
    public RightTriangle() {
        super();
    }
    
    public RightTriangle(RightTriangle t) {
        super(t);
    }
    
    
    
    @Override
    public void draw(Graphics canvas) {
        int x = position.x;
        int y = position.y;
        int h = properties.get("Length").intValue();
        int w = properties.get("Width").intValue();
        xP[0] = x;
        yP[0] = y;
        xP[1] = x;
        xP[2] = x + w;
        yP[1] = y + h;
        yP[2] = y + h;
        super.draw(canvas);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new RightTriangle(this);
    }

}
