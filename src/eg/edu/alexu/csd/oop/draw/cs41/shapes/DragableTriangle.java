package eg.edu.alexu.csd.oop.draw.cs41.shapes;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;

/**
 * @author Muhammad Salah
 * provides an implementation of the drawing function of a triangle
 * the triangle can be drawn using drag
 */
public abstract class DragableTriangle extends ShapeStructure {
    protected int[] xP = {0,0,0};
    protected int[] yP = {0,0,0};
    
    public DragableTriangle(DragableTriangle dt) {
        this.color = dt.color;
        this.fill = dt.fill;
        this.position = new Point(dt.position);
        this.properties = new HashMap<String, Double>(dt.properties);
    }
    
    public DragableTriangle() {
        this.color = Color.black;
        this.fill = Color.white;
        this.position = new Point(0, 0);
        this.properties = new HashMap<String, Double>();
        properties.put("stroke", 1.0);
        properties.put("Transperancy", 1.0);
        properties.put("Width", 0.0);
        properties.put("Length", 0.0);
    }
    
    @Override
    public void draw(Graphics canvas) {
        int stroke = properties.get("stroke").intValue();
        Graphics2D g2d = (Graphics2D) canvas;
        float alpha = properties.get("Transperancy").floatValue();
        AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alcom);
        g2d.setColor(fill);
        g2d.setStroke(new BasicStroke(stroke));
        g2d.fillPolygon(xP, yP, 3);
        g2d.setColor(color);
        g2d.drawPolygon(xP, yP, 3);
        
    }

}
