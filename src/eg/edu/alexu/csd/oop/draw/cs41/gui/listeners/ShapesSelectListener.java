package eg.edu.alexu.csd.oop.draw.cs41.gui.listeners;

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
import eg.edu.alexu.csd.oop.draw.cs41.gui.components.ShapeListObject;

public class ShapesSelectListener implements ListSelectionListener{
    private DrawMouseLisenter mouseListener;

    public ShapesSelectListener(DrawMouseLisenter mouseLisenter) {
        this.mouseListener = mouseLisenter;
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList source = (JList) e.getSource();
        ShapeListObject selected = (ShapeListObject) source.getSelectedValue();
        if(selected == null) return;
        try {
            Shape shape = selected.getShapeClass().newInstance();
            mouseListener.setDrawing(shape);
            mouseListener.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    
    

}
