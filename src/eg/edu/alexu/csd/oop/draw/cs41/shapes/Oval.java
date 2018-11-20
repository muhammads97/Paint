package eg.edu.alexu.csd.oop.draw.cs41.shapes;

import java.awt.AlphaComposite;
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
        
        properties.put("Width", r1);
        properties.put("Length", r2);
        properties.put("stroke", 1.0);
        properties.put("Transperancy", 1.0);
    }
    
    @Override
    public void draw(Graphics canvas) {
        Graphics2D g2d = (Graphics2D) canvas;
        int stroke = properties.get("stroke").intValue();
        float alpha = properties.get("Transperancy").floatValue();
        AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alcom);
        g2d.setColor(fill);
        g2d.setStroke(new BasicStroke(stroke));
        
        int x = position.x;
        int y = position.y;
        int w = properties.get("Width").intValue();
        int h = properties.get("Length").intValue();
        g2d.fillOval(x, y, w, h);
        
        g2d.setColor(color);
        
        g2d.drawOval(x, y, w, h);
        
    }

    @Override
    public abstract Object clone() throws CloneNotSupportedException ;

}
