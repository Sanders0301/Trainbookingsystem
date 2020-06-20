package ui.widget;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"FieldCanBeLocal", "WeakerAccess"})
public class XPanel extends JPanel implements Measurable {
    private List<Component> labelList = new ArrayList<>();
    private List<Component> componentList = new ArrayList<>();
    private List<JButton> btnList = new ArrayList<>();
    private Image backgroundImage;

    private int leftMargin = 12;
    private int topMargin = 12;
    private int labelWidth = 100;
    private int labelHeight = 30;
    private int fieldWidth = 300;
    private int fieldHeight = 30;
    private int padding = 10;
    private int btnWidth = 100, btnHeight = 30;
    protected int panelWidth = 400, panelHeight = 300;

    public XPanel() {
        this.setLayout(null);
        initComponents();
        relayout();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this);
        }
    }

    /**
     * 
     */
    protected void initComponents() {
    }

    @SuppressWarnings("SameParameterValue")
    private void move(Component component, int x, int y) {
        Rectangle rect = component.getBounds();
        rect.x += x;
        rect.y += y;
        component.setBounds(rect);
    }

    /**
     * 
     */
    private void relayout() {
        int panelWidth = 0, panelHeight;
        int x = leftMargin + padding, y = topMargin, width = labelWidth, height = labelHeight;
        for (int i = 0; i < labelList.size(); i++) {
            y += padding;
            int margin = 0;
            if (labelList.get(i) != null) {
                margin = width;
                labelList.get(i).setBounds(x, y, width, height);
            }
            Component c = componentList.get(i);
            int componentHeight = fieldHeight, componentWidth = fieldWidth;
            if (c instanceof Measurable) {
                componentHeight = ((Measurable) c).height();
                componentWidth = ((Measurable) c).width();
                if (componentWidth == -1) {
                    componentWidth = panelWidth - margin;
                }
                panelWidth = Math.max(panelWidth, margin + componentWidth);
            }
            c.setBounds(x + margin + padding, y, componentWidth, componentHeight);
            y += componentHeight;
            y += padding;
        }
        y += padding;
        // add btn as last line
        int offset = x;
        for (JButton btn : btnList) {
            btn.setBounds(x + offset, y, btnWidth, btnHeight);
            offset += padding + btnWidth + padding;
        }


        y += btnHeight;
        y += padding;
        panelWidth = Math.max(panelWidth, x + width + fieldWidth);
        panelWidth = Math.max(panelWidth, offset);
        // let button align center to parent
        for (JButton btn : btnList) {
            move(btn, (panelWidth - offset) / 2, 0);
        }
        panelWidth += padding + padding + leftMargin + 60; // skin param
        panelHeight = y + topMargin + 85;
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
    }

    public Component componentAt(int n) {
        return componentList.get(n);
    }


    private abstract static class MeasurableItem extends JPanel implements Measurable {
    }

    public void addItem(Component component, int width, int height) {
        addItem(new MeasurableItem() {
            {
                setLayout(null);
                add(component);
                component.setBounds(0, 0, width(), height());
            }

            @Override
            public int width() {
                return width;
            }

            @Override
            public int height() {
                return height;
            }
        });
    }

    public void removeAllItems(){
        labelList.clear();
        componentList.clear();
    }

    public void addItem(Component component) {
        addItem(null, component);
    }

    /**
     * 
     *
     * @param text      
     * @param component 
     */
    public void addItem(String text, Component component) {
        if (text != null) {
            JLabel label = new JLabel();
            label.setText(text);
            labelList.add(label);
            this.add(label);
        } else {
            labelList.add(null);
        }
        componentList.add(component);
        this.add(component);
        relayout();
    }

    /**
     * {@link JLabel}
     *
     * @param name 
     * @param text JLabel
     * @return {@link JLabel}
     */
    public JLabel addLabel(String name, String text) {
        JLabel label = new JLabel(text);
        addItem(name, label);
        return label;
    }

    /**
     * {@link JTextField}
     *
     * @param name 
     * @param text JTextField
     * @return {@link JTextField}
     */
    public JTextField addField(String name, String text) {
        JTextField edit = new JTextField();
        edit.setColumns(30);
        edit.setText(text);
        addItem(name, edit);
        return edit;
    }

    /**
     * {@link JTextField}
     *
     * @param text JTextField
     * @return {@link JTextField}
     */
    public JTextField addHint(String text) {
        XTextField edit = new XTextField(text);
        edit.setColumns(30);
        addItem(edit);
        return edit;
    }

    /**
     * {@link XPasswordField}
     *
     * @param text XPasswordField 
     * @return {@link XPasswordField}
     */
    public XPasswordField addPasswordHint(String text) {
        XPasswordField edit = new XPasswordField(text);
        edit.setColumns(30);
        addItem(edit);
        return edit;
    }

    /**
     * {@link JTextField}
     *
     * @param name  
     * @param value 
     * @return {@link JTextField}
     */
    public JTextField addNumberField(String name, Integer value) {
        JTextField field = new JTextField();
        field.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE))) {
                    getToolkit().beep();
                    e.consume();
                }
            }
        });
        if (value != null) {
            field.setText(String.valueOf(value));
        }
        addItem(name, field);
        return field;
    }

    /**
     * {@link JTextField}
     *
     * @param name  
     * @param value 
     * @return {@link JTextField}
     */
    public JTextField addFloatField(String name, Float value) {
        JTextField field = new JTextField();
        field.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == '.') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE))) {
                    getToolkit().beep();
                    e.consume();
                }
            }
        });
        if (value != null) {
            field.setText(String.valueOf(value));
        }
        addItem(name, field);
        return field;
    }

    /**
     * {@link JComboBox}
     *
     * @param     
     * @param �
     * @return {@link JComboBox}
     */
    public JComboBox addComboBox(String name, String... options) {
        JComboBox box = new JComboBox<>(options);
        addItem(name, box);
        return box;
    }

    /**
     * {@link JCheckBox}
     *
     * @param  
     * @param  JCheckBox
     * @return {@link JCheckBox}
     */
    public JCheckBox addCheckBox(String name, boolean state) {
        JCheckBox box = new JCheckBox("", state);
        addItem(name, box);
        return box;
    }

    /**
     * {@link JTable}
     *
     * @param 
     * @return {@link JTable}
     */
    public JTable addTable(String text) {
        XTable table = new XTable();
        ComponentScroll scrollPane = new ComponentScroll(table);
        addItem(text, scrollPane);
        return table;
    }

    /**
     * {@link JButton}
     *
     * @param  
     * @return {@link JButton}
     */
    public JButton addBtn(String text) {
        JButton btn = new JButton(text);
        btnList.add(btn);
        this.add(btn);
        relayout();
        return btn;
    }

    /**
     * {@link JTextField}
     *
     * @param  
     * @return JTextField
     */
    public String field(int n) {
        return ((JTextField) componentList.get(n)).getText();
    }

    /**
     * {@link JTextField}
     *
     * @param  
     * @return JTextField
     */
    public int fieldInt(int n) {
        return Integer.parseInt(((JTextField) componentList.get(n)).getText());
    }

    /**
     * {@link JTextField}
     *
     * @param 
     * @return JTextField
     */
    public float fieldFloat(int n) {
        return Float.parseFloat(((JTextField) componentList.get(n)).getText());
    }

    /**
     * {@link JComboBox}
     *
     * @param 
     * @return JComboBox
     */
    public int option(int n) {
        return ((JComboBox) componentList.get(n)).getSelectedIndex();
    }

    /**
     * {@link JComboBox}
     *
     * @param  
     * @return JComboBox
     */
    public Object optionValue(int n) {
        return ((JComboBox) componentList.get(n)).getSelectedItem();
    }

    /**
     * {@link JCheckBox}
     *
     * @param  
     * @return JCheckBox
     */
    public boolean checked(int n) {
        return ((JCheckBox) componentList.get(n)).isSelected();
    }


    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }


    public void setBackgroundImage(String path) {
        try {
            this.backgroundImage = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int width() {
        return panelWidth;
    }

    @Override
    public int height() {
        return panelHeight;
    }

    /**
     * Table
     */
    private static class ComponentScroll extends JScrollPane implements Measurable {
        private static final int TABLE_WIDTH = 300;
        private static final int TABLE_HEIGHT = 200;

        ComponentScroll(Component component) {
            super(component);
        }

        @Override
        public int width() {
            return TABLE_WIDTH;
        }

        @Override
        public int height() {
            return TABLE_HEIGHT;
        }
    }
}
