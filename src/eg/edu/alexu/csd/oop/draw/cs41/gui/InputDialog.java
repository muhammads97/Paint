package eg.edu.alexu.csd.oop.draw.cs41.gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class InputDialog {
    private Map<String, Double> prop;
    
    public InputDialog(Map<String, Double> prop) {
        this.prop = prop;
    }
    
    public Map<String, Double> showDialog() {
        
        int selected = JOptionPane.showConfirmDialog(null, getPanel(),
                                                    "enter shape properties",
                                                    JOptionPane.OK_CANCEL_OPTION,
                                                    JOptionPane.PLAIN_MESSAGE);
        if(selected == JOptionPane.OK_OPTION) {
            return prop;
        }
        return null;
    }
    
    private JPanel getPanel() {
        JPanel optionPanel = new JPanel();
        //optionPanel.setLayout(null);
        optionPanel.setBounds(100, 100, 500, 800);
        optionPanel.setSize(new Dimension(500, 800));
        optionPanel.setPreferredSize(new Dimension(500, 500));
        int x = 0;
        int y = 0;
        
        for(String key: prop.keySet()) {
            PropertyEntry pe = new PropertyEntry(key);
            pe.setValue(prop.get(key));
            pe.addlistener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent arg0) {
                    prop.put(key, pe.getValue());
                }
            });
            pe.setSize(new Dimension(120, 38));
            pe.setBounds(new Rectangle(x, y, pe.getSize().width, pe.getSize().height));
            x += pe.getSize().width;
            if(x + 120 > optionPanel.getSize().width) {
                x = 0;
                y += 38;
            }
            optionPanel.add(pe);
        }
        
        return optionPanel;
    }
}
