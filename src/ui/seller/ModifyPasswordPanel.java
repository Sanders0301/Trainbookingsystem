package ui.seller;


import javax.swing.*;

import Controller.TrainTicketClient;
import dao.MySQLManager;
import ui.widget.XPanel;
import utils.Constants;

import java.awt.*;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ModifyPasswordPanel extends XPanel {

    private JPasswordField addPasswordField(String name) {
        JPasswordField field = new JPasswordField();
        addItem(name, field);
        return field;
    }

    @Override
    protected void initComponents() {
        final JPasswordField oldPass = addPasswordField("Old password"),
                newPass = addPasswordField("New Password"),
                newPassConfirm = addPasswordField("Comfirm new password");
        addBtn("Update").addActionListener(e -> {
            String pass = String.valueOf(oldPass.getPassword());
            String now = String.valueOf(newPass.getPassword());
            String confirm = String.valueOf(newPassConfirm.getPassword());
            modifyPassword(pass, now, confirm);
        });
    }

    private void modifyPassword(String pass, String now, String confirm) {
        EventQueue.invokeLater(() -> {
            if (pass.equals(Constants.currentManager.getPassword())) {
                if (now.equals(confirm)) {
                	Socket socket = TrainTicketClient.getSocket();
                	try {
						ObjectOutputStream oos = TrainTicketClient.getOos();
						Constants.currentManager.setPassword(now);
						oos.writeObject(Constants.currentManager);
						oos.flush();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    //MySQLManager.getInstance().dao().modifyPassword(Constants.currentManager, now);
                    JOptionPane.showMessageDialog(null, "Update success!�Please remember new password");
                } else {
                    JOptionPane.showMessageDialog(null, "The password is different! Please re-enter");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Old password error! Please re-enter");
            }
        });
    }
}
