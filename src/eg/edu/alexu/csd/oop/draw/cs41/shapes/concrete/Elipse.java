package eg.edu.alexu.csd.oop.draw.cs41.shapes.concrete;

import eg.edu.alexu.csd.oop.draw.cs41.shapes.Oval;

/**
 * @author Muhammad Salah
 *
 */
public class Elipse extends Oval {

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
