package eg.edu.alexu.csd.oop.draw.cs41.gui;

import java.awt.Button;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ImageButton extends Button{
    private Image icon;
    
    public ImageButton(Image icon) {
        this.icon =  icon;
    }
    @Override
    public void paint(Graphics arg0) {
        // TODO Auto-generated method stub
        super.paint(arg0);
        if(icon != null) {
            icon = icon.getScaledInstance(30, 30, Image.SCALE_DEFAULT);
            int x = (getWidth()/2 - icon.getWidth(this)/2);
            int y = (getHeight()/2 - icon.getHeight(this)/2);
//            icon.paintIcon(this, arg0, x, y);
            arg0.drawImage(icon, x, y, this);
        }
    }

}
