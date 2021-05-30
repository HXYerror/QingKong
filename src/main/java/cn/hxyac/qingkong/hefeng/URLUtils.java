package cn.hxyac.qingkong.hefeng;

public class URLUtils {

    public static final  String KEY = "b7a8b7ded5c9436284d847de2761db2b";

    public static final  String KEY2 = "";

    public static String now_url = "https://devapi.qweather.com/v7/weather/now";

    public static String three_url = "https://devapi.qweather.com/v7/weather/3d";

    public static String city_url = "https://geoapi.qweather.com/v2/city/lookup";

    public static String now_urlmoney = "https://api.qweather.com/v7/weather/now";

    public static String three_urlmoney = "https://api.qweather.com/v7/weather/3d";


    public static String getTemp_url(String cityCode){
        String url = now_url+"?location="+cityCode+"&key="+KEY;

        String url2 = now_urlmoney+"?location="+cityCode+"&key="+KEY2;

        return url;
    }

    public static String getThree_url(String cityCode){
        String url = three_url+"?location="+cityCode+"&key="+KEY;

        String url2 = three_urlmoney+"?location="+cityCode+"&key="+KEY2;

        return url;
    }

    public static String getCity_url(String city){
        String url = city_url+"?location="+city+"&key="+KEY;
        return url;
    }
}
