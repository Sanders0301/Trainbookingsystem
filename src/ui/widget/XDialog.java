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
 /**
  * Subclasses override this method to add custom controls
  */
    
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
/**
     * Add a component to the dialog box
     * @param text 
     * @param component 
     */
    
    public void addItem(String text, Component component) {
        panel.addItem(text, component);
    }
    /**
     * link JLabel
     * @param name 
     * @param text JLabel
     * @return {@link JLabel}
     */

    public JLabel addLabel(String name, String text) {
        return panel.addLabel(name, text);
    }
    /**
     * {@link JTextField}
     * @param name 
     * @param text JTextField
     * @return {@link JTextField}
     */
    
    public JTextField addField(String name, String text) {
        return panel.addField(name, text);
    }
//Only non-negative integers can be entered in a text box
    
    public JTextField addNumberField(String name, Integer value) {
        return panel.addNumberField(name, value);
    }
//Only non-negative decimals can be entered in a text field
    
    public JTextField addFloatField(String name, Float value) {
        return panel.addFloatField(name, value);
    }
/**
     * {@link JComboBox}
     * @param name 
     * @param options JComboBox
     * @return {@link JComboBox}
     */
    
    public JComboBox addComboBox(String name, String... options) {
        return panel.addComboBox(name, options);
    }
 /**
     * {@link JCheckBox}
     * @param name 
     * @param state JCheckBox
     * @return {@link JCheckBox}
     */
    
    public JCheckBox addCheckBox(String name, boolean state) {
        return panel.addCheckBox(name, state);
    }
    /**
     * {@link JTable}
     * @param text 
     * @return {@link JTable}
     */
    
    public JTable addTable(String text) {
        return panel.addTable(text);
    }
    /**
     * get{@link JTextField}
     * @param n 
     * @return JTextField
     */
    
    public String field(int n) {
        return panel.field(n);
    }
    /**
     * get{@link JTextField}
     * @param n 
     * @return JTextField
     */
    
    public int fieldInt(int n) {
        return panel.fieldInt(n);
    }
    
    /**
     * get{@link JTextField}
     * @param n 
     * @return JTextField
     */
    public float fieldFloat(int n) {
        return panel.fieldFloat(n);
    }
    /**
     * 获取{@link JComboBox}控件的值
     * @param n 控件的序号
     * @return JComboBox当前所选项的索引值（从0开始）
     */
    
    public int option(int n) {
        return panel.option(n);
    }

    /**
     * get{@link JComboBox}
     *
     * @param n 
     * @return JComboBox（start0）
     */
    public Object optionValue(int n) {
        return panel.optionValue(n);
    }

   /**
     * get{@link JCheckBox}
     * @param n 
     * @return JCheckBox
     */
    public boolean checked(int n) {
        return panel.checked(n);
    }

    /**
     * Determines the button's callback function
     */
    protected void onOK() {
        // add your code here
        dispose();
    }

    //Cancel button's callback function
    protected void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /**
     * Launches an untitled dialog box
     * convenient for {@link #popup(String)}
     */
    public void popup(){
        popup("");
    }

    /**
     * Set the appropriate size according to the layout and launch the dialog
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