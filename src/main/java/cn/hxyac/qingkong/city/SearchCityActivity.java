package cn.hxyac.qingkong.city;

import android.content.ComponentName;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import cn.hxyac.qingkong.MainActivity;
import cn.hxyac.qingkong.R;
import cn.hxyac.qingkong.bean.CityCodeBean;
import cn.hxyac.qingkong.bean.WeatherBean;
import cn.hxyac.qingkong.db.DBManager;
import cn.hxyac.qingkong.hefeng.GetData;
import cn.hxyac.qingkong.hefeng.URLUtils;
import cn.hxyac.qingkong.tools.DoTool;
import com.google.gson.Gson;

public class SearchCityActivity extends AppCompatActivity implements View.OnClickListener {

    EditText searchEt;
    ImageView  submitIv;
    GridView searchGv;

    String[] hotCity = {"呼和浩特","北京","上海","广州","深圳","南京","苏州","厦门","长沙","成都",
            "杭州","武汉","西安","重庆","天津","南宁"};

    private ArrayAdapter<String> adapter;
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchEt = findViewById(R.id.search_et);
        submitIv = findViewById(R.id.search_iv_submit);
        searchGv = findViewById(R.id.search_gv);


        submitIv.setOnClickListener(this);
        //todo 可以试试tablelayout，挤在一起不是很好看？
        adapter = new ArrayAdapter<>(this, R.layout.item_hot_city,hotCity);
        searchGv.setAdapter(adapter);
        setListener();
    }

    @Override
    public void onClick(View v) {
        //todo 可以添加一个返回按钮
        switch (v.getId()) {
            case R.id.search_iv_submit:
                city = searchEt.getText().toString();
                if (!TextUtils.isEmpty(city)) {
                    next();
                }else {
                    Toast.makeText(this,"输入内容不能为空！",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void setListener() {
        searchGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                city = hotCity[position];
                next();
            }
        });
    }

    public void next(){
        String result = GetData.Data(URLUtils.getCity_url(city));
        CityCodeBean cityCodeBean = new Gson().fromJson(result,CityCodeBean.class);
        if(cityCodeBean.getCode().equals("200")){
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("city",cityCodeBean.getLocation().get(0).getName());
            intent.putExtra("cityCode",cityCodeBean.getLocation().get(0).getId());
            startActivity(intent);
            //todo 广播添加成功
            DoTool.runToast(this,getPackageName(),"添加成功");
        }else{
            Toast.makeText(this,"添加失败，未收录此城市！",Toast.LENGTH_SHORT).show();
        }
    }
}