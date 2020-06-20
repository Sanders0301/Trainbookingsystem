package ui.seller;

import bean.TrainOrder;
import dao.MySQLManager;
import ui.manager.TrainOrderPanel;
import ui.widget.XDialog;
import javax.swing.*;

import Controller.TrainTicketClient;

import java.awt.*;
import java.io.ObjectOutputStream;
import java.net.Socket;

@SuppressWarnings("WeakerAccess")
public class HistoryPanel extends TrainOrderPanel {
    private JButton btnRefund;

    public HistoryPanel() {
        btnPanel.add(btnRefund = new JButton());
        btnRefund.setText("Refund");
        btnRefund.addActionListener(e -> onRefund(table.getSelectedRow()));
    }

    protected void onRefund(int selectedRow) {
        TrainOrder order = orders.get(selectedRow);
        Socket socket = TrainTicketClient.getSocket();
        new XDialog() {
            @Override
            protected void initComponents() {
                addItem(new JLabel("Refund " + order.getMoney() + " NZD.\nComfirm Refund"));
            }

            @Override
            protected void onOK() {
                order.setOrderState(TrainOrder.STATE_REFUNDED);
                EventQueue.invokeLater(() -> {
                    //MySQLManager.getInstance().dao().updateTrainOrder(order);
                	ObjectOutputStream oos = TrainTicketClient.getOos();
                	try {
						oos.writeObject(order);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    JOptionPane.showMessageDialog(null, "Refund succeed");
                    super.onOK();
                });
            }
        }.popup("Comfirm Refund");
    }
}
