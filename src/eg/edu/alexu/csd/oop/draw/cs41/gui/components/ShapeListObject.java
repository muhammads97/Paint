package eg.edu.alexu.csd.oop.draw.cs41.gui.components;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import eg.edu.alexu.csd.oop.draw.Shape;

public class ShapeListObject extends JLabel{
    private Class<? extends Shape> shapeClass;
    private String shapeName = "shape";
    
    public ShapeListObject(ImageIcon icon, Class<? extends Shape> shapeClass) {
        super(icon);
        this.shapeClass = shapeClass;
        String[] name = shapeClass.getName().split("\\.");
        shapeName = name[name.length - 1];
    }
    
    public void setShapeName(String name) {
        this.shapeName = name;
    }
    
    public String getName() {
        return shapeName;
    }
    
    public Class<? extends Shape> getShapeClass(){
        return shapeClass;
    }
}
