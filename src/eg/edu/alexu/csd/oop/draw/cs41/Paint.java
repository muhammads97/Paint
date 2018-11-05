package eg.edu.alexu.csd.oop.draw.cs41;

import java.awt.EventQueue;
import java.net.URL;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ListSelectionModel;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.cs41.gui.DrawMouseLisenter;
import eg.edu.alexu.csd.oop.draw.cs41.gui.DrawingArea;
import eg.edu.alexu.csd.oop.draw.cs41.gui.ImageButton;
import eg.edu.alexu.csd.oop.draw.cs41.gui.PropertyEntry;
import eg.edu.alexu.csd.oop.draw.cs41.gui.ShapeListCellRender;
import eg.edu.alexu.csd.oop.draw.cs41.gui.ShapeListObject;
import eg.edu.alexu.csd.oop.draw.cs41.gui.ShapesSelectListener;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JList;
import java.awt.Component;
import java.awt.Cursor;

import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import javax.swing.JSplitPane;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.CardLayout;
import javax.swing.border.EmptyBorder;

public class Paint {
    final static int CELL_SIZE = 30;
    private DrawingEngine engine;
    private DrawingArea area;
    private DrawMouseLisenter mouseListener;

    private JFrame frame;
    private JPanel optionsPanel;
    private Panel shapeSelectedOptions;
    private JPanel propertiesPanel;
    private JList list;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Paint paint = new Paint();
                    paint.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Paint() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        initializeFrame();
        
