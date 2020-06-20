package utils;


public class StringUtils {
    public static boolean empty(String s){
        return s == null || s.equals("");
    }
    public static boolean empty(Object s){
        return s == null || s.equals("");
    }
}
