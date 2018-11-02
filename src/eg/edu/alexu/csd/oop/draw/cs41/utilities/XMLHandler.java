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
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eg.edu.alexu.csd.oop.draw.Shape;

public class XMLHandler {
    private File file;
    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private Document doc;
    private Element dom;
    private List<Shape> shapes;
    private List<Class <? extends Shape>> supportedShapes;
    
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
    
    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
    }
    
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
            if(shape.getColor() != null) {
                s.setAttribute("color", String.valueOf(shape.getColor().getRGB()));
            }
            if(shape.getFillColor() != null) {
                s.setAttribute("fill", String.valueOf(shape.getFillColor().getRGB()));
            }
            if(shape.getPosition() != null) {
                s.setAttribute("x", String.valueOf(shape.getPosition().x));
                s.setAttribute("y", String.valueOf(shape.getPosition().y));
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
            if(s.hasAttribute("color")) {
                shape.setColor(Color.getColor("color", Integer.valueOf(s.getAttribute("color"))));
            }
            if(s.hasAttribute("fill")) {
                shape.setFillColor(Color.getColor("color", Integer.valueOf(s.getAttribute("fill"))));
            }
            if(s.hasAttribute("x") && s.hasAttribute("y")) {
                shape.setPosition(new Point(Integer.valueOf(s.getAttribute("x")), Integer.valueOf(s.getAttribute("y"))));
            }
            shapes.add(shape);
        }
    }
    
    public List<Shape> getShapes() {
        return shapes;
    }
}
