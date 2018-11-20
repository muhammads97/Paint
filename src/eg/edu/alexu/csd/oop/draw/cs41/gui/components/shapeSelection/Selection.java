package eg.edu.alexu.csd.oop.draw.cs41.gui.components.shapeSelection;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.cs41.gui.components.DrawingArea;
import eg.edu.alexu.csd.oop.draw.cs41.shapes.interfaces.FreeShape;
import eg.edu.alexu.csd.oop.draw.cs41.shapes.interfaces.PointsShape;

public class Selection extends JComponent implements MouseMotionListener, MouseListener{
    private Shape shape;
    private Shape newShape;
    private DrawingArea area;
    private ResizeBox rb;
    private ResizeBox rb1;
    private ResizeBox rb2;
    private ResizeBox rb3;
    private ResizeBox rb4;
    private ResizeBox rb5;
    private ResizeBox rb6;
    private ResizeBox rb7;
    
    private Point start;
    
    public Selection(Shape shape, DrawingArea area) {
        this.shape = shape;
        try {
            this.newShape = (Shape) shape.clone();
        } catch (CloneNotSupportedException e) {
            
        }
        this.area = area;
        setLayout(null);
        setCursor(new Cursor(Cursor.MOVE_CURSOR));
        setSelectionBounds();
        addResizeBoxes();
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    public Shape getShape() {
        return shape;
    }
    
    public Shape getNewShape() {
        return newShape;
    }
    
    public void repaint() {
        area.repaint();
        setSelectionBounds();
        updateResizeBoxes();
        super.repaint();
    }
    
    public void updateStroke(int v) {
        newShape.getProperties().put("stroke", Double.valueOf(v));
        updateShape();
        repaint();
    }
    
    public void updateTrans(Double v) {
        newShape.getProperties().put("Transperancy", Double.valueOf(v));
        updateShape();
        repaint();
    }
    
    public void updateShape() {
        if(!newShape.equals(shape)) {
            area.updateShape(newShape, shape);
            try {
                shape = (Shape) newShape.clone();
            } catch (CloneNotSupportedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics canvas) {
        Graphics2D g2d = (Graphics2D) canvas;
        g2d.setColor(Color.white);
        drawRec(g2d);
        float[] pattern = {8, 4};
        Stroke stroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, pattern, 0);
        g2d.setStroke(stroke);
        g2d.setColor(Color.BLUE);
        drawRec(g2d);
        super.paintComponent(canvas);
    }
    
    private void drawRec(Graphics2D canvas) {
        int h = getHeight();
        int w = getWidth();
        canvas.drawRect(3, 3, w - 6, h - 6);
    }
    
    private void setSelectionBounds() {
        if(newShape == null) return;
        if(PointsShape.class.isAssignableFrom(shape.getClass()) 
                || FreeShape.class.isAssignableFrom(shape.getClass())) {
            int xmin = newShape.getPosition().x;
            int ymin = newShape.getPosition().y;
            int xmax = newShape.getPosition().x;
            int ymax = newShape.getPosition().y;
            int npoints = newShape.getProperties().get("nPoints").intValue();
            for(int i = 2; i <= npoints; i++) {
                int x = newShape.getProperties().get("x" + i).intValue(); 
                int y = newShape.getProperties().get("y" + i).intValue(); 
                xmin = Math.min(xmin, x);
                ymin = Math.min(ymin, y);
                xmax = Math.max(xmax, x);
                ymax = Math.max(ymax, y);
            }
            setBounds(xmin - 3, ymin - 3, xmax - xmin + 6, ymax - ymin + 6);
        } else {
            int x = newShape.getPosition().x;
            int y = newShape.getPosition().y;
            int h = newShape.getProperties().get("Length").intValue();
            int w = newShape.getProperties().get("Width").intValue();
            setBounds(x - 3, y - 3, w + 6, h + 6);
        }
    }
    
    private void addResizeBoxes() {
        removeAll();
        rb = new ResizeBox(0, 0, 5, 5, 0, this, newShape);
        rb1 = new ResizeBox(getWidth() / 2 - 2, 0, 5, 5, 1, this, newShape);
        rb2 = new ResizeBox(getWidth() - 5, 0, 5, 5, 2, this, newShape);
        rb3 = new ResizeBox(getWidth() - 5, getHeight() / 2 - 2, 5, 5, 3, this, newShape);
        rb4 = new ResizeBox(getWidth() - 5, getHeight() - 5, 5, 5, 4, this, newShape);
        rb5 = new ResizeBox(getWidth() / 2 - 2, getHeight() - 5, 5, 5, 5, this, newShape);
        rb6 = new ResizeBox(0, getHeight() - 5, 5, 5, 6, this, newShape);
        rb7 = new ResizeBox(0, getHeight() / 2 - 2, 5, 5, 7, this, newShape);
        add(rb);
        add(rb1);
        add(rb2);
        add(rb3);
        add(rb4);
        add(rb5);
        add(rb6);
        add(rb7);
        validate();
    }
    
    private void updateResizeBoxes() {
        rb.setLocation(new Point(0,  0));
        rb1.setLocation(new Point(getWidth() / 2 - 2, 0));
        rb2.setLocation(new Point(getWidth() - 5, 0));
        rb3.setLocation(new Point(getWidth() - 5, getHeight() / 2 - 2));
        rb4.setLocation(new Point(getWidth() - 5, getHeight() - 5));
        rb5.setLocation(new Point(getWidth() / 2 - 2, getHeight() - 5));
        rb6.setLocation(new Point(0, getHeight() - 5));
        rb7.setLocation(new Point(0, getHeight() / 2 - 2));
    }
    @Override
    public boolean equals(Object selection) {
        Selection s = (Selection) selection;
        return s.getShape().equals(this.shape);
    }
    
    public boolean equals(Shape shape) {
        return shape.equals(this.shape);
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
        start = arg0.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        if(!area.isEnableSingleMove()) {
            area.approveMove();
            return;
        }
        updateShape();
    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
        Point end = arg0.getPoint();
        int transX = end.x - start.x;
        int transY = end.y - start.y;
        if(!area.isEnableSingleMove()) {
            area.moveSelected(transX, transY);
            return;
        }
        moveShape(transX, transY);
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    
    public void moveShape(int transX, int transY) {
        newShape.getPosition().x += transX;
        newShape.getPosition().y += transY;
        if(PointsShape.class.isAssignableFrom(shape.getClass()) 
                || FreeShape.class.isAssignableFrom(shape.getClass())) {
            int npoints = newShape.getProperties().get("nPoints").intValue();
            for(int i = 2; i <= npoints; i++) {
                int x = newShape.getProperties().get("x" + i).intValue(); 
                int y = newShape.getProperties().get("y" + i).intValue(); 
                x += transX;
                y += transY;
                newShape.getProperties().put("x" + i, Double.valueOf(x));
                newShape.getProperties().put("y" + i, Double.valueOf(y));
            }
        }
        repaint();
    }
    
}
