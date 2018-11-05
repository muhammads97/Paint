package eg.edu.alexu.csd.oop.draw.cs41.gui;

import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Map;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.cs41.shapes.DragableShape;

public class ShapesSelectListener implements ListSelectionListener{
    private DrawMouseLisenter mouseListener;
    private JPanel options;
    private JPanel properties;
    
    public ShapesSelectListener(DrawMouseLisenter mouseLisenter, JPanel options, JPanel properties) {
        this.mouseListener = mouseLisenter;
        this.options = options;
        this.properties = properties;
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList source = (JList) e.getSource();
        ShapeListObject selected = (ShapeListObject) source.getSelectedValue();
        if(selected == null) return;
        try {
            mouseListener.setDrawing(true);
            Shape shape = selected.getShapeClass().newInstance();
            if(!DragableShape.class.isAssignableFrom(shape.getClass())) {
                InputDialog id = new InputDialog(shape.getProperties());
                Map<String, Double> prop = id.showDialog();
                if(prop != null) {
                    shape.setProperties(prop);
                    mouseListener.setDragable(false);
                } else {
                    return;
                }
            }
            mouseListener.setShape(shape);
            mouseListener.setCursor(Cursor.CROSSHAIR_CURSOR);
            properties.validate();
            CardLayout cl = (CardLayout)options.getLayout();
            cl.show(options, "propertiesPanel");  
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    
    

}
