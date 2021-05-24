package cn.hxyac.qingkong;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

/**
 D:\Android\android-sdk-windows\emulator\emulator -avd Pixel_2_API_30 -dns-server 114.114.114.114
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("xianshi");
        setContentView(R.layout.activity_main);
    }
}