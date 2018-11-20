package eg.edu.alexu.csd.oop.draw.cs41.gui.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import eg.edu.alexu.csd.oop.draw.cs41.gui.components.DrawingArea;

public class FillAndColorHandler {
    BufferedImage image;
    Graphics2D g2d;
    
    public FillAndColorHandler(int w, int h) {
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    }
    
    public Color getColorOfPixel(Point p, DrawingArea area) {
        g2d = image.createGraphics();
        area.paint(g2d);
        Color c = new Color(image.getRGB(p.x, p.y));
        g2d.dispose();
        return c;
    }
    
    public void saveImage(DrawingArea area) {
        g2d = image.createGraphics();
        area.paint(g2d);
        JFileChooser fc = new JFileChooser();
        fc.addChoosableFileFilter(new FileNameExtensionFilter("JPG File", "jpg"));
        fc.setMultiSelectionEnabled(false);
        fc.setAcceptAllFileFilterUsed(false);
        fc.setCurrentDirectory(new File(""));
        fc.showSaveDialog(null);
        
        File file = fc.getSelectedFile();
        if(file != null) {
            try {
                ImageIO.write(image, "jpeg", file);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
}
