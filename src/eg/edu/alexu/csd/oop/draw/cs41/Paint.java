package eg.edu.alexu.csd.oop.draw.cs41;

import java.awt.EventQueue;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ListSelectionModel;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.cs41.gui.ShapeListCellRender;
import eg.edu.alexu.csd.oop.draw.cs41.gui.components.CircularButton;
import eg.edu.alexu.csd.oop.draw.cs41.gui.components.DrawingArea;
import eg.edu.alexu.csd.oop.draw.cs41.gui.components.PropertyEntry;
import eg.edu.alexu.csd.oop.draw.cs41.gui.components.ShapeListObject;
import eg.edu.alexu.csd.oop.draw.cs41.gui.components.colorPicker.ColorPickerDialog;
import eg.edu.alexu.csd.oop.draw.cs41.gui.listeners.DrawMouseLisenter;
import eg.edu.alexu.csd.oop.draw.cs41.gui.listeners.ShapesSelectListener;
import eg.edu.alexu.csd.oop.draw.cs41.gui.tools.FillAndColorHandler;

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
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JSeparator;
import javax.swing.JCheckBox;

public class Paint {
    final static int CELL_SIZE = 30;
    private DrawingEngine engine;
    private DrawingArea area;
    private DrawMouseLisenter mouseListener;

    private JFrame frame;
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
        panel.setBounds(0, 0, 984, 601);
        panel.setLayout(null);
        frame.getContentPane().add(panel);

      //splitpane to split drawing area and options
        JSplitPane splitPane = new JSplitPane();
        splitPane.setBackground(Color.WHITE);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setBounds(0, 0, 804, 601);
//        panel.add(splitPane, BorderLayout.CENTER);
        
      //options panel
        JPanel panel_1 = new JPanel();
        panel_1.setSize(984, 52);
        panel_1.setBackground(Color.WHITE);
        panel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
     //   splitPane.setLeftComponent(panel_1);
        panel_1.setLayout(null);
        panel_1.setPreferredSize(new Dimension(840, 52));
        panel_1.setLocation(new Point(0, 0));
        engine = new Engine();
        area = new DrawingArea(engine);
        area.setSize(new Dimension(834, 542));
        area.setLocation(new Point(150, 0));
        mouseListener = new DrawMouseLisenter(area);
        area.setMouseListener(mouseListener);
        
        panel_1.add(deleteButton());
        
        panel_1.add(strockSlider());
        
        //change shape color (open color selector)
        panel_1.add(colorSelector());
        
        //change shape fill color (open color selector)
        panel_1.add(fillColorSelector());
        
        //tool for selecting shapes
        panel_1.add(selectTool());
        
        panel_1.add(fillTool());
        panel_1.add(colorPickTool());
        
        PropertyEntry pe = new PropertyEntry("Transperancy");
        pe.setLocation(487, 11);
        pe.setSize(150, 30);
        pe.addlistener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent arg0) {
                Double v = pe.getValue();
                mouseListener.setTrans(v);
                area.repaint();
            }
        });
        
        panel_1.add(pe);
        
        JPanel panel_2 = new JPanel();
        panel_2.setSize(984, 549);
        panel_2.setBorder(new EmptyBorder(0, 0, 0, 0));
        panel_2.setLocation(new Point(0, 52));
        //splitPane.setRightComponent(panel_2);
        panel_2.setLayout(null);
        panel_2.add(listOfShapes());
        panel_2.add(area);
        panel.add(panel_1);
        
        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.VERTICAL);
        separator.setBounds(153, 11, 1, 30);
        panel_1.add(separator);
        
        JSeparator separator_1 = new JSeparator();
        separator_1.setOrientation(SwingConstants.VERTICAL);
        separator_1.setBounds(305, 11, 1, 30);
        panel_1.add(separator_1);
        
        JSeparator separator_2 = new JSeparator();
        separator_2.setOrientation(SwingConstants.VERTICAL);
        separator_2.setBounds(476, 11, 1, 30);
        panel_1.add(separator_2);
        
        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_3.setBackground(Color.WHITE);
        panel_3.setBounds(86, 6, 57, 40);
        panel_1.add(panel_3);
        panel_3.setLayout(null);
        
        JCheckBox noFill = new JCheckBox("");
        noFill.setBounds(3, 10, 20, 20);
        panel_3.add(noFill);
        noFill.setToolTipText("No Fill");
        noFill.setSelected(true);
        noFill.setBackground(Color.WHITE);
        
        noFill.addChangeListener(new ChangeListener() {
            
            @Override
            public void stateChanged(ChangeEvent arg0) {
                boolean b = noFill.isSelected();
                if(b) {
                    mouseListener.setNoFill(true);
                } else {
                    mouseListener.setNoFill(false);
                }
                
                
            }
        });
        
        JLabel label = new JLabel("");
        label.setBounds(23, 5, 30, 30);
        panel_3.add(label);
        label.setIcon(new ImageIcon(Paint.class.getResource("/eg/edu/alexu/csd/oop/draw/cs41/resources/no-filling.png")));
        panel.add(panel_2);
