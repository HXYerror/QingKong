package cn.hxyac.qingkong.hefeng;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

public class HttpUtils {

    public static String getJsonContent(String path){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            URL url = new URL(path);
            //System.out.println(path);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            InputStream is = conn.getInputStream();
            int hasRead = 0;
            byte[]buf = new byte[1024];
            while ((hasRead = is.read(buf))!=-1){
                baos.write(buf,0,hasRead);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return baos.toString();
    }
}
