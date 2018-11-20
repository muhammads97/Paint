package eg.edu.alexu.csd.oop.draw.cs41.shapes.concrete;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.cs41.shapes.ShapeStructure;
import eg.edu.alexu.csd.oop.draw.cs41.shapes.interfaces.FreeShape;

public class HandDrawShape extends ShapeStructure implements FreeShape {

    public HandDrawShape(HandDrawShape handDrawShape) {
        this.color = handDrawShape.color;
        this.fill = handDrawShape.fill;
        this.position = new Point(handDrawShape.position);
        this.properties = new HashMap<String, Double>(handDrawShape.properties);
    }
    
    public HandDrawShape() {
        this.color = Color.BLACK;
        this.fill = null;
        this.position = new Point(0, 0);
        this.properties = new HashMap<String, Double>();
        
        properties.put("stroke", 1.0);
        properties.put("Transperancy", 1.0);
        properties.put("nPoints", 1.0);
    }
    
    @Override
    public void draw(Graphics canvas) {
        Graphics2D g2d = (Graphics2D) canvas;
        float alpha = properties.get("Transperancy").floatValue();
        AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alcom);
        g2d.setColor(color);
        int stroke = properties.get("stroke").intValue();
        g2d.setStroke(new BasicStroke(stroke));
        Point current = position;
        int npoints = properties.get("nPoints").intValue();
        for(int i = 2; i < npoints; i++) {
            int x = properties.get("x" + i).intValue();
            int y = properties.get("y" + i).intValue();
            g2d.drawLine(current.x, current.y, x, y);
            current = new Point(x, y);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return new HandDrawShape(this);
    }

}
