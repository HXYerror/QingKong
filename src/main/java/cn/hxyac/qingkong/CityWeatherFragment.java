package cn.hxyac.qingkong;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import cn.hxyac.qingkong.bean.ThreeDayBean;
import cn.hxyac.qingkong.bean.WeatherBean;
import cn.hxyac.qingkong.db.DBManager;
import cn.hxyac.qingkong.hefeng.GetData;
import cn.hxyac.qingkong.hefeng.URLUtils;
import cn.hxyac.qingkong.tools.DoTool;
import com.google.gson.Gson;

import java.lang.reflect.Type;

public class CityWeatherFragment extends Fragment implements View.OnClickListener{
    TextView tempTv,cityTv,conditionTv,windTv,tempRangeTv,dateTv;
    TextView clothIndexTv,carIndexTv,coldIndexTv,sportIndexTv,raysIndexTv,airIndexTv;
    ImageView dayIv;
    LinearLayout futureLayout;
    String city;
    String cityCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_city_weather, container, false);
        initView(view);
        Bundle bundle = getArguments();
        city = bundle.getString("city");
        cityCode = bundle.getString("citycode");
        //System.out.println("-----------------creat"+city);
        getData();
        return view;
    }



    private void initView(View view) {
// 用于初始化控件操作
        tempTv = view.findViewById(R.id.frag_tv_currenttemp);
        cityTv = view.findViewById(R.id.frag_tv_city);
        conditionTv = view.findViewById(R.id.frag_tv_condition);
        windTv = view.findViewById(R.id.frag_tv_wind);
        tempRangeTv = view.findViewById(R.id.frag_tv_temprange);
        dateTv = view.findViewById(R.id.frag_tv_date);
        clothIndexTv = view.findViewById(R.id.frag_index_tv_dress);
        carIndexTv = view.findViewById(R.id.frag_index_tv_washcar);
        coldIndexTv = view.findViewById(R.id.frag_index_tv_cold);
        sportIndexTv = view.findViewById(R.id.frag_index_tv_sport);
        raysIndexTv = view.findViewById(R.id.frag_index_tv_rays);
        airIndexTv = view.findViewById(R.id.frag_index_tv_air);
        dayIv = view.findViewById(R.id.frag_iv_today);
        futureLayout = view.findViewById(R.id.frag_center_layout);
//设置点击事件的监听
        clothIndexTv.setOnClickListener(this);
        carIndexTv.setOnClickListener(this);
        coldIndexTv.setOnClickListener(this);
        sportIndexTv.setOnClickListener(this);
        raysIndexTv.setOnClickListener(this);
        airIndexTv.setOnClickListener(this);
    }

    private void getData() {
        //todo 如果没有获取到数据，根据code判断，告知用户
        String result = GetData.Data(URLUtils.getTemp_url(cityCode));

        WeatherBean weatherBean = (WeatherBean) parseData(result,WeatherBean.class);
        if(result != null && weatherBean.getCode().equals("200")){
            int i = DBManager.updateInfoByCity(city,result);
            if (i<=0) {
                DBManager.addCityInfo(city,cityCode,result);
            }
            showDataTemp(weatherBean);
        }else{
            String s = DBManager.queryInfoByCity(city);
            if (!TextUtils.isEmpty(s)) {
                weatherBean = (WeatherBean) parseData(s,WeatherBean.class);
                showDataTemp(weatherBean);
            }
        }

        result = GetData.Data(URLUtils.getThree_url(cityCode));
        if (result == null) return;

        ThreeDayBean threeDayBean = (ThreeDayBean) parseData(result,ThreeDayBean.class);
        if(threeDayBean.getCode().equals("200")){
            showDataThree(threeDayBean);
        }
    }

    private Object parseData(String result, Type type) {
        //使用gson解析数据
        return new Gson().fromJson(result,type);
    }

    private void showDataTemp(WeatherBean weatherBean) {

        tempTv.setText(weatherBean.getNow().getTemp());
        //todo city信息需要提前写好，接口没有
        cityTv.setText(city);
        conditionTv.setText(weatherBean.getNow().getText());
        windTv.setText(weatherBean.getNow().getWindDir() + " " + weatherBean.getNow().getWindScale()+"级");
        tempRangeTv.setText("体感"+weatherBean.getNow().getFeelsLike());
        dateTv.setText(weatherBean.getNow().getObsTime());
        //todo 图标的路径转化可能有问题，之后再看

        String imageName = "i"+weatherBean.getNow().getIcon();
        int resId = getResources().getIdentifier(imageName, "mipmap", getContext().getPackageName());
        dayIv.setImageResource(resId);
        if(resId == 0)  dayIv.setImageResource(R.mipmap.i999);
    }

    private void showDataThree(ThreeDayBean threeDayBean) {
        //将三天的天气加载
        for(int i = 0;i < 3;i++){
            View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.item_main_three,null);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            futureLayout.addView(itemView);

            TextView idateTv = itemView.findViewById(R.id.item_center_tv_date);
            TextView iconTv = itemView.findViewById(R.id.item_center_tv_con);
            TextView windTv = itemView.findViewById(R.id.item_center_tv_wind);
            TextView itemprangeTv = itemView.findViewById(R.id.item_center_tv_temp);
            ImageView iIv = itemView.findViewById(R.id.item_center_iv);

            idateTv.setText(threeDayBean.getDaily().get(i).getFxDate());
            iconTv.setText(threeDayBean.getDaily().get(i).getTextDay());
            windTv.setText(threeDayBean.getDaily().get(i).getWindDirDay());
            itemprangeTv.setText(threeDayBean.getDaily().get(i).getTempMin() + " - " +
                    threeDayBean.getDaily().get(i).getTempMax());


            String imageName = "i"+threeDayBean.getDaily().get(i).getIconDay();
            int resId = getResources().getIdentifier(imageName, "mipmap", getContext().getPackageName());
            iIv.setImageResource(resId);
            if(resId == 0)  iIv.setImageResource(R.mipmap.i999);
        }
    }


    @Override
    public void onClick(View v) {
        //todo 对于指数的一些处理，先不做
    }
}