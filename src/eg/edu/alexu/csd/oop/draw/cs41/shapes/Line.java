package eg.edu.alexu.csd.oop.draw.cs41.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;

public class Line extends ShapeStructure implements DragableShape{
    public Line(Line l){
        this.fill = l.fill;
        this.color = l.color;
        this.position = l.position;
        this.properties = new HashMap<String, Double>(l.properties);
    }
    
    public Line(){
        color = Color.black;
        fill = null;
        properties = new HashMap<String, Double>();
        properties.put("x", 0.0);
        properties.put("y", 0.0);
        properties.put("stroke", 1.0);
    }
    
    @Override
    public void draw(Graphics canvas) {
        canvas.setColor(color);
        int x1 = position.x;
        int y1 = position.y;
        int x2 = properties.get("x").intValue();
        int y2 = properties.get("y").intValue();
        
        Graphics2D g2d = (Graphics2D) canvas;
        g2d.setStroke(new BasicStroke(properties.get("stroke").intValue()));
        canvas.drawLine(x1, y1, x2, y2);
        
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Line(this);
    }
    
}
