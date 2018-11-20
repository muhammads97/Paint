package eg.edu.alexu.csd.oop.draw.cs41.gui.tools;

import java.awt.Color;
import java.awt.Point;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.cs41.Engine;
import eg.edu.alexu.csd.oop.draw.cs41.shapes.interfaces.FreeShape;
import eg.edu.alexu.csd.oop.draw.cs41.shapes.interfaces.PointsShape;
import eg.edu.alexu.csd.oop.draw.cs41.utilities.Helper;

public class ShapeHandler {
    private boolean drag = false;
    private boolean points = false;
    private boolean freeShape = false;
    private Shape shape;
    private int clicks = 0;
    
    public ShapeHandler(Shape shape) {
        this.shape = shape;
        if(FreeShape.class.isAssignableFrom(shape.getClass())) {
            freeShape = true;
        } else if (PointsShape.class.isAssignableFrom(shape.getClass())) {
            points = true;
        } else {
            drag = true;
        }
    }
    
    public boolean isFreeShape() {
        return freeShape;
    }
    
    public boolean isDrag() {
        return drag;
    }
    
    public boolean isPoints() {
        return points;
    }
    
    public boolean getFreeShape() {
        return freeShape;
    }
    
    public void drag(Point s, Point e, boolean shift) {
        if(drag) {
            int x = Math.min(e.x, s.x);
            int y = Math.min(e.y, s.y);
            int w = Math.abs(e.x - s.x);
            int h = Math.abs(e.y - s.y);
            if(shift) {
                int d = Math.min(w, h);
                if(s.x > e.getX()) {
                    x = s.x - d;
                } else {
                    x = s.x;
                }
                if(s.y > e.y) {
                    y = s.y - d;
                } else {
                    y = s.y;
                }
                w = h = Math.min(h, w);
            }
            shape.setPosition(new Point(x, y));
            Map<String, Double> prop = shape.getProperties();
            prop.put("Width", Double.valueOf(w));
            prop.put("Length", Double.valueOf(h));
        } else if (freeShape) {
            shape.setPosition(new Point(s));
            int npoints = shape.getProperties().get("nPoints").intValue();
            npoints ++;
            shape.getProperties().put("x" + npoints, e.getX());
            shape.getProperties().put("y" + npoints, e.getY());
            shape.getProperties().put("nPoints", Double.valueOf(npoints));
        }
    }
    
    public boolean pointsPress(Point s) {
        if(firstClick()) {
            clicks++;
            shape.setPosition(s);
            return false;
        } else {
            clicks++;
            shape.getProperties().put("x" + clicks, s.getX());
            shape.getProperties().put("y" + clicks, s.getY());
            int npoints = shape.getProperties().get("nPoints").intValue();
            if(clicks == npoints) {
                return true;
            } else {
                return false;
            }
        }
    }
    
    public boolean firstClick() {
        return clicks == 0;
    }
    
    public void move(Point e) {
        if(points) {
            int npoints = shape.getProperties().get("nPoints").intValue();
            if(clicks < npoints) {
                if(clicks == 0) return;
                shape.getProperties().put("x" + (clicks+1), e.getX());
                shape.getProperties().put("y" + (clicks+1), e.getY());
            }
        }
    }
    

    public static Shape search(Shape[] shapes, Point click) {
        for(Shape shape : shapes) {
            if(PointsShape.class.isAssignableFrom(shape.getClass()) 
                    || FreeShape.class.isAssignableFrom(shape.getClass())) {
                
                int x1 = shape.getPosition().x;
                int y1 = shape.getPosition().y;
                int x2 = shape.getPosition().x;
                int y2 = shape.getPosition().y;
                int nPoints = shape.getProperties().get("nPoints").intValue();
                for(int i = 2; i <= nPoints; i++) {
                    int x,y;
                    x = shape.getProperties().get("x" + i).intValue();
                    y = shape.getProperties().get("y" + i).intValue();
                    x1 = Math.min(x, x1);
                    y1 = Math.min(y, y1);
                    x2 = Math.max(x, x2);
                    y2 = Math.max(y, y2);
                }
                if(click.x <= x2 && click.x >= x1) {
                    if(click.y <= y2 && click.y >= y1) {
                        return shape;
                        
                    }
                }
            } else {
                int x1 = shape.getPosition().x;
                int y1 = shape.getPosition().y;
                
                int x2 = shape.getProperties().get("Width").intValue();
                x2 += x1;
                int y2 = shape.getProperties().get("Length").intValue();
                y2 += y1;
                if(click.x <= x2 && click.x >= x1) {
                    if(click.y <= y2 && click.y >= y1) {
                        return shape;
                    }
                }
            }
        }
        return null;
    }
    
    
    
}
