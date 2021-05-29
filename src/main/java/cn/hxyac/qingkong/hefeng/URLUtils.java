package cn.hxyac.qingkong.hefeng;

public class URLUtils {

    public static final  String KEY = "b7a8b7ded5c9436284d847de2761db2b";

    public static String now_url = "https://devapi.qweather.com/v7/weather/now";

    public static String three_url = "https://devapi.qweather.com/v7/weather/3d";

    public static String getTemp_url(String city){
        String url = now_url+"?location="+city+"&key="+KEY;
        return url;
    }

    public static String getThree_url(String city){
        String url = three_url+"?location="+city+"&key="+KEY;
        return url;
    }
}
