package cn.hxyac.qingkong.hefeng;

import java.util.concurrent.Callable;

public class GetData {

    public static String DataTemp(final String cityCode){
        final String[] str = new String[1];
        Thread thread =  new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    str[0] =  HttpUtils.getJsonContent(URLUtils.getTemp_url(cityCode));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        while (thread.isAlive()){};
        System.out.println(str[0]);
        return str[0];
    }

    public static String DataThree(final String cityCode){
        final String[] str = new String[1];
        Thread thread =  new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    str[0] =  HttpUtils.getJsonContent(URLUtils.getThree_url(cityCode));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        while (thread.isAlive()){};
        System.out.println(str[0]);
        return str[0];
    }
}
