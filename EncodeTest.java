package mytest;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

public class EncodeTest {

    public static void main(String[] args) throws UnsupportedEncodingException {
        byte[] bs = "JD书吧".getBytes();
        String string = new String(bs, "iso8859-1");
        char[] chs = string.toCharArray();
        String result = "";
        for (char c : chs) {
            result = result + Integer.toHexString(c);
        }
        System.out.println(result);
        
        System.out.println("-----------------");
        
        LinkedList<String> list = new LinkedList<String>();
        int startIndex = result.indexOf("e"), endIndex = result.indexOf("e", startIndex + 1);
        while (endIndex - startIndex == 6) {
            System.out.println(startIndex + " --> " + endIndex);
            if (endIndex - 1 > result.length() - 1) {
                break;
            }
            
            startIndex = result.indexOf("e");
            startIndex = startIndex == -1 ? 0 : startIndex;
            endIndex = result.indexOf("e", startIndex + 1);
            endIndex = endIndex == -1 ? result.length() : endIndex;
            
            System.out.println(startIndex + " --> " + endIndex);
            System.out.println("原原原字符串 " + result);
            String subStr = result.substring(startIndex, endIndex);
            System.out.println("截取的字符串 " + subStr);
            
            list.addLast(subStr);
            
            result = result.replaceFirst(subStr, "");
            System.out.println("截后的字符串 " + result);
            System.out.println();
        }
        
        for (String str : list) {
            System.out.println(str);
        }
        
        System.out.println("+++++++++++++++++");
        
        while (result.length() > 0) {
            String subStr = result.substring(result.length() - 2);
            System.out.println("截取的字符串 " + subStr);
            
            list.addFirst(subStr);
            
            result = result.replaceFirst(subStr, "");
        }
        
        System.out.println("=================");
        
        byte[] bytes = new byte[list.size()];
        
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
            bytes[i] = (byte) Integer.parseInt(list.get(i), 16);
        }
        
        String s = new String(bytes, "iso8859-1");
        System.out.println(s);
        
    }

}
