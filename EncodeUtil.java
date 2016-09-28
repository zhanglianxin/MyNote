package test;

import java.util.Base64;
import java.util.Base64.Encoder;

public class EncodeUtil {

    public static char[] encode(String string) throws Exception {
        char[] chs = string.toCharArray();
        String result = "";
        for (char c : chs) {
            result = result + "\\" + Integer.toHexString(c)/* + " " */;
        }

        Encoder encoder = Base64.getEncoder();

        byte[] bytes = encoder.encode(result.getBytes());
        char[] cs = new char[bytes.length];

        for (int i = 0; i < bytes.length; i++) {
//            System.out.print(bytes[i] + " ");
            cs[i] = (char) bytes[i];
        }

//        System.out.println(cs);
        
        return cs;
    }
    
}
