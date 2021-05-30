package cn.hxyac.qingkong.tools;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import androidx.annotation.Nullable;
import cn.hxyac.qingkong.R;

public class MusicService extends Service {

    static boolean ifPlayer = true;
    private MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate(){
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // The service is starting, due to a call to startService()
        int resid = intent.getIntExtra("resid",R.raw.error);
        player = MediaPlayer.create(this,resid);
        if(ifPlayer) {
            player.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy(){
        if(ifPlayer) player.stop();
        super.onDestroy();
    }
}
