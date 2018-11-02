package eg.edu.alexu.csd.oop.test.draw;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.cs41.Engine;

public class ManualTest {

    public static void main(String[] args) {
        Engine instance = new Engine();
//        Shape s1 = new DummyShape();
//        s1.setColor(Color.BLUE);
//        Shape s2 = new DummyShape();
//        s2.setColor(Color.RED);
//        s1.setPosition(new Point(0, 0));
//        Shape s3 = new DummyShape();
//        s3.setFillColor(Color.CYAN);
//        Map<String, Double> m = new HashMap<String, Double>();
//        m.put("radius", 5.0);
//        s3.setProperties(m);
//        instance.addShape(s1);
//        
//        instance.addShape(s2);
//        instance.addShape(s3);
//        instance.save("ahi.xml");
        System.out.println(instance.getSupportedShapes().size());
        instance.load("ahi.xml");
        Shape[] shapes = instance.getShapes();
        for(Shape shape : shapes) {
            System.out.println(shape.getFillColor());
            System.out.println(shape.getClass().getName());
        }
    }

}
