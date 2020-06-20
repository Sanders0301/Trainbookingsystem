package ui.base;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import ui.widget.XPanel;
import ui.widget.XTable;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Arrays;

@SuppressWarnings("FieldCanBeLocal")
public abstract class ModelPanel extends XPanel {
    private JTextField editSearch;
    private JButton btnSearch, btnInsert, btnUpdate, btnDelete;
    protected JTable table;
    protected JPanel btnPanel;
    private DefaultTableModel model;

    public ModelPanel(){
       //Custom - styled tables 
        table = new XTable();
        model = getTableModel();
        setModel(model);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        setLayout(new BorderLayout());
        add(btnPanel(), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel btnPanel(){
        btnPanel = new JPanel(new FlowLayout());
        btnPanel.add(editSearch = new JTextField());
        btnPanel.add(btnSearch = new JButton("Search"));
        btnPanel.add(btnInsert = new JButton("ADD"));
        btnPanel.add(btnUpdate = new JButton("Update"));
        btnPanel.add(btnDelete = new JButton("Delete"));
        editSearch.setColumns(50);
        editSearch.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        btnSearch.addActionListener(e -> {
            String key = editSearch.getText();
            onSearch(key.trim());
        });
        btnInsert.addActionListener(e -> onInsert());
        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row >= 0){ // selected something
                onUpdate(modelIndex(row));
            }
        });
        btnDelete.addActionListener(e -> {
            int[] rows = table.getSelectedRows();
            if(rows.length > 0){
                onDelete(modelIndexes(rows));
            }
        });
        return btnPanel;
    }

    
    private int modelIndex(int index){
        RowSorter sorter = table.getRowSorter();
        if(sorter != null){
            return sorter.convertRowIndexToModel(index);
        }
        return index;
    }

    private int[] modelIndexes(int[] index){
        int[] res = new int[index.length];
        for(int i = 0; i < index.length; i++){
            res[i] = modelIndex(index[i]);
        }
        return res;
    }

    private void setModel(TableModel model){
        //Column width
        setColumnWeight();
        //The sorting
        RowSorter<TableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        table.setModel(model);
    }

    private void setColumnWeight(){
        addComponentListener(new ComponentListener() {
            boolean loaded = false;
            @Override
            public void componentResized(ComponentEvent e) {
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
                if(loaded) return;
                float[] weight = getColumnWeight(table.getColumnCount());
                float weightSum = 0;
                for(float w : weight)
                    weightSum += w;
                for(int i = 0; i < table.getColumnCount(); i++){
                    TableColumn column = table.getColumnModel().getColumn(i);
                    int width = (int) (getWidth() * (weight[i] / weightSum));
                    column.setPreferredWidth(width);
                }
                loaded = true;
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });
    }
    /**Gets the width of each column in the table, which can be overridden by a subclass
     * 
     * @param columnCount 
     * @return 
     */
    
    public float[] getColumnWeight(int columnCount){
        float[] defaultWeight = new float[columnCount];
        Arrays.fill(defaultWeight, 1.0f);
        return defaultWeight;
    }
/**
     * Update the data in the Table,{@link #getTableModel()}
     */
    
    public void refresh(){
        TableModel tableModel = getTableModel();
        setModel(tableModel);
        table.validate();
        table.updateUI();
    }

    public abstract DefaultTableModel getTableModel();
    public abstract void onSearch(String key);
    public abstract void onInsert();
    public abstract void onDelete(int[] selectedRows);
    public abstract void onUpdate(int selectedRow);
}