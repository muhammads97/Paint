package eg.edu.alexu.csd.oop.draw.cs41.utilities;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eg.edu.alexu.csd.oop.draw.Shape;

/**
 * @author Muhammad Salah
 * a class to handle xml files and operations
 */
public class XMLHandler {
    /**
     * xml file
     */
    private File file;
    /**
     * document builder factory
     */
    private DocumentBuilderFactory factory;
    /**
     * document builder
     */
    private DocumentBuilder builder;
    /**
     * document object
     */
    private Document doc;
    /**
     * the main element of the documents
     */
    private Element dom;
    /**
     * list of shapes to read or write
     */
    private List<Shape> shapes;
    /**
     * list of supported shapes' classes to load the shapes 
     */
    private List<Class <? extends Shape>> supportedShapes;
    
    /**
     * @param path file path
     * @param supportedShapes 
     */
    public XMLHandler(String path, List<Class <? extends Shape>> supportedShapes) {
        this.supportedShapes = supportedShapes;
        this.file = new File(path);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        factory.setValidating(false);
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.newDocument();
            dom = doc.createElement("root");
            doc.appendChild(dom);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @param shapes shapes to write
     */
    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
    }
    
    /**
     * save the shapes to the path
     */
    public void saveShapes() {
        for(Shape shape : shapes) {
            Element s = doc.createElement("shape");
            s.setAttribute("class", shape.getClass().getName());
            Map<String, Double> properties = shape.getProperties();
            Element prop = doc.createElement("properties");
            if(properties != null) {
                Set<String> keys = properties.keySet();
                for(String key : keys){
                    prop.setAttribute(key, String.valueOf(shape.getProperties().get(key)));
                }
            }
            
            if(shape.getPosition() != null) {
                s.setAttribute("x", String.valueOf(shape.getPosition().x));
                s.setAttribute("y", String.valueOf(shape.getPosition().y));
            }
            
            if(shape.getColor() != null) {
                Element color = doc.createElement("color");
                float[] comp = new float[4];
                shape.getColor().getComponents(comp);
                color.setAttribute("r", String.valueOf(comp[0]));
                color.setAttribute("g", String.valueOf(comp[1]));
                color.setAttribute("b", String.valueOf(comp[2]));
                color.setAttribute("a", String.valueOf(comp[3]));
                s.appendChild(color);
            }
            if(shape.getFillColor() != null) {
                Element color = doc.createElement("fillColor");
                float[] comp = new float[4];
                shape.getFillColor().getComponents(comp);
                color.setAttribute("r", String.valueOf(comp[0]));
                color.setAttribute("g", String.valueOf(comp[1]));
                color.setAttribute("b", String.valueOf(comp[2]));
                color.setAttribute("a", String.valueOf(comp[3]));
                s.appendChild(color);
            }
            s.appendChild(prop);
            dom.appendChild(s);
        }
        
        TransformerFactory tfact = TransformerFactory.newInstance();
        Transformer tform;
        try {
            tform = tfact.newTransformer();
            tform.setOutputProperty(OutputKeys.INDENT, "yes");
            tform.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
            tform.transform(new DOMSource(doc), new StreamResult(file));
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
    
    /**
     * load shapes 
     */
    @SuppressWarnings("deprecation")
    public void loadShapes() {
        try {
            doc = builder.parse(file);
            dom = doc.getDocumentElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        shapes = new ArrayList<Shape>();
        NodeList shapeElements = dom.getChildNodes();
        for(int i = 0; i < shapeElements.getLength(); i++) {
            Node child = shapeElements.item(i);
            if ( child.getNodeType() != Node.ELEMENT_NODE )
                continue;
            Element s = (Element) child;
            String className = s.getAttribute("class");
            Shape shape = null;
            try {
                for(Class<? extends Shape> c : supportedShapes) {
                    if(c.getName().equals(className)) {
                        shape = c.newInstance();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(shape == null) continue;
            Element prop = (Element) s.getElementsByTagName("properties").item(0);
            Map<String, Double> p = new HashMap<String, Double>();
            NamedNodeMap nnm = prop.getAttributes();
            for(int j = 0; j < nnm.getLength(); j++) {
                String key = nnm.item(j).getNodeName();
                Double value = Double.valueOf(nnm.item(j).getNodeValue());
                p.put(key, value);
            }
            shape.setProperties(p);
            Element color = (Element) s.getElementsByTagName("color").item(0);
            if(color != null) {
                float[] comp = new float[4];
                comp[0] = Float.valueOf(color.getAttribute("r"));
                comp[1] = Float.valueOf(color.getAttribute("g"));
                comp[2] = Float.valueOf(color.getAttribute("b"));
                comp[3] = Float.valueOf(color.getAttribute("a"));
                
                Color c = new Color(comp[0], comp[1], comp[2], comp[3]);
                shape.setColor(c);
            }
            Element fillColor = (Element) s.getElementsByTagName("fillColor").item(0);
            if(fillColor != null) {
                float[] comp = new float[4];
                comp[0] = Float.valueOf(fillColor.getAttribute("r"));
                comp[1] = Float.valueOf(fillColor.getAttribute("g"));
                comp[2] = Float.valueOf(fillColor.getAttribute("b"));
                comp[3] = Float.valueOf(fillColor.getAttribute("a"));
                
                Color c = new Color(comp[0], comp[1], comp[2], comp[3]);
                shape.setFillColor(c);
            }
            if(s.hasAttribute("x") && s.hasAttribute("y")) {
                shape.setPosition(new Point(Integer.valueOf(s.getAttribute("x")), Integer.valueOf(s.getAttribute("y"))));
            }
            shapes.add(shape);
        }
    }
    
    /**
     * @return llist of shapes
     */
    public List<Shape> getShapes() {
        return shapes;
    }
}
