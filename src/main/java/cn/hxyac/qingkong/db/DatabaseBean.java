package cn.hxyac.qingkong.db;

public class DatabaseBean {
    private int _id;
    private String cityCode;
    private String city;
    private String content;

    public DatabaseBean() {
    }

    public DatabaseBean(int _id, String city, String content,String cityCode) {
        this._id = _id;
        this.city = city;
        this.content = content;
        this.cityCode = cityCode;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
