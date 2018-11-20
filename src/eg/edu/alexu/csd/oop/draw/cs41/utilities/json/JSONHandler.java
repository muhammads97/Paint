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
                JSONObject color = new JSONObject();
                float[] comp = new float[4];
                s.getColor().getComponents(comp);
                color.put("r", String.valueOf(comp[0]));
                color.put("g", String.valueOf(comp[1]));
                color.put("b", String.valueOf(comp[2]));
                color.put("a", String.valueOf(comp[3]));
                shape.put("color", color);
            }
            if(s.getFillColor() != null) {
                JSONObject color = new JSONObject();
                float[] comp = new float[4];
                s.getFillColor().getComponents(comp);
                color.put("r", String.valueOf(comp[0]));
                color.put("g", String.valueOf(comp[1]));
                color.put("b", String.valueOf(comp[2]));
                color.put("a", String.valueOf(comp[3]));
                shape.put("fillColor", color);
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
                    JSONObject color = (JSONObject) shape.get("color");
                    float[] comp = new float[4];
                    comp[0] = Float.valueOf((String)color.get("r"));
                    comp[1] = Float.valueOf((String)color.get("g"));
                    comp[2] = Float.valueOf((String)color.get("b"));
                    comp[3] = Float.valueOf((String)color.get("a"));
                    Color c = new Color(comp[0], comp[1], comp[2], comp[3]);
                    s.setColor(c);
                }
                if(shape.hasKey("fillColor")) {
                    JSONObject color = (JSONObject) shape.get("fillColor");
                    float[] comp = new float[4];
                    comp[0] = Float.valueOf((String)color.get("r"));
                    comp[1] = Float.valueOf((String)color.get("g"));
                    comp[2] = Float.valueOf((String)color.get("b"));
                    comp[3] = Float.valueOf((String)color.get("a"));
                    Color c = new Color(comp[0], comp[1], comp[2], comp[3]);
                    s.setFillColor(c);
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
