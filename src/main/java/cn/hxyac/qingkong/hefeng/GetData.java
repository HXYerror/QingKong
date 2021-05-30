package cn.hxyac.qingkong.hefeng;

import java.util.concurrent.Callable;

public class GetData {

    public static String Data(final String url){
        final String[] str = new String[1];
        Thread thread =  new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    str[0] =  HttpUtils.getJsonContent(url);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        while (thread.isAlive()){};
        return str[0];
    }
}
