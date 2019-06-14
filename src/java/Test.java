
import com.util.security.Protection;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Erik
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        BASE64Encoder base64encoder = new BASE64Encoder();
        byte encrip[] = Protection.makeDigest("eislas", "root");
        String pw_encrip = base64encoder.encode(encrip);
        
        System.out.println(pw_encrip);
    }

}