      //main panel
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 804, 601);
        panel.setLayout(null);
        frame.getContentPane().add(panel);

      //splitpane to split drawing area and options
        JSplitPane splitPane = new JSplitPane();
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setBounds(0, 0, 804, 601);
        panel.add(splitPane, BorderLayout.CENTER);
        
      //options panell
        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new EmptyBorder(0, 0, 0, 0));
        splitPane.setLeftComponent(panel_1);
        panel_1.setLayout(null);
        panel_1.setPreferredSize(new Dimension(840, 130));
        
        optionsPanel = new JPanel();
        optionsPanel.setBounds(297, 11, 495, 110);
        optionsPanel.setLayout(new CardLayout(0, 0));
        panel_1.add(optionsPanel);
        
        propertiesPanel = new JPanel();
        optionsPanel.add(propertiesPanel, "propertiesPanel");
        propertiesPanel.setLayout(null);
        
        engine = new Engine();
        area = new DrawingArea(engine);
        area.setSize(new Dimension(800, 460));
        mouseListener = new DrawMouseLisenter(area, optionsPanel, propertiesPanel);
        area.setMouseListener(mouseListener);
        
        //shapes list
        panel_1.add(listOfShapes());
        
        panel_1.add(strockSlider());
        
        //change shape color (open color selector)
        panel_1.add(colorSelector());
        
        //change shape fill color (open color selector)
        panel_1.add(fillColorSelector());
        
        //tool for selecting shapes
        panel_1.add(selectTool());
        
        
        
        shapeSelectedOptions = new Panel();
        shapeSelectedOptions.setBounds(0, 0, 162, 110);
        shapeSelectedOptions.setLayout(null);
        optionsPanel.add(shapeSelectedOptions, "shapeSelectedOptions");
        
       
        shapeSelectedOptions.add(moveButton());
        shapeSelectedOptions.add(deleteButton());
        shapeSelectedOptions.add(editButton());
        
        
        
        
        
        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new EmptyBorder(0, 0, 0, 0));
        splitPane.setRightComponent(panel_2);
        panel_2.setLayout(null);
        panel_2.add(area, BorderLayout.CENTER);
        
        CardLayout cl = (CardLayout)optionsPanel.getLayout();
        cl.show(optionsPanel, "propertiesPanel");
    }
    
    private void initializeFrame() {
        frame = new JFrame();
        frame.setBounds(100, 100, 820, 640);
        frame.setMenuBar(menuBar());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
    }
   
    private JScrollPane listOfShapes() {
        list = new JList();
        list.setBorder(new LineBorder(new Color(0, 191, 255), 2, true));
        list.setFixedCellHeight(CELL_SIZE);
        list.setFixedCellWidth(CELL_SIZE);
        list.setAlignmentX(Component.LEFT_ALIGNMENT);
        list.setAlignmentY(Component.TOP_ALIGNMENT);
        list.setLayoutOrientation(list.VERTICAL_WRAP);
        list.setVisibleRowCount(3);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new ShapeListCellRender());
        list.setListData(getListItems());
        
        JScrollPane listScroll = new JScrollPane(list);
        listScroll.setBounds(10, 11, 123, 110);
        listScroll.setPreferredSize(new Dimension(CELL_SIZE * 4 + 3, CELL_SIZE * 3 + 20));
        
        ShapesSelectListener listListener = new ShapesSelectListener(mouseListener, optionsPanel, propertiesPanel);
        list.addListSelectionListener(listListener);
        
        
        
        return listScroll;
    }
    
    private Button colorSelector() {
        Button colorButton = new Button();
        colorButton.setBackground(mouseListener.getColor());
        colorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(null, "select color", mouseListener.getColor());
                mouseListener.setColor(color);
                if(mouseListener.getShape() != null) {
                    mouseListener.getShape().setColor(color);
                    mouseListener.repaint();
                }
                colorButton.setBackground(color);
            }
        });
        colorButton.setBounds(176, 11, 49, 54);
        
        return colorButton;
    }
    
    private Button fillColorSelector() {
        Button fillButton = new Button();
        fillButton.setBackground(mouseListener.getFillColor());
        fillButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(null, "select color", mouseListener.getFillColor());
                mouseListener.setFillColor(color);
                if(mouseListener.getShape() != null) {
                    mouseListener.getShape().setFillColor(color);
                    mouseListener.repaint();
                }
               
                fillButton.setBackground(color);
            }
        });
        fillButton.setBounds(176, 71, 49, 50);
        
        return fillButton;
    }
    
    private ImageButton selectTool() {
        BufferedImage img = null;
        try {
            img = ImageIO.read(this.getClass().getResource("resources/select.png"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        ImageButton selectButton = new ImageButton(img);
        
        selectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                list.clearSelection();
                mouseListener.setCursor(Cursor.HAND_CURSOR);
                mouseListener.setShape(null);
            }
        });
        selectButton.setBounds(231, 11, 60, 110);
        
        return selectButton;
    }
    
    private ImageButton moveButton() {
        BufferedImage img = null;
        try {
            img = ImageIO.read(this.getClass().getResource("resources/move.png"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        ImageButton moveButton = new ImageButton(img);
        moveButton.setBounds(0, 0, 76, 51);
        moveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(mouseListener.isSelected()) {
                    mouseListener.setMove(true);
                }
            }
        });
        
        return moveButton;
    }
    
    private ImageButton deleteButton() {
        BufferedImage img = null;
        try {
            img = ImageIO.read(this.getClass().getResource("resources/delete.png"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        ImageButton deleteButton = new ImageButton(img);
        deleteButton.setBounds(86, 0, 76, 51);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                propertiesPanel.removeAll();
                CardLayout cl = (CardLayout)optionsPanel.getLayout();
                cl.show(optionsPanel, "propertiesPanel");
                mouseListener.deleteSelected();
            }
        });
        
        return deleteButton;
    }
    
    private ImageButton editButton() {
        BufferedImage img = null;
        try {
            img = ImageIO.read(this.getClass().getResource("resources/edit.png"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        ImageButton editButton = new ImageButton(img);
        editButton.setBounds(0, 60, 162, 50);
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                propertiesPanel.removeAll();
                CardLayout cl = (CardLayout)optionsPanel.getLayout();
                cl.show(optionsPanel, "propertiesPanel");
                if(mouseListener.getShape() == null) return;
                mouseListener.getShape().setColor(mouseListener.getColor());
                mouseListener.getShape().setFillColor(mouseListener.getFillColor());
                mouseListener.repaint();
                Map<String, Double> prop = mouseListener.getShape().getProperties();
                int x = 0;
                int y = 0;
                for(String key: prop.keySet()) {
                    PropertyEntry pe = new PropertyEntry(key);
                    pe.setValue(prop.get(key));
                    pe.addlistener(new ChangeListener() {
                        
                        @Override
                        public void stateChanged(ChangeEvent arg0) {
                            prop.put(key, pe.getValue());
                            area.repaint();
                        }
                    });
                    pe.setSize(new Dimension(120, 38));
                    pe.setBounds(new Rectangle(x, y, pe.getSize().width, pe.getSize().height));
                    x += pe.getSize().width;
                    if(x + 120 > propertiesPanel.getSize().width) {
                        x = 0;
                        y += 38;
                    }
                    propertiesPanel.add(pe);
                }
            }
        });
        
        return editButton;
    }
    
    private JSlider strockSlider() {
        JSlider slider = new JSlider(JSlider.VERTICAL, 1, 5, 1);
        slider.setLocation(140, 11);
        slider.setSize(new Dimension(30, 110));   
        slider.addChangeListener(new ChangeListener() {
            
            @Override
            public void stateChanged(ChangeEvent e) {
                mouseListener.setStroke(slider.getValue());
            }
        });
        return slider;
    }
    
    private MenuBar menuBar() {
        MenuBar mb = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem saveItem = new MenuItem("Save");
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.addChoosableFileFilter(new FileNameExtensionFilter("XML File", "xml"));
                fc.addChoosableFileFilter(new FileNameExtensionFilter("JSON File", "json"));
                fc.setMultiSelectionEnabled(false);
                fc.setAcceptAllFileFilterUsed(false);
                fc.setCurrentDirectory(new File(""));
                fc.showSaveDialog(null);
                
                File file = fc.getSelectedFile();
                if(file != null) {
                    engine.save(file.getPath());
                }
                
            }
        });
        
        MenuItem openItem = new MenuItem("Open");
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.addChoosableFileFilter(new FileNameExtensionFilter("XML File", "xml"));
                fc.addChoosableFileFilter(new FileNameExtensionFilter("JSON File", "json"));
                fc.setMultiSelectionEnabled(false);
                fc.setAcceptAllFileFilterUsed(false);
                fc.setCurrentDirectory(new File(""));
                fc.showOpenDialog(null);
                
                File file = fc.getSelectedFile();
                if(file != null) {
                    engine.load(file.getPath());
                    area.repaint();
                }
                
            }
        });
        
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                
            }
        });
        
        fileMenu.add(saveItem);
        fileMenu.add(openItem);
        fileMenu.add(exitItem);
        mb.add(fileMenu);
        
        Menu editMenu = new Menu("Edit");
        MenuItem undoItem = new MenuItem("Undo");
        undoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                engine.undo();
                mouseListener.setShape(null);
                area.setSelected(null);
                list.clearSelection();
                area.repaint();
            }
        });
        MenuItem redoItem = new MenuItem("redo");
        redoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                engine.redo();
                mouseListener.setShape(null);
                area.setSelected(null);
                list.clearSelection();
                area.repaint();
            }
        });
        
        editMenu.add(undoItem);
        editMenu.add(redoItem);
        mb.add(editMenu);
        
        Menu toolsMenu = new Menu("tools");
        MenuItem addJar = new MenuItem("import shape");
        addJar.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.addChoosableFileFilter(new FileNameExtensionFilter("JAR File", "jar"));
                fc.setMultiSelectionEnabled(false);
                fc.setAcceptAllFileFilterUsed(false);
                fc.setCurrentDirectory(new File("./"));
                fc.showOpenDialog(null);            
                File file = fc.getSelectedFile();
                if(file != null) {
                    engine.installPluginShape(file.getPath());
                    list.setListData(getListItems());
                }
                
            }
        });
        toolsMenu.add(addJar);
        mb.add(toolsMenu);
        return mb;
    }
    
    private JLabel[] getListItems() {
        JLabel[] items = new JLabel[engine.getSupportedShapes().size()];
        for(int i = 0; i < items.length; i++) {
            Class shapeClass = engine.getSupportedShapes().get(i);
            ImageIcon icon = null;
            try {
                URL url = this.getClass().getResource("resources/"+shapeClass.getName()+".png");
                icon = new ImageIcon(url);
            } catch (Exception e) {
                
            }
            items[i] = new ShapeListObject(icon, shapeClass);
        }
        return items;
    }
}
