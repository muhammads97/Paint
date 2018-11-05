package eg.edu.alexu.csd.oop.draw.cs41.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;

public class DrawingArea extends JPanel{
    private final static int AREA_SIZE = 600;
    private DrawingEngine engine;
    private Shape selected = null;
    private boolean selection = false;
    
    
    public DrawingArea(DrawingEngine engine) {
        this.engine = engine;
        setPreferredSize(new Dimension(820, 420));
        setBackground(Color.WHITE);
    }
    
    public void setSelection(Shape shape) {
        if(shape != null) {
            this.selection = true;
        } else {
            this.selection = false;
        }
        
        selected = shape;
        
    }
    
    public void setMouseListener(DrawMouseLisenter ml) {
        addMouseListener(ml);
        addMouseMotionListener(ml);
    }
    
    
    public void setSelected(Shape shape) {
        this.selected = shape;
    }
    
    public DrawingEngine getEngine() {
        return engine;
    }
    
    @Override
    public Dimension getPreferredSize()
    {
        return isPreferredSizeSet() ?
            super.getPreferredSize() : new Dimension(AREA_SIZE, AREA_SIZE);
    }
    
    @Override
    protected void paintComponent(Graphics canvas) {
        super.paintComponent(canvas);
        engine.refresh(canvas);
        
        
        if(selection) {
            Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
            Graphics2D g2d = (Graphics2D) canvas;
            
            Stroke temp = g2d.getStroke();
            g2d.setStroke(dashed);
            
            Color t = g2d.getColor();
            g2d.setColor(Color.BLACK);
            
            int x = selected.getPosition().x;
            int y = selected.getPosition().y;
            int x2;
            int y2;
            try {
                x2 = selected.getProperties().get("x").intValue();
                y2 = selected.getProperties().get("y").intValue();
            } catch (Exception e) {
                try {
                    x2 = selected.getProperties().get("Width").intValue();
                    y2 = selected.getProperties().get("Length").intValue();
                    x2 = x + x2;
                    y2 = y + y2;
                } catch (Exception e2) {
                    return;
                }
               
            }
            
            g2d.drawRect(Math.min(x, x2), Math.min(y2, y), Math.abs(x2-x), Math.abs(y2-y));
            
            g2d.setStroke(temp);
            g2d.setColor(t);
        } else if(selected != null) {
            selected.draw(canvas);
        }
    }
    
    
    
}
