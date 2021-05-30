package cn.hxyac.qingkong.db;

import android.app.Application;

public class UniteApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.initDB(this);
    }

}
