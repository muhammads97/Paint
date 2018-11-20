package eg.edu.alexu.csd.oop.draw.cs41.gui.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.border.Border;

public class CircularButton extends JButton{
    private int r;
    public CircularButton(int r) {
        this.r = r;
        setOpaque(true);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    @Override
    protected void paintComponent(Graphics arg0) {
        Graphics2D g = (Graphics2D) arg0;
        g.setColor(getBackground());
        g.setStroke(new BasicStroke(2));
        super.paintComponent(arg0);
    }
}
