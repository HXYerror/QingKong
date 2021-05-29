package cn.hxyac.qingkong;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;
import cn.hxyac.qingkong.hefeng.GetData;

import java.util.ArrayList;
import java.util.List;

/**
 D:\Android\android-sdk-windows\emulator\emulator -avd Pixel_2_API_30 -dns-server 114.114.114.114
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView addCityIv,moreIv;
    LinearLayout pointLayout;
    RelativeLayout outLayout;
    ViewPager2 mainVp;

    List<Fragment> fragmentList;
    List<String> cityList;
    List<ImageView> imageViewList;

    CityFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载布局文件
        setContentView(R.layout.activity_main);

        addCityIv = findViewById(R.id.main_iv_add);
        moreIv = findViewById(R.id.main_iv_more);
        pointLayout = findViewById(R.id.main_layout_point);
        mainVp = findViewById(R.id.main_vp);

        //添加点击事件，增加城市和设置
        addCityIv.setOnClickListener(this);
        moreIv.setOnClickListener(this);

        //加载fragment
        fragmentList = new ArrayList<>();
        cityList = new ArrayList<>();
        imageViewList = new ArrayList<>();

        //todo 这里放汉字还是编码，之后还要考虑
        if (cityList.size()==0) {
            cityList.add("北京");
            cityList.add("天津");
        }

        initPager();


        //adapter = new CityFragmentPagerAdapter(this,fragmentList);
        adapter = new CityFragmentPagerAdapter(getSupportFragmentManager(),getLifecycle(),fragmentList);
        mainVp.setAdapter(adapter);

       /* 测试获取https数据
       GetData.testData();*/

        //initPoint();
    }

    private void initPager() {
        /* 创建Fragment对象，添加到ViewPager数据源当中*/
        for (int i = 0; i < cityList.size(); i++) {
            CityWeatherFragment cwFrag = new CityWeatherFragment();
            Bundle bundle = new Bundle();
            bundle.putString("city",cityList.get(i));
            cwFrag.setArguments(bundle);
            fragmentList.add(cwFrag);
            System.out.println("添加"+ cityList.get(i));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_iv_add:

                break;
            case R.id.main_iv_more:

                break;
        }
    }
}