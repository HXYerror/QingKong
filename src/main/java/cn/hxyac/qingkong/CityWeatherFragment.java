package cn.hxyac.qingkong;


//extends BaseFragment

import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import cn.hxyac.qingkong.bean.ThreeDayBean;
import cn.hxyac.qingkong.bean.WeatherBean;
import cn.hxyac.qingkong.hefeng.GetData;
import com.google.gson.Gson;

import java.lang.reflect.Type;

public class CityWeatherFragment extends Fragment implements View.OnClickListener{
    TextView tempTv,cityTv,conditionTv,windTv,tempRangeTv,dateTv;
    TextView clothIndexTv,carIndexTv,coldIndexTv,sportIndexTv,raysIndexTv,airIndexTv;
    ImageView dayIv;
    LinearLayout futureLayout;
    ScrollView outLayout;

    //JHIndexBean.ResultBean.LifeBean lifeBean;    //指数信息



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_city_weather, container, false);
        initView(view);
        getData("101010100");
        System.out.println("设置viewpaper的视图");
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
        //outLayout = view.findViewById(R.id.out_layout);
//设置点击事件的监听
        clothIndexTv.setOnClickListener(this);
        carIndexTv.setOnClickListener(this);
        coldIndexTv.setOnClickListener(this);
        sportIndexTv.setOnClickListener(this);
        raysIndexTv.setOnClickListener(this);
        airIndexTv.setOnClickListener(this);
    }

//   @Override
//    public void onSuccess(String result, Type type) {
//        parseData(result,type);
//    }
//
//    @Override
//    public void onError(Throwable ex, boolean isOnCallback) {
//
//    }

    private void getData(String cityCode) {
        String result = GetData.DataTemp(cityCode);
        WeatherBean weatherBean = (WeatherBean) parseData(result,WeatherBean.class);
        showDataTemp(weatherBean);

        result = GetData.DataThree(cityCode);
        ThreeDayBean threeDayBean = (ThreeDayBean) parseData(result,ThreeDayBean.class);
        showDataThree(threeDayBean);
    }

    private Object parseData(String result, Type type) {
        //使用gson解析数据
        return new Gson().fromJson(result,type);
    }

    private void showDataTemp(WeatherBean weatherBean) {

        tempTv.setText(weatherBean.getNow().getTemp());
        //todo city信息需要提前写好，接口没有
        //cityTv.setText(weatherBean.getNow().get);
        conditionTv.setText(weatherBean.getNow().getText());
        windTv.setText(weatherBean.getNow().getWindDir() + " " + weatherBean.getNow().getWindScale()+"级");
        tempRangeTv.setText("体感"+weatherBean.getNow().getFeelsLike());
        dateTv.setText(weatherBean.getNow().getObsTime());

        //根据天气选择图标
        //todo 图标的路径转化可能有问题，之后再看
        //dayIv.setImageIcon(Icon.createWithContentUri("@mipmap/"+ "i"+weatherBean.getNow().getIcon()));
        //dayIv.setImageIcon(Icon.createWithFilePath("@mipmap/"+ "i"+weatherBean.getNow().getIcon()));
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

            //iIv.setImageIcon(Icon.createWithContentUri("@mipmap/"+ "i"+threeDayBean.getDaily().get(i).getIconDay()));
        }
    }


    @Override
    public void onClick(View v) {
        //todo 对于指数的一些处理，先不做
    }
}