package cn.hxyac.qingkong.city;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import cn.hxyac.qingkong.MainActivity;
import cn.hxyac.qingkong.R;
import cn.hxyac.qingkong.db.DBManager;
import cn.hxyac.qingkong.tools.DoTool;
import cn.hxyac.qingkong.tools.MusicService;

import java.util.ArrayList;
import java.util.List;

public class DeleteCityActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView errorIv,rightIv;
    ListView deleteLv;
    List<String[]> mDatas;
    List<String> deleteCitys;
    private DeleteCityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delect);

        errorIv = findViewById(R.id.delete_iv_error);
        rightIv = findViewById(R.id.delete_iv_right);
        deleteLv = findViewById(R.id.delete_lv);

        errorIv.setOnClickListener(this);
        rightIv.setOnClickListener(this);

        mDatas = DBManager.queryAllCityName();
        deleteCitys = new ArrayList<>();


        adapter = new DeleteCityAdapter(this, mDatas, deleteCitys);
        deleteLv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_iv_error:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示信息").setMessage("您确定要舍弃更改么？")
                        .setPositiveButton("舍弃更改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();   //关闭当前的activity
                            }
                        });

                builder.setNegativeButton("取消",null);
                builder.create().show();
                break;
            case R.id.delete_iv_right:
                for (int i = 0; i < deleteCitys.size(); i++) {
                    String city = deleteCitys.get(i);
                    DBManager.deleteInfoByCity(city);
                }
                DoTool.runMusic(this,R.raw.delete);
                DoTool.runToast(this,getPackageName(),"删除成功");
                finish();
                break;
        }
    }
}