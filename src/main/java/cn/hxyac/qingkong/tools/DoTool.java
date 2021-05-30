package cn.hxyac.qingkong.tools;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class DoTool {

    private static final String MY_ACTION = "qingkong.broadcast.MY_ACTION";
    private static boolean ifPlayer = true;

    public static void runMusic(final Context context, final int resid){
        if(!ifPlayer) return;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(context, MusicService.class);
                intent.putExtra("resid",resid);
                context.startService(intent);
            }
        });
        thread.start();
    }

    public static void runToast(final Context context,final String packageName, final String msg){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MY_ACTION);
                intent.putExtra("msg", msg);
                intent.setComponent(new ComponentName(packageName,"cn.hxyac.qingkong.tools.MyReceiver"));
                context.sendBroadcast(intent);
            }
        });
        thread.start();
    }

    public static boolean isIfPlayer() {
        return ifPlayer;
    }

    public static void setIfPlayer(boolean ifPlayer) {
        DoTool.ifPlayer = ifPlayer;
    }
}
