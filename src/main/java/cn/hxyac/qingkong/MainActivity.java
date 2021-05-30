package cn.hxyac.qingkong;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import cn.hxyac.qingkong.city.CityManagerActivity;
import cn.hxyac.qingkong.db.DBManager;

import java.util.ArrayList;
import java.util.List;

/**
 D:\Android\android-sdk-windows\emulator\emulator -avd Pixel_2_API_30 -dns-server 114.114.114.114
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView addCityIv,moreIv;
    LinearLayout pointLayout;
    ViewPager2 mainVp;

    List<Fragment> fragmentList;
    List<String[]> cityList;
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

        //todo 听说操作数据库是在子线程完成？
        cityList = DBManager.queryAllCityName();
        imageViewList = new ArrayList<>();


        try {
            Intent intent = getIntent();
            String cityCode = intent.getStringExtra("cityCode");
            String city = intent.getStringExtra("city");
            String[] temp = new String[2];
            temp[0] = city;
            temp[1] = cityCode;

            if (!TextUtils.isEmpty(city)) {
                int i = 0;
                for(;i < cityList.size();i++){
                    if(cityList.get(i)[1].equals(cityCode)){
                        break;
                    }
                }
                if(i == cityList.size()) cityList.add(temp);
            }
        }catch (Exception e){
            Log.i("hxyerror","对不起，我有罪");
        }


        if (cityList.size()==0) {
            cityList.add(new String[]{"北京","101010100"});
            cityList.add(new String[]{"天津","101030100"});
        }

        initPager();
        adapter = new CityFragmentPagerAdapter(this,fragmentList);
        //adapter = new CityFragmentPagerAdapter(getSupportFragmentManager(),getLifecycle(),fragmentList);
        mainVp.setAdapter(adapter);

        initPoint();
        mainVp.setCurrentItem(fragmentList.size()-1);

        //设置ViewPager页面监听
        setPagerListener();

    }

    private void setPagerListener(){
        mainVp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < imageViewList.size(); i++) {
                    imageViewList.get(i).setImageResource(R.mipmap.a1);
                }
                imageViewList.get(position).setImageResource(R.mipmap.a2);
            }
        });
    }

    private void initPoint() {
        for (int i = 0; i < fragmentList.size(); i++) {
            ImageView pIv = new ImageView(this);
            pIv.setImageResource(R.mipmap.a1);
            pIv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) pIv.getLayoutParams();
            lp.setMargins(0,0,20,0);
            imageViewList.add(pIv);
            pointLayout.addView(pIv);
        }
        imageViewList.get(imageViewList.size()-1).setImageResource(R.mipmap.a2);
    }

    private void initPager() {
        //创建Fragment对象，添加到ViewPager数据源当中
        for (int i = 0; i < cityList.size(); i++) {
            CityWeatherFragment cwFrag = new CityWeatherFragment();
            Bundle bundle = new Bundle();
            bundle.putString("city",cityList.get(i)[0]);
            bundle.putString("citycode",cityList.get(i)[1]);
            cwFrag.setArguments(bundle);
            fragmentList.add(cwFrag);
            //todo println("添加"+ cityList.get(i)[0])
            //System.out.println("添加"+ cityList.get(i)[0]);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.main_iv_add:
                intent.setClass(this, CityManagerActivity.class);
                break;
            case R.id.main_iv_more:
                intent.setClass(this, MoreActivity.class);
                break;
        }
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        List<String[]> list = DBManager.queryAllCityName();
        if (list.size()==0) {
            list.add(new String[]{"北京","101010100"});
        }
        cityList.clear();
        cityList.addAll(list);
        fragmentList.clear();
        initPager();
        adapter.notifyDataSetChanged();

        imageViewList.clear();
        pointLayout.removeAllViews();
        initPoint();

        mainVp.setCurrentItem(fragmentList.size()-1);
    }
}