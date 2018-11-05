package eg.edu.alexu.csd.oop.draw.cs41.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;

public abstract class Polygon extends ShapeStructure{
    private int nPoints;
    protected int[] xPoints;
    protected int[] yPoints;
    
    public Polygon(Polygon p) {
        this.color = p.color;
        this.fill = p.fill;
        this.position = new Point(p.position);
        this.properties = new HashMap<String, Double>(p.properties);
    }
    
    public Polygon(int nPoints) {
        this.nPoints = nPoints;
        xPoints = new int[nPoints];
        yPoints = new int[nPoints];
        this.color = Color.BLACK;
        this.fill = Color.WHITE;
        this.position = new Point(0, 0);
        this.properties = new HashMap<String, Double>();
        properties.put("stroke", 1.0);
        for(int i = 1; i <= nPoints; i++) {
            properties.put("Length" + i, 0.0);
            properties.put("Length" + i, 0.0);
        }
    }
    
    @Override
    public void draw(Graphics canvas) {
        Graphics2D g2d = (Graphics2D) canvas;
        int stroke = properties.get("stroke").intValue();
        g2d.setColor(fill);
        g2d.setStroke(new BasicStroke(stroke));
        g2d.fillPolygon(xPoints, yPoints, nPoints);
        g2d.setColor(color);
        g2d.drawPolygon(xPoints, yPoints, nPoints);
    }
}
