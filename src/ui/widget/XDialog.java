package ui.widget;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings({"FieldCanBeLocal", "UnusedReturnValue", "WeakerAccess"})
public class XDialog extends JDialog {
    private XPanel panel;
    protected JButton buttonOK, buttonCancel;

    public XDialog() {
        panel = new XPanel();

        buttonOK = panel.addBtn("Comfirm");
        buttonCancel = panel.addBtn("Cancel");

        setContentPane(panel);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        panel.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        initComponents();
    }

    
    protected void initComponents(){}

    public Component componentAt(int n) {
        return panel.componentAt(n);
    }

    public void addItem(Component component, int width, int height) {
        panel.addItem(component, width, height);
    }

    public void addItem(Component component) {
        panel.addItem(component);
    }

    
    public void addItem(String text, Component component) {
        panel.addItem(text, component);
    }

    
    public JLabel addLabel(String name, String text) {
        return panel.addLabel(name, text);
    }

    
    public JTextField addField(String name, String text) {
        return panel.addField(name, text);
    }

    
    public JTextField addNumberField(String name, Integer value) {
        return panel.addNumberField(name, value);
    }

    
    public JTextField addFloatField(String name, Float value) {
        return panel.addFloatField(name, value);
    }

    
    public JComboBox addComboBox(String name, String... options) {
        return panel.addComboBox(name, options);
    }

    
    public JCheckBox addCheckBox(String name, boolean state) {
        return panel.addCheckBox(name, state);
    }

    
    public JTable addTable(String text) {
        return panel.addTable(text);
    }

    
    public String field(int n) {
        return panel.field(n);
    }

    
    public int fieldInt(int n) {
        return panel.fieldInt(n);
    }

    
    public float fieldFloat(int n) {
        return panel.fieldFloat(n);
    }

    
    public int option(int n) {
        return panel.option(n);
    }

    
    public Object optionValue(int n) {
        return panel.optionValue(n);
    }

   
    public boolean checked(int n) {
        return panel.checked(n);
    }

    
    protected void onOK() {
        // add your code here
        dispose();
    }

    
    protected void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /**
     * 
     * convenient for {@link #popup(String)}
     */
    public void popup(){
        popup("");
    }

    /**
     * 
     * @param title 
     */
    public void popup(String title){
        EventQueue.invokeLater(() -> {
            setTitle(title);
            setSize(panel.panelWidth, panel.panelHeight);
            setResizable(false);
            setLocationRelativeTo(null);
            setVisible(true);
        });
    }
}