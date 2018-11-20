package eg.edu.alexu.csd.oop.draw.cs41.gui.colorPicker;

import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class MyColorBar2 extends ColorPanelParent {

    public MyColorBar2(int prefW, int prefH, ColorProperty colorProperty) {
        super(prefW, prefH, colorProperty, false);

        // create and set the background image
        setColorPropertyValue(Color.RED); // fix the magic number?
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // draws the background image

        // this draws a line at the cursor's location
        g.setXORMode(Color.WHITE);
        int y = getCursorP().y;
        g.drawLine(0, y, getPrefW(), y);
    }

}