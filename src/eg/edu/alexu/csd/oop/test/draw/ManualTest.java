package eg.edu.alexu.csd.oop.test.draw;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.cs41.Engine;
import eg.edu.alexu.csd.oop.test.DummyShape;

public class ManualTest {

    public static void main(String[] args) {
        Engine instance = new Engine();
        Shape s1 = new DummyShape();
        s1.setColor(Color.BLUE);
        Shape s2 = new DummyShape();
        s2.setColor(Color.RED);
        
        instance.addShape(s1);
        
        instance.updateShape(s1, s2);
        
        System.out.println(instance.getShapes()[0].getColor());
        System.out.println(s1.getColor());
    }

}
