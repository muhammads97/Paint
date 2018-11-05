package eg.edu.alexu.csd.oop.draw.cs41.shapes;

import java.awt.Point;
import java.util.HashMap;

public class Elipse extends Oval implements DragableShape{

    public Elipse(Elipse o) {
        super(o);
    }
    
    public Elipse() {
        super(0.0, 0.0);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        
        return new Elipse(this);
    }
    

}
