package eg.edu.alexu.csd.oop.draw.cs41.shapes;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author Muhammad Salah
 * abstract class implements the main functionality of a polygon shape
 */
public abstract class Polygon extends ShapeStructure{
    private int nPoints;
    protected int[] xPoints;
    protected int[] yPoints;
    
    public Polygon(Polygon p) {
        this.color = p.color;
        this.fill = p.fill;
        this.position = new Point(p.position);
        this.properties = new HashMap<String, Double>(p.properties);
        this.nPoints = p.nPoints;
        this.xPoints = p.xPoints;
        this.yPoints = p.yPoints;
    }
    
    public Polygon(int nPoints) {
        this.nPoints = nPoints;
        this.color = Color.BLACK;
        this.fill = Color.WHITE;
        this.position = new Point(0, 0);
        this.properties = new HashMap<String, Double>();
        properties.put("stroke", 1.0);
        properties.put("Transperancy", 1.0);
        properties.put("nPoints", Double.valueOf(nPoints));
        for(int i = 2; i <= nPoints; i++) {
            properties.put("x" + i, -1.0);
            properties.put("y" + i, -1.0);
        }
    }
    
    @Override
    public void draw(Graphics canvas) {
        fillArrays();
        Graphics2D g2d = (Graphics2D) canvas;
        int stroke = properties.get("stroke").intValue();
        float alpha = properties.get("Transperancy").floatValue();
        AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alcom);
        g2d.setColor(fill);
        g2d.setStroke(new BasicStroke(stroke));
        g2d.fillPolygon(xPoints, yPoints, xPoints.length);
        g2d.setColor(color);
        g2d.drawPolygon(xPoints, yPoints, xPoints.length);
    }
    
    /**
     * used to fill the points arrays from the properties map
     */
    private void fillArrays() {
        xPoints = new int[nPoints];
        yPoints = new int[nPoints];
        xPoints[0] = position.x;
        yPoints[0] = position.y;
        for(int i = 2; i <= nPoints; i++) {
            int x = properties.get("x" + i).intValue();
            int y = properties.get("y" + i).intValue();
            if(x == -1) {
                xPoints = Arrays.copyOf(xPoints, i - 1);
                yPoints = Arrays.copyOf(yPoints, i - 1);
//                System.out.println(xPoints.length);
                break;
            }
            xPoints[i - 1] = x;
            yPoints[i - 1] = y;
        }
    }
}
