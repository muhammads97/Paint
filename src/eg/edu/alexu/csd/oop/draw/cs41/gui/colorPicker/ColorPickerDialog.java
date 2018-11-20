package eg.edu.alexu.csd.oop.draw.cs41.gui.colorPicker;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ColorPickerDialog extends JFrame{
    private HsvChooserPanel2 chooserPanel;
    private boolean mouseIn = false;
    
    public ColorPickerDialog(PropertyChangeListener p, Color c) {
        chooserPanel = new HsvChooserPanel2(ColorProperty.SATURATION, c);
        chooserPanel.addPropertyChangeListener(HsvChooserPanel2.COLOR, p);
        add(chooserPanel);
        this.addMouseListener(new MouseListener() {
            
            @Override
            public void mouseReleased(MouseEvent arg0) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void mousePressed(MouseEvent arg0) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void mouseExited(MouseEvent arg0) {
                mouseIn = false;
                //setVisible(false);
            }
            
            @Override
            public void mouseEntered(MouseEvent arg0) {
                mouseIn = true;
            }
            
            @Override
            public void mouseClicked(MouseEvent arg0) {
                // TODO Auto-generated method stub
                
            }
        });
        setAlwaysOnTop(true);
    }
    public boolean isMouseIn() {
        return mouseIn;
    }
}
