package eg.edu.alexu.csd.oop.draw.cs41.gui.listeners;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputAdapter;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.cs41.gui.components.DrawingArea;
import eg.edu.alexu.csd.oop.draw.cs41.gui.tools.FillAndColorHandler;
import eg.edu.alexu.csd.oop.draw.cs41.gui.tools.ShapeHandler;
import eg.edu.alexu.csd.oop.draw.cs41.utilities.Helper;

public class DrawMouseLisenter extends MouseInputAdapter {
    private static Color TRANSPERANT_FILL = new Color(1f,1f,1f,0f );
    private DrawingArea area;
    private Shape shape = null;
    private Point start;
    private int stroke = 1;
    private Double trans = 1.0;
    private Color color = Color.BLACK;
    private Color fill = Color.WHITE;
    private boolean noFill = true;
    private boolean dragableShape = true;
    private boolean filling = false;
    private boolean picking = false;
    private boolean drawing = false;
    private boolean selecting = false;
    private ShapeHandler shapeHandler = null;
    private static volatile boolean shiftPressed = false;
    private static volatile boolean ctrlPressed = false;
    private JButton fillButton;

    
    public DrawMouseLisenter(DrawingArea drawingArea) {
        this.area = drawingArea;
        startKeyListening();
    }
    
    public void setNoFill(boolean b) {
        this.noFill = b;
    }
    
    public void fillButton(JButton b) {
        fillButton = b;
    }
    
    public void setTrans(Double v) {
        this.trans = v;
        area.updateTrans(v);
    }
    
    public static boolean isShiftPressed() {
        synchronized (DrawMouseLisenter.class) {
            return shiftPressed;
        }
    }
    
    public static boolean isCtrlPressed() {
        synchronized (DrawMouseLisenter.class) {
            return ctrlPressed;
        }
    }
    
    private void startKeyListening() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

            @Override
            public boolean dispatchKeyEvent(KeyEvent ke) {
                synchronized (DrawMouseLisenter.class) {
                    switch (ke.getID()) {
                    case KeyEvent.KEY_PRESSED:
                        if (ke.getKeyCode() == KeyEvent.VK_SHIFT) {
                            shiftPressed = true;
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_CONTROL) {
                            ctrlPressed = true;
                        }
                        break;

                    case KeyEvent.KEY_RELEASED:
                        if (ke.getKeyCode() == KeyEvent.VK_SHIFT) {
                            shiftPressed = false;
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_CONTROL) {
                            ctrlPressed = false;
                        }
                        break;
                    }
                    return false;
                }
            }
        });
    }
    
    public void setDragable(boolean v) {
        this.dragableShape = v;
    }
    
    public void repaint() {
        area.repaint();
    }
    
    public void deleteSelected() {
        area.deleteSelected();
    }
    
    public void setStroke(int v) {
        this.stroke = v;
        area.updateStroke(v);
    }
    
    public Shape getShape() {
        return shape;
    }
    
    public boolean isDrawing() {
        return drawing;
    }
    
    public void setFilling(boolean filling) {
        this.filling = filling;
        drawing = false;
        selecting = false;
        area.emptySelection();
    }
    
    public void setPicking(boolean picking) {
        this.picking = picking;
        filling = false;
        drawing = false;
        selecting = false;
        area.emptySelection();
    }
    
    public void setDrawing(Shape shape) {
        this.drawing = true;
        this.filling = false;
        this.selecting = false;
        this.picking = false;
        this.shape = shape;
        area.emptySelection();
        area.repaint();
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
    
    public void setFillColor(Color color) {
        this.fill = color;
    }
    
    public Color getColor() {
        return color;
    }
    public Color getFillColor() {
        return fill;
    }
    
    public void setSelecting(boolean selecting) {
        drawing = false;
        this.selecting = selecting;
    }
    
    public void setCursor(Cursor c) {
        area.setCursor(c);
    }
    
    public void mouseDragged(MouseEvent e) {
        if(shape == null) return;
        if(selecting) return;
        if(shapeHandler == null || shapeHandler.isPoints()) return ;
        if(!shapeHandler.isPoints()) {
            shapeHandler.drag(start, e.getPoint(), shiftPressed);
            area.repaint();
        }
    }
    
    public void mouseMoved(MouseEvent e) {
        if(drawing && (shapeHandler != null)) {
            shapeHandler.move(e.getPoint());
            area.repaint();
            if(!dragableShape) {
                if(shape != null) {
                    shape.setPosition(e.getPoint());
                    shape.setColor(color);
                    shape.setFillColor(fill);
                    area.setSelected(shape);
                    area.repaint();
                }
            }
        }
    }
    
    public void mousePressed(MouseEvent e) {
        start = e.getPoint();
        if(drawing) {
            if(shape != null) {
                if(shapeHandler == null) {
                    try {
                        shape = shape.getClass().newInstance();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    shape.setColor(color);
                    if(noFill) {
                        shape.setFillColor(TRANSPERANT_FILL);
                    } else {
                        shape.setFillColor(fill);
                    }
                    
                    shape.getProperties().put("stroke", Double.valueOf(stroke));
                    shape.getProperties().put("Transperancy", trans);
                    shapeHandler = new ShapeHandler(shape);
                    area.setSelected(shape);
                }
                if(shapeHandler.isPoints()) {
                    boolean b = shapeHandler.pointsPress(start);
                    if(b) {
                        area.getEngine().addShape(shape);
                        shapeHandler = null;
                        area.setSelected(null);
                        //listProperties();
                    }
                }
            }    
        } else if(selecting) {
            shape = ShapeHandler.search(area.getEngine().getShapes(), e.getPoint());
            if(shape == null) {
                area.emptySelection();
                return;
            } else {
                if(isCtrlPressed()) {
                    area.selectShape(shape);
                } else {
                    area.emptySelection();
                    area.selectShape(shape);
                }
            }
        } else if (filling) {
            shape = ShapeHandler.search(area.getEngine().getShapes(), e.getPoint());
            if(shape == null) {
                area.setBackground(fill);
            } else {
                Shape newShape;
                try {
                    newShape = (Shape) shape.clone();
                } catch (CloneNotSupportedException e1) {
                    return;
                }
                newShape.setFillColor(fill);
                area.getEngine().updateShape(shape, newShape);
            }
            area.repaint();
        } else if(picking) {
            FillAndColorHandler f = new FillAndColorHandler(area.getWidth(), area.getHeight());
            Color c = f.getColorOfPixel(start, area);
            fill = c;
            if(fillButton != null) {
                fillButton.setBackground(c);
            }
        }
    }
    
    public void mouseReleased(MouseEvent e) {
        if(shape == null) return;
        if(!drawing) return;
        if(shapeHandler == null || shapeHandler.isPoints()) return;
        if(start.equals(e.getPoint())) return;
        try {
            area.getEngine().addShape(shape);
            area.setSelected(null);
            shapeHandler = null;
            area.repaint();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }
}
