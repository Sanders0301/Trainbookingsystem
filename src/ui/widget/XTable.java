package ui.widget;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class XTable extends JTable {
    private DefaultTableCellRenderer cellRenderer = new MyCellRenderer();

    public XTable() {
        setFont(new Font("TimesRoman", Font.PLAIN, 14));
        setRowHeight(30);
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        return cellRenderer;
    }

    private class MyCellRenderer extends DefaultTableCellRenderer{
        MyCellRenderer(){
            setHorizontalAlignment(CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table,
                                                       Object value, boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            if (row % 2 == 0)
                setBackground(new Color(255, 255, 255, 30)); //Sets the base color for odd rows
            else if (row % 2 == 1)
                setBackground(new Color(0, 0, 0, 10)); //Sets the base color for an even number of rows
            return super.getTableCellRendererComponent(table, value,
                    isSelected, hasFocus, row, column);
        }
    }
}
