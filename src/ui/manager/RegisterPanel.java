package ui.manager;

import java.awt.*;
import java.util.List;

import bean.Customer;
import bean.Manager;
import dao.MySQLManager;
import dao.DBManager;
import ui.base.ListTableModel;
import ui.base.ModelPanel;
import ui.widget.XDialog;
import utils.Constants;

public class RegisterPanel extends ModelPanel {
	private static final long serialVersionUID = 1L;
	private List<Customer> customers;

    private void fetchAll(){
       // customers = MySQLManager.getInstance().dao().getAllCustomers();
        customers = DBManager.getInstance().dao().getAllCustomers();
    }

    @Override
    public ListTableModel getTableModel() {
        if(customers == null) fetchAll();
        return new ListTableModel<Customer>(Constants.ColumnName.CUSTOMER, customers) {
            @Override
            public Object getValueAt(int row, int column) {
                Customer customer = customers.get(row);
                switch (column) {
                    case 0:
                        return customer.getIdNum();
                    case 1:
                        return customer.getName();
                    case 2:
                        return customer.getSexString();
                    case 3:
                        return customer.getTel();
                    case 4:
                        return customer.getCustomerTypeString();
                }
                return null;
            }
        };
    }

    @Override
    public float[] getColumnWeight(int columnCount) {
        return new float[]{2, 2, 1, 1, 1};
    }

    @Override
    public void onSearch(String key) {
        EventQueue.invokeLater(() -> {
            if (key.equals("")) {
                fetchAll();
            } else {
                //customers = MySQLManager.getInstance().dao().searchCustomers(key);
		customers = DBManager.getInstance().dao().searchCustomers(key);
            }
            refresh();
        });
    }

    @Override
    public void onInsert() {
        new XDialog() {
            @Override
            protected void initComponents() {
                
                String[] columns = Constants.ColumnName.CUSTOMER;
                addField(columns[0], "");
                addField(columns[1], "");
                addComboBox(columns[2], "Female", "Male");
                addField(columns[3], "");
                addComboBox(columns[4], "Adult", "Student");
            }

            @Override
            protected void onOK() {
                Customer customer = new Customer();
                customer.setIdNum(field(0));
                customer.setName(field(1));
                customer.setSex(option(2));
                customer.setTel(field(3));
                customer.setCustomerType(option(4) + 1);
                EventQueue.invokeLater(() -> {
                   // MySQLManager.getInstance().dao().insertCustomer(customer);
		    DBManager.getInstance().dao().insertCustomer(customer);
                    fetchAll();
                    refresh();
                    super.onOK();
                });
            }
        }.popup("Add User");
    }

    @Override
    public void onDelete(int[] selectedRows) {
        if(selectedRows.length <= 0) return;
        EventQueue.invokeLater(() -> {
            for (int row : selectedRows) {
                Customer customer = customers.get(row);
               // MySQLManager.getInstance().dao().deleteCustomer(customer);
	        DBManager.getInstance().dao().deleteCustomer(customer);
            }
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                customers.remove(i);
            }
            refresh();
        });
    }

    @Override
    public void onUpdate(int selectedRow) {
        if(selectedRow < 0) return;
        Customer customer = customers.get(selectedRow);
        new XDialog() {
            @Override
            protected void initComponents()
            {
                String[] columns = Constants.ColumnName.CUSTOMER;
                addField(columns[0], customer.getIdNum());
                addField(columns[1], customer.getName());
                addComboBox(columns[2], "Female", "Male").setSelectedIndex(customer.getSex());
                addField(columns[3], customer.getTel());
                addComboBox(columns[4], "Adult", "Student").setSelectedIndex(customer.getCustomerType() - 1);
            }
            @Override
            protected void onOK() {
                customer.setIdNum(field(0));
                customer.setName(field(1));
                customer.setSex(option(2));
                customer.setTel(field(3));
                customer.setCustomerType(option(4) + 1);
                EventQueue.invokeLater(() -> {
                  //  MySQLManager.getInstance().dao().updateCustomer(customer);
		    DBManager.getInstance().dao().updateCustomer(customer);
                    refresh();
                    super.onOK();
                });
            }
        }.popup("Update User");
    }
}