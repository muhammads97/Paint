package eg.edu.alexu.csd.oop.draw.cs41.shapes;

import java.awt.Graphics;

public class Circle extends Oval implements DragableShape{

    public Circle(Circle c){
        super(c);
    }
    
    public Circle() {
        super(0.0, 0.0);
    }

    @Override
    public void draw(Graphics canvas) {
        int h = Math.abs(position.y - properties.get("y").intValue());
        int w = Math.abs(position.x - properties.get("x").intValue());
        
        int d = Math.min(h, w);
        if(position.y >= properties.get("y").intValue()) {
            h = position.y - d;
        } else {
            h = position.y + d;
        }
        if(position.x >= properties.get("x").intValue()) {
            w = position.x - d;
        } else {
            w = position.x + d;
        }
        properties.put("x", Double.valueOf(w));
        properties.put("y", Double.valueOf(h));
        super.draw(canvas);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return new Circle(this);
    }

}
