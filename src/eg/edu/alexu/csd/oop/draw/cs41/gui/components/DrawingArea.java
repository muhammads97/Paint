package eg.edu.alexu.csd.oop.draw.cs41.gui.components;

import java.awt.AWTEvent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;
import javax.swing.JPanel;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.cs41.gui.components.shapeSelection.Selection;
import eg.edu.alexu.csd.oop.draw.cs41.gui.listeners.DrawMouseLisenter;

public class DrawingArea extends JPanel{
    private final static int AREA_SIZE = 600;
    private DrawingEngine engine;
    private Shape drawingShape = null;
    private List<Selection> selectedShapes;
    private boolean selection = false;
    private boolean enableSingleMove = true;
    
    public DrawingArea(DrawingEngine engine) {
        this.engine = engine;
        setPreferredSize(new Dimension(820, 420));
        setBackground(Color.WHITE);
        selectedShapes = new ArrayList<Selection>();
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
    }
    
    public boolean isEnableSingleMove() {
        return enableSingleMove;
    }
    public void enableSingleMove() {
        enableSingleMove = true;
    }
    public void disableSlingleMove() {
        enableSingleMove = false;
    }
    
    public void moveSelected(int transX, int transY) {
        for(Selection s : selectedShapes) {
            s.moveShape(transX, transY);
        }
    }
    
    public void approveMove() {
        for(Selection s : selectedShapes) {
            s.updateShape();
        }
    }
    
    public void setSelection(Shape shape) {
        if(shape != null) {
            this.selection = true;
        } else {
            this.selection = false;
        }
        
        drawingShape = shape;
    }
    
    public void updateShape(Shape newShape, Shape oldShape) {
        engine.updateShape(oldShape, newShape);
    }
    
    public void selectShape(Shape s) {
        drawingShape = null;
        Selection selection;
        try {
            selection = new Selection((Shape) s.clone(), this);
        } catch (CloneNotSupportedException e1) {
            return;
        }
        if(selectedShapes.contains(selection)) {
            return;
        }
        this.selectedShapes.add(selection);
        add(selection);
        selection.repaint();
        if(selectedShapes.size() > 1) {
            disableSlingleMove();
        } else {
            enableSingleMove();
        }
    }
    
    public void deleteSelected() {
        for(Selection s: selectedShapes) {
            Shape sh = s.getShape();
            engine.removeShape(sh);
            
        }
        
        emptySelection();
        repaint();
    }
    
    public void unselectShape(Shape s) {
        Selection selection;
        try {
            selection = new Selection((Shape) s.clone(), this);
        } catch (CloneNotSupportedException e1) {
            return;
        }
        this.selectedShapes.remove(selection);
    }
    
    public void emptySelection() {
        this.selectedShapes.clear();
        this.removeAll();
        this.validate();
        this.repaint();
    }
    
    public List<Shape> getSelectedShapes(){
        List <Shape> shapes = new ArrayList<Shape>();
        for(Selection s: selectedShapes) {
            shapes.add(s.getShape());
        }
        return shapes;
    }
    
    public void setMouseListener(DrawMouseLisenter ml) {
        addMouseListener(ml);
        addMouseMotionListener(ml);
    }
    
    public void setSelected(Shape shape) {
        this.drawingShape = shape;
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
    
    public void updateTrans(Double v) {
        for(Selection s : selectedShapes) {
            s.updateTrans(v);
        }
    }
    
    public void updateStroke(int v) {
        for(Selection s : selectedShapes) {
            s.updateStroke(v);
        }
    }
    
    @Override
    protected void paintComponent(Graphics canvas) {
        super.paintComponent(canvas);
        
        if(!selectedShapes.isEmpty()) {
            Shape[] shapes = engine.getShapes();
            for(Shape s : shapes) {
                Selection sel = new Selection(s, this);
                if(selectedShapes.contains(sel)) continue;
                s.draw(canvas);
            }
            for(Selection s : selectedShapes) {
                Shape newShape = s.getNewShape();
                newShape.draw(canvas);
            }
        } else {
            engine.refresh(canvas);
        }
        
        if(drawingShape != null) {
            drawingShape.draw(canvas);
        }
    }
}
