package eg.edu.alexu.csd.oop.draw.cs41.utilities.json;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.cs41.utilities.Helper;

public class JSONHandler {
    private File file;
    private List<Shape> shapes;
    private List<Class<?extends Shape>> supportedShapes;
    
    public JSONHandler(String path, List<Class<?extends Shape>> supportedShapes) {
        this.supportedShapes = supportedShapes;
        this.file = new File(path);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
    }
    public List<Shape> getShapes() {
        return shapes;
    }
    
    public void saveShapes() {
        JSONArray arrShapes = new JSONArray();
        for(Shape s : shapes) {
            JSONObject shape = new JSONObject();
            shape.put("class", s.getClass().getName());
            if(s.getColor() != null) {
                shape.put("color", String.valueOf(s.getColor().getRGB()));
            }
            if(s.getFillColor() != null) {
                shape.put("fill", String.valueOf(s.getFillColor().getRGB()));
            }
            if(s.getPosition() != null) {
                shape.put("x", String.valueOf(s.getPosition().x));
                shape.put("y", String.valueOf(s.getPosition().y));
            }
            
            JSONObject prop = new JSONObject();
            Map<String, Double> properties = s.getProperties();
            if(properties != null) {
                for(String key : properties.keySet()) {
                    prop.put(key, String.valueOf(properties.get(key)));
                }
                shape.put("prop", prop);
            }
            
            arrShapes.add(shape);
        }
        String toSave = arrShapes.toString();
        Helper.writeFile(file, toSave);
    }
    
    public void loadShapes() {
        String jsonString = Helper.readFile(file);
        shapes = new ArrayList<Shape>();
        try {
            JSONArray arrShapes = new JSONArray(jsonString);
            arrShapes.parse();
            for(int i = 0; i < arrShapes.size(); i++) {
                JSONObject shape = arrShapes.get(i);
                String className = (String) shape.get("class");
                Shape s = null;
                try {
                    for(Class<? extends Shape> c : supportedShapes) {
                        if(c.getName().equals(className)) {
                            s = c.newInstance();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(s == null) continue;
                if(shape.hasKey("color")) {
                    s.setColor(Color.getColor("color", Integer.valueOf((String)shape.get("color"))));
                }
                if(shape.hasKey("fill")) {
                    s.setFillColor(Color.getColor("color", Integer.valueOf((String)shape.get("fill"))));
                }
                if(shape.hasKey("x") && shape.hasKey("y")) {
                    s.setPosition(new Point(Integer.valueOf((String)shape.get("x")), Integer.valueOf((String)shape.get("y"))));
                }
                if(shape.hasKey("prop")) {
                    JSONObject prop = (JSONObject)shape.get("prop");
                    Map<String, Double> props = new HashMap<String, Double>();
                    for(String key: prop.keySet()) {
                        props.put(key, Double.valueOf((String)prop.get(key)));
                    }
                    s.setProperties(props);
                }
                shapes.add(s);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
