package eg.edu.alexu.csd.oop.draw.cs41.gui;

import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;
import java.awt.BorderLayout;

public class PropertyEntry extends JPanel {
    private JSpinner mySpinner;
    public PropertyEntry(String name) {
        setLayout(new BorderLayout(0, 0));
        Label label = new Label(name);
        add(label, BorderLayout.CENTER);
        mySpinner = new JSpinner();
        if(name == "stroke") {
            mySpinner.setModel(new SpinnerNumberModel(1.0, 1.0, 5.0, 1.0));
            mySpinner.setEditor(new JSpinner.NumberEditor(mySpinner,"#"));
        } else {
            mySpinner.setModel(new SpinnerNumberModel(0.0, 0.0, 1000.0, 1));
            mySpinner.setEditor(new JSpinner.NumberEditor(mySpinner,"###.##"));
        }
        
        //JFormattedTextField txt = ((JSpinner.NumberEditor) mySpinner.getEditor()).getTextField();
        //((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);
        mySpinner.setPreferredSize(new Dimension(50, 28));
        
        add(mySpinner, BorderLayout.EAST);
        setPreferredSize(new Dimension(120, 38));
        setSize(getSize());
        
        JPanel panel = new JPanel();
        add(panel, BorderLayout.WEST);
        validate();
        //setMinimumSize(new Dimension(150, 30));
    }
    
    public void setValue(Double v) {
        mySpinner.setValue(v);
    }
    
    public Double getValue() {
        return (Double)mySpinner.getValue();
    }
    public void addlistener(ChangeListener cl) {
        mySpinner.addChangeListener(cl);
    }
    

}
