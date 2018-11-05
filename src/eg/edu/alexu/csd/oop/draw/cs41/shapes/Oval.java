package eg.edu.alexu.csd.oop.draw.cs41.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;

public abstract class Oval extends ShapeStructure{
    public Oval(Oval o) {
        this.color = o.color;
        this.fill = o.fill;
        this.position = new Point(o.position);
        this.properties = new HashMap<String, Double>(o.properties);
    }
    
    public Oval(Double r1, Double r2) {
        this.color = Color.black;
        this.fill = Color.white;
        this.position = new Point(0, 0);
        this.properties = new HashMap<String, Double>();
        
        properties.put("x", r1);
        properties.put("y", r2);
        properties.put("stroke", 1.0);
    }
    
    @Override
    public void draw(Graphics canvas) {
        Graphics2D g2d = (Graphics2D) canvas;
        int stroke = properties.get("stroke").intValue();
        g2d.setColor(fill);
        g2d.setStroke(new BasicStroke(stroke));
        
        int x = Math.min(position.x, properties.get("x").intValue());
        int y = Math.min(position.y, properties.get("y").intValue());
        int w = Math.abs(position.x - properties.get("x").intValue());
        int h = Math.abs(position.y - properties.get("y").intValue());
        g2d.fillOval(x, y, w, h);
        
        g2d.setColor(color);
        
        g2d.drawOval(x, y, w, h);
        
    }

    @Override
    public abstract Object clone() throws CloneNotSupportedException ;

}
