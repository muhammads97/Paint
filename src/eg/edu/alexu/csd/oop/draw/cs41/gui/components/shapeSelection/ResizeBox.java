package eg.edu.alexu.csd.oop.draw.cs41.gui.components.shapeSelection;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.TransferHandler;
import javax.swing.event.MouseInputListener;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.cs41.shapes.interfaces.FreeShape;
import eg.edu.alexu.csd.oop.draw.cs41.shapes.interfaces.PointsShape;

public class ResizeBox extends JPanel implements MouseMotionListener, MouseListener{
    private Shape shape;
    private Selection selection;
    private int boxNum;
    
    public ResizeBox(int x, int y, int w, int h, int boxNum, Selection s, Shape shape) {
        setBounds(x, y, w, h);
        setOpaque(true);
        this.selection = s;
        this.shape = shape;
        this.boxNum = boxNum;
        switch(boxNum) {
            case 0:
                this.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
                break;
            case 1:
                this.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
                break;
            case 2:
                this.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
                break;
            case 3:
                this.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
                break;
            case 4:
                this.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
                break;
            case 5:
                this.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
                break;
            case 6:
                this.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
                break;
            case 7:
                this.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
                break;
        }
        addMouseMotionListener(this);
        addMouseListener(this);
    }        
    
    
    
    @Override
    protected void paintComponent(Graphics canvas) {
        //super.paintComponent(canvas);
        Graphics2D g2d = (Graphics2D) canvas;
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.white);
        g2d.fillRect(0 , 0, getWidth(), getHeight());
        g2d.setColor(Color.black);
        g2d.drawRect(0 , 0, getWidth(), getHeight());
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        switch(boxNum) {
            case 0:
                stretchUp(y);
                stretchLeft(x);
                break;
            case 1:
                stretchUp(y);
                break;
            case 2:
                stretchUp(y);
                stretchRight(x);
                break;
            case 3:
                stretchRight(x);
                break;
            case 4:
                stretchDown(y);
                stretchRight(x);
                break;
            case 5:
                stretchDown(y);
                break;
            case 6:
                stretchDown(y);
                stretchLeft(x);
                break;
            case 7:
                stretchLeft(x);
                break;
        }
        
    }

    
    private void stretchUp(int yMouse) {
        if(yMouse > 0 && selection.getHeight() < 10) return;
        Double factor = (Double.valueOf(selection.getHeight() - yMouse))/ selection.getHeight();
        if(PointsShape.class.isAssignableFrom(shape.getClass()) 
                || FreeShape.class.isAssignableFrom(shape.getClass())) {
            int ymax = shape.getPosition().y;
            int shift = 0;
            int npoints = shape.getProperties().get("nPoints").intValue();
            shape.getPosition().y *= factor;
            shift = shape.getPosition().y - ymax;
            for(int i = 2; i <= npoints; i++) {
                int y = shape.getProperties().get("y" + i).intValue();
                if(y > ymax) {
                    ymax = y;
                    y *= factor;
                    shift = y - ymax;
                } else {
                    y *= factor;
                }
                
                shape.getProperties().put("y" + i, Double.valueOf(y));
            }
            
            shape.getPosition().y -= shift;
            for(int i = 2; i <= npoints; i++) {
                int y = shape.getProperties().get("y" + i).intValue();
                y -= shift;
                shape.getProperties().put("y" + i, Double.valueOf(y));
            }
        } else {
            int h = shape.getProperties().get("Length").intValue();
            int shift = h;
            h *= factor;
            shape.getProperties().put("Length", Double.valueOf(h));
            shift = h - shift;
            shape.getPosition().y -= shift;
            
        }
        this.selection.repaint();
    }
    
    private void stretchLeft(int xMouse) {
        if(xMouse > 0 && selection.getWidth() < 10) return;
        Double factor = (Double.valueOf(selection.getWidth() - xMouse))/ selection.getWidth();
        if(PointsShape.class.isAssignableFrom(shape.getClass()) 
                || FreeShape.class.isAssignableFrom(shape.getClass())) {
            int xmax = shape.getPosition().x;
            int shift = 0;
            int npoints = shape.getProperties().get("nPoints").intValue();
            shape.getPosition().x *= factor;
            shift = shape.getPosition().x - xmax;
            for(int i = 2; i <= npoints; i++) {
                int x = shape.getProperties().get("x" + i).intValue();
                if(x > xmax) {
                    xmax = x;
                    x *= factor;
                    shift = x - xmax;
                } else {
                    x *= factor;
                }
                
                shape.getProperties().put("x" + i, Double.valueOf(x));
            }
            
            shape.getPosition().x -= shift;
            for(int i = 2; i <= npoints; i++) {
                int x = shape.getProperties().get("x" + i).intValue();
                x -= shift;
                shape.getProperties().put("x" + i, Double.valueOf(x));
            }
        } else {
            int w = shape.getProperties().get("Width").intValue();
            int shift = w;
            w *= factor;
            shape.getProperties().put("Width", Double.valueOf(w));
            shift = w - shift;
            shape.getPosition().x -= shift;
        }
        this.selection.repaint();
    }
    
    private void stretchDown(int yMouse) {
        if(yMouse < 0 && selection.getHeight() < 10) return;
        Double factor = (Double.valueOf(selection.getHeight() + yMouse))/ selection.getHeight();
        if(PointsShape.class.isAssignableFrom(shape.getClass()) 
                || FreeShape.class.isAssignableFrom(shape.getClass())) {
            int ymin = shape.getPosition().y;
            int shift = 0;
            int npoints = shape.getProperties().get("nPoints").intValue();
            shape.getPosition().y *= factor;
            shift = shape.getPosition().y - ymin;
            for(int i = 2; i <= npoints; i++) {
                int y = shape.getProperties().get("y" + i).intValue();
                if(y < ymin) {
                    ymin = y;
                    y *= factor;
                    shift = y - ymin;
                } else {
                    y *= factor;
                }
                shape.getProperties().put("y" + i, Double.valueOf(y));
            }
            
            shape.getPosition().y -= shift;
            for(int i = 2; i <= npoints; i++) {
                int y = shape.getProperties().get("y" + i).intValue();
                y -= shift;
                shape.getProperties().put("y" + i, Double.valueOf(y));
            }
        } else {
            int h = shape.getProperties().get("Length").intValue();
            h *= factor;
            shape.getProperties().put("Length", Double.valueOf(h));
        }
        this.selection.repaint();
    }

    private void stretchRight(int xMouse) {
        if(xMouse < 0 && selection.getHeight() < 10) return;
        Double factor = (Double.valueOf(selection.getWidth() + xMouse))/ selection.getWidth();
        if(PointsShape.class.isAssignableFrom(shape.getClass())
                || FreeShape.class.isAssignableFrom(shape.getClass())) {
            int xmin = shape.getPosition().x;
            int shift = 0;
            int npoints = shape.getProperties().get("nPoints").intValue();
            shape.getPosition().x *= factor;
            shift = shape.getPosition().x - xmin;
            for(int i = 2; i <= npoints; i++) {
                int x = shape.getProperties().get("x" + i).intValue();
                if(x < xmin) {
                    xmin = x;
                    x *= factor;
                    shift = x - xmin;
                } else {
                    x *= factor;
                }
                shape.getProperties().put("x" + i, Double.valueOf(x));
            }
            
            shape.getPosition().x -= shift;
            for(int i = 2; i <= npoints; i++) {
                int x = shape.getProperties().get("x" + i).intValue();
                x -= shift;
                shape.getProperties().put("x" + i, Double.valueOf(x));
            }
        } else {
            int w = shape.getProperties().get("Width").intValue();
            w *= factor;
            shape.getProperties().put("Width", Double.valueOf(w));
        }
        this.selection.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }



    @Override
    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }



    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }



    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }



    @Override
    public void mousePressed(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }



    @Override
    public void mouseReleased(MouseEvent arg0) {
        selection.updateShape();
        selection.repaint();
    }


}
