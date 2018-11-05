package eg.edu.alexu.csd.oop.draw.cs41.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputAdapter;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.cs41.Engine;
import eg.edu.alexu.csd.oop.draw.cs41.utilities.Helper;

public class DrawMouseLisenter extends MouseInputAdapter {
    private DrawingArea area;
    private Shape shape = null;
    private Point start;
    private int stroke = 1;
    private Color color = Color.BLACK;
    private Color fill = Color.WHITE;
    private boolean dragableShape = true;
    private boolean select = false;
    private boolean moving = false;
    private boolean drawing = false;
    private boolean selecting = false;
    private JPanel options;
    private JPanel properties;
    
    
    public DrawMouseLisenter(DrawingArea drawingArea, JPanel options, JPanel properties) {
        this.area = drawingArea;
        this.options = options;
        this.properties = properties;
    }
    
    public void setDragable(boolean v) {
        this.dragableShape = v;
    }
    
    public void repaint() {
        area.repaint();
    }
    
    public void deleteSelected() {
        if(select) {
            if(shape != null) {
                area.getEngine().removeShape(shape);
                select = false;
                area.setSelection(null);
                shape = null;
                area.repaint();
            }
        }
    }
    
    public void setStroke(int v) {
        this.stroke = v;
    }
    
    public void setMove(boolean move) {
        if(shape == null) return;
        if(!select) return;
        select = false;
        drawing = false;
        this.moving = move;
        
    }
    
    public Shape getShape() {
        return shape;
    }
    
    public boolean isSelected() {
        return select;
    }
    
    public boolean isDrawing() {
        return drawing;
    }
    
    public void setDrawing(boolean v) {
        this.drawing = v;
        this.select = false;
        this.selecting = false;
        this.moving = false;
        area.setSelection(null);
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
    
    public void setShape(Shape shape) {
        if(shape != null) {
            this.shape = shape;
            select = false;
            selecting = false;
            moving = false;
            drawing = true;
        } else {
            selecting = true;
            select = false;
            moving = false;
            drawing = false;
        }
        
    }
    
    public void setCursor(int type) {
        area.setCursor(new Cursor(type));
    }
    
    public void mouseDragged(MouseEvent e) {
        if(shape == null) return;
        if(selecting) return;
        if(dragableShape) {
            shape.setPosition(start);
            Map<String, Double> prop = shape.getProperties();
            prop.put("x", Double.valueOf(e.getX()));
            prop.put("y", Double.valueOf(e.getY()));
            area.repaint();
        }
    }
    
    public void mouseMoved(MouseEvent e) {
        if(drawing) {
            if(!dragableShape) {
                if(shape != null) {
                    shape.setPosition(e.getPoint());
                    shape.setColor(color);
                    shape.setFillColor(fill);
                    area.setSelected(shape);
                    area.repaint();
                }
            }
        } else if (moving) {
            if(shape != null) {
                int hight;
                int width;
                try {
                    hight = Math.abs(shape.getPosition().y - shape.getProperties().get("y").intValue());
                    width = Math.abs(shape.getPosition().x - shape.getProperties().get("x").intValue());
                } catch (Exception e2) {
                    hight = shape.getProperties().get("Length").intValue();
                    width = shape.getProperties().get("Width").intValue();
                }
                
                Shape shape2 = null;
                try {
                    shape2 = (Shape) shape.clone();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if(shape2 != null) {
                    if(dragableShape) {
                        shape2.getProperties().put("x", Double.valueOf(e.getPoint().x + width));
                        shape2.getProperties().put("y", Double.valueOf(e.getPoint().y + hight));
                    }
                    shape2.setPosition(e.getPoint());
                    area.getEngine().updateShape(shape, shape2);
                    shape = shape2;
                    area.setSelection(shape);
                }
                area.repaint();
            }
        }
    }
    
    public void mousePressed(MouseEvent e) {
        start = e.getPoint();
        if(drawing) {
            if(dragableShape) {
                area.setSelection(null);
                if(shape != null) {
                    try {
                        shape = (Shape)shape.clone();
                    } catch (CloneNotSupportedException e1) {
                        e1.printStackTrace();
                    }
                    shape.setColor(color);
                    shape.setFillColor(fill);
                    shape.getProperties().put("stroke", Double.valueOf(stroke));
                    area.setSelected(shape);
                }
            } else {
                if(shape != null) {
                    try {
                        area.getEngine().addShape((Shape)shape.clone());
                        shape = null;
                        area.setSelected(null);
                        dragableShape = true;
                        drawing = false;
                    } catch (CloneNotSupportedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    
                }
            }
            
        } else if (moving) {
            if(shape != null) {
                moving = false;
                select = true;
                area.setSelected(shape);
            }
        } else if (selecting) {
            shape = Helper.search(area.getEngine().getShapes(), start);
            area.setSelection(shape);
            
            if(shape != null) {
                System.out.println(shape.getPosition());
                System.out.println(shape.getProperties().get("x"));
                System.out.println(shape.getProperties().get("y"));
                System.out.println(start);
                select = true;
                CardLayout cl = (CardLayout)options.getLayout();
                cl.show(options, "shapeSelectedOptions");
            }
            else {
                properties.removeAll();
                CardLayout cl = (CardLayout)options.getLayout();
                cl.show(options, "propertiesPanel");
            }
            area.repaint();
        } 
    }

    public void mouseReleased(MouseEvent e) {
        if(shape == null) return;
        if(!drawing) return;
        try {
            area.getEngine().addShape(shape);
            listProperties();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }
    
    private void listProperties() {
        properties.removeAll();
        if(shape == null) return;
        //System.out.println(mouseListener.getShape().getProperties().size());
        Map<String, Double> prop = shape.getProperties();
        int x = 0;
        int y = 0;
        for(String key: prop.keySet()) {
            PropertyEntry pe = new PropertyEntry(key);
            pe.setValue(prop.get(key));
            pe.addlistener(new ChangeListener() {
                
                @Override
                public void stateChanged(ChangeEvent arg0) {
                    prop.put(key, pe.getValue());
                    area.repaint();
                }
            });
            pe.setSize(new Dimension(120, 38));
            pe.setBounds(new Rectangle(x, y, pe.getSize().width, pe.getSize().height));
            x += pe.getSize().width;
            if(x + 120 > properties.getSize().width) {
                x = 0;
                y += 38;
            }
            properties.add(pe);
        }
        properties.validate();
        CardLayout cl = (CardLayout)options.getLayout();
        cl.show(options, "propertiesPanel");    
    }
}
