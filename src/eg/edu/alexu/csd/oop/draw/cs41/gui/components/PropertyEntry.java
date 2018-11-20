package eg.edu.alexu.csd.oop.draw.cs41.gui.components;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
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
import java.awt.Color;

public class PropertyEntry extends JPanel {
    private JSpinner mySpinner;
    public PropertyEntry(String name) {
        setToolTipText(name);
        setBackground(Color.WHITE);
        setLayout(new BorderLayout(0, 0));
        JLabel label = new JLabel(name);
        label.setBackground(Color.WHITE);
        add(label, BorderLayout.CENTER);
        mySpinner = new JSpinner();
        if(name == "stroke") {
            mySpinner.setModel(new SpinnerNumberModel(1.0, 1.0, 5.0, 1.0));
            mySpinner.setEditor(new JSpinner.NumberEditor(mySpinner,"#"));
        } else if (name.equals("Transperancy")) {
            mySpinner.setModel(new SpinnerNumberModel(1.0, 0.0, 1.0, 0.01));
            mySpinner.setEditor(new JSpinner.NumberEditor(mySpinner,"#.##"));   
        }
        
        else {
            mySpinner.setModel(new SpinnerNumberModel(0.0, 0.0, 1000.0, 1));
            mySpinner.setEditor(new JSpinner.NumberEditor(mySpinner,"###.##"));
        }
        
        //JFormattedTextField txt = ((JSpinner.NumberEditor) mySpinner.getEditor()).getTextField();
        //((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);
        mySpinner.setPreferredSize(new Dimension(50, 28));
        
        add(mySpinner, BorderLayout.EAST);
        setPreferredSize(new Dimension(150, 38));
        setSize(getSize());
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
