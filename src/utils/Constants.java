package utils;


import javax.swing.*;

import bean.Manager;
import ui.login.LoginFrame;

public class Constants {
    public static final String BG_PATH = "C:\\Users\\sun\\Documents\\NetBeansProjects\\TrainTicketsManager\\res\\bg.jpg";

    public static class ColumnName{
        public static final String[] CUSTOMER = {
                "Passport ID", "Name", "Gender", "Phone number", "Type of user"
        };
        public static final String[] MANAGER = {
                "Admin ID", "Ticket office ID", "User name", "Password", "Name", "Gender", "Administrator category"
        };
        public static final String[] STATION = {
                "Station ID", "Station name"
        };
        public static final String[] TICKET_POINT = {
                "Ticket office ID", "Ticket office name", "Ticket office address", "Opening hours"
        };
        public static final String[] TRAIN = {
                "Train number", "Type of train", "Stops along the route"
        };
        public static final String[] SCHEDULE = {
                "Train journey ID", "Starting time", "Pre-sale time", "Speed（km/h）", "Train number"
        };
        public static final String[] ORDER_INSERT = {
                "Ticket office ID", "User passport ID ", "Train journey ID", "Train number", "Carriage", "Seat number", "Type of seat", "Starting station", "Terminus", "Student ticket", "Amount", "Order status"
        };
        public static final String[] ORDER = {
                "Order ID", "User name", "Train number", "Starting station", "Terminus", "Amount", "Order status" 
        };
    }

    public static Manager currentManager;

    public static boolean checkManagerType(int type) {
        if (Constants.currentManager == null) {
            JOptionPane.showMessageDialog(null, "Log out");
            WidgetUtils.popup(LoginFrame.class, WindowConstants.EXIT_ON_CLOSE);
            System.exit(0);
            return false;
        } else if (Constants.currentManager.getManagerType() != type) {
            JOptionPane.showMessageDialog(null, "No corresponding administrator rights");
            System.exit(0);
            return false;
        }
        return true;
    }
}
