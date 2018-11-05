package eg.edu.alexu.csd.oop.draw.cs41.shapes;

import java.awt.Graphics;

public class IsoscaleTriangle extends DragableTriangle implements DragableShape{
    public IsoscaleTriangle() {
        super();
    }
    
    public IsoscaleTriangle(IsoscaleTriangle t) {
        super(t);
    }
    
    
    
    @Override
    public void draw(Graphics canvas) {
        int x = Math.min(position.x, properties.get("x").intValue());
        int y = Math.min(position.y, properties.get("y").intValue());
        int h = Math.abs(position.y - properties.get("y").intValue());
        int w = Math.abs(position.x - properties.get("x").intValue());
        xP[0] = x + w/2;
        yP[0] = y;
        xP[1] = x;
        xP[2] = x + w;
        yP[1] = y + h;
        yP[2] = y + h;
        super.draw(canvas);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new IsoscaleTriangle(this);
    }

}
