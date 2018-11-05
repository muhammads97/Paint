package eg.edu.alexu.csd.oop.draw.cs41.gui;

import java.awt.Component;
import java.net.URL;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class ShapeListCellRender implements ListCellRenderer<JLabel>{
    protected static Border noFocusBorder = new EmptyBorder(15, 1, 1, 1);

    protected static TitledBorder focusBorder = new TitledBorder(LineBorder.createGrayLineBorder(),
        "title");
    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
    @Override
    public Component getListCellRendererComponent(JList<? extends JLabel> list, JLabel label, int index, boolean selected, boolean focus) {
        JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, label, index, selected, focus);
        ImageIcon icon = (ImageIcon) label.getIcon();
        if(icon == null) {
            URL url = ShapeListCellRender.class.getResource("../resources/default.png");
            icon = new ImageIcon(url);
        }
        renderer.setIcon(icon);
        renderer.setText(null);
        renderer.setToolTipText(((ShapeListObject)label).getName());
        return renderer;
    }

}