//        panel_3.add(listOfLayers());
    }
    
    private void initializeFrame() {
        frame = new JFrame();
        frame.setBounds(50, 100, 1000, 640);
        frame.setMenuBar(menuBar());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
    }
   
    private JScrollPane listOfShapes() {
        list = new JList();
        list.setBorder(null);
        list.setFixedCellHeight(CELL_SIZE);
        list.setFixedCellWidth(CELL_SIZE);
        list.setAlignmentX(Component.LEFT_ALIGNMENT);
        list.setAlignmentY(Component.TOP_ALIGNMENT);
        list.setLayoutOrientation(list.VERTICAL);
        list.setVisibleRowCount(1);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new ShapeListCellRender());
        list.setListData(getListItems());
        
        JScrollPane listScroll = new JScrollPane(list);
        listScroll.setBounds(0, 0, 150, 549);
        listScroll.setPreferredSize(new Dimension(CELL_SIZE * 4 + 3, CELL_SIZE * 3 + 20));
        
        ShapesSelectListener listListener = new ShapesSelectListener(mouseListener);
        list.addListSelectionListener(listListener);
        
        return listScroll;
    }
    
    
    private JButton colorSelector() {
        CircularButton colorButton = new CircularButton(10);
        colorButton.setBackground(mouseListener.getColor());

        ColorPickerDialog cpd = new ColorPickerDialog(pce ->{
            Color color = (Color) pce.getNewValue();
            mouseListener.setColor(color);
            colorButton.setBackground(color);
        } , mouseListener.getColor());
        cpd.setUndecorated(true);
        cpd.setSize(200, 200);
        colorButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Point p = colorButton.getLocationOnScreen();
                p.x += 10;
                p.y += 10;
                cpd.setLocation(p);
                cpd.setVisible(!cpd.isVisible());
            }
        });
        colorButton.setBounds(10, 11, 30, 30);
        //colorButton.setBorderPainted(false);
        return colorButton;
    }
    
    private JButton fillColorSelector() {
        CircularButton colorButton = new CircularButton(10);
        colorButton.setBackground(mouseListener.getFillColor());

        ColorPickerDialog cpd = new ColorPickerDialog(pce ->{
            Color color = (Color) pce.getNewValue();
            mouseListener.setFillColor(color);
            colorButton.setBackground(color);
        } , mouseListener.getFillColor());
        cpd.setUndecorated(true);
        cpd.setSize(200, 200);
        colorButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Point p = colorButton.getLocationOnScreen();
                p.x += 10;
                p.y += 10;
                cpd.setLocation(p);
                cpd.setVisible(!cpd.isVisible());
            }
        });
        colorButton.setBounds(50, 11, 30, 30);
        mouseListener.fillButton(colorButton);
        return colorButton;
    }
    
    
    private JButton selectTool() {
        ImageIcon icon = new ImageIcon(this.getClass().getResource("resources/select.png"));
        JButton selectButton = new JButton(icon);
        selectButton.setBackground(Color.WHITE);
        
        selectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                list.clearSelection();
                mouseListener.setCursor(new Cursor(Cursor.HAND_CURSOR));
                mouseListener.setSelecting(true);
            }
        });
        selectButton.setBounds(316, 11, 30, 30);
        
        return selectButton;
    }
    
    private JButton deleteButton() {
        ImageIcon img = new ImageIcon(this.getClass().getResource("resources/delete.png"));
        JButton deleteButton = new JButton(img);
        deleteButton.setBounds(356, 11, 30, 30);
        deleteButton.setBackground(Color.white);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mouseListener.deleteSelected();
            }
        });
        
        return deleteButton;
    }
    private JButton fillTool() {
        ImageIcon img = new ImageIcon(this.getClass().getResource("resources/fill.png"));
        JButton fillTool = new JButton(img);
        fillTool.setBounds(396, 11, 30, 30);
        fillTool.setBackground(Color.white);
        fillTool.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Image i = Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("resources/fill.png"));
                Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(i, new Point(0,0), "cFill");
                mouseListener.setCursor(c);
                mouseListener.setFilling(true);
                list.clearSelection();
            }
        });
        
        return fillTool;
    }
    private JButton colorPickTool() {
        ImageIcon img = new ImageIcon(this.getClass().getResource("resources/pick.png"));
        JButton colorPicker = new JButton(img);
        colorPicker.setBounds(436, 11, 30, 30);
        colorPicker.setBackground(Color.white);
        colorPicker.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Image i = Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("resources/pick.png"));
                Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(i, new Point(0,0), "cFill");
                mouseListener.setCursor(c);
                mouseListener.setPicking(true);
                list.clearSelection();
            }
        });
        
        return colorPicker;
    }
    
    private JSlider strockSlider() {
        JSlider slider = new JSlider(SwingConstants.HORIZONTAL, 1, 5, 1);
        slider.setLocation(164, 11);
        slider.setSize(new Dimension(131, 30)); 
        slider.setBackground(Color.white);
        slider.setToolTipText("Stroke");
        slider.addChangeListener(new ChangeListener() {
            
            @Override
            public void stateChanged(ChangeEvent e) {
                mouseListener.setStroke(slider.getValue());
                area.repaint();
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
        
        MenuItem saveJPG = new MenuItem("Export as JPG");
        saveJPG.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                FillAndColorHandler f = new FillAndColorHandler(area.getWidth(), area.getHeight());
                f.saveImage(area);
            }
        });
        
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveJPG);
        fileMenu.add(exitItem);
        mb.add(fileMenu);
        
        Menu editMenu = new Menu("Edit");
        MenuItem undoItem = new MenuItem("Undo");
        undoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                engine.undo();
                //mouseListener.setSelecting(selecting);(null);
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
                //mouseListener.setShape(null);
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
