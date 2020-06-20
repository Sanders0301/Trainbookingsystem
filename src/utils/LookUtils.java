package utils;

import javax.swing.*;

public class LookUtils {
    public static void darcula() {
        try {
           // UIManager.setLookAndFeel("com.bulenkov.darcula.DarculaLaf");
	  UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void beautyEye() {
        try {
            UIManager.put("RootPane.setupButtonVisible", false);
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
