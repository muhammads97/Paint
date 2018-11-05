package eg.edu.alexu.csd.oop.draw.cs41.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;

public class Rectangle extends ShapeStructure implements DragableShape{

    public Rectangle(Rectangle r) {
        this.color = r.color;
        this.fill = r.fill;
        this.position = new Point(r.position);
        this.properties = new HashMap<String, Double>(r.properties);
    }
    
    public Rectangle() {
        this.color = Color.BLACK;
        this.fill = Color.WHITE;
        this.position = new Point(0, 0);
        this.properties = new HashMap<String, Double>();
        properties.put("stroke", 1.0);
        properties.put("x", 0.0);
        properties.put("y", 0.0);
    }

    
    
    @Override
    public void draw(Graphics canvas) {
        int x = Math.min(position.x, properties.get("x").intValue());
        int y = Math.min(position.y, properties.get("y").intValue());
        int h = Math.abs(position.y - properties.get("y").intValue());
        int w = Math.abs(position.x - properties.get("x").intValue());
        int stroke = properties.get("stroke").intValue();
        
        position = new Point(x, y);
        properties.put("x", Double.valueOf(x + w));
        properties.put("y", Double.valueOf(y + h));
        
        Graphics2D g2d = (Graphics2D) canvas;
        g2d.setColor(fill);
        g2d.setStroke(new BasicStroke(stroke));
        g2d.fillRect(x, y, w, h);
        g2d.setColor(color);
        g2d.drawRect(x, y, w, h);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Rectangle(this);
    }

}
