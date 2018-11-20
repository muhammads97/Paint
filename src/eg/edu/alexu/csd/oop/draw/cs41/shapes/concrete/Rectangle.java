package eg.edu.alexu.csd.oop.draw.cs41.shapes.concrete;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;

import eg.edu.alexu.csd.oop.draw.cs41.shapes.ShapeStructure;

public class Rectangle extends ShapeStructure {

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
        properties.put("Transperancy", 1.0);
        properties.put("Length", 0.0);
        properties.put("Width", 0.0);
    }

    
    
    @Override
    public void draw(Graphics canvas) {
        int x = position.x;
        int y = position.y;
        int h = properties.get("Length").intValue();
        int w = properties.get("Width").intValue();
        int stroke = properties.get("stroke").intValue();
        
        Graphics2D g2d = (Graphics2D) canvas;
        float alpha = properties.get("Transperancy").floatValue();
        AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alcom);
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
