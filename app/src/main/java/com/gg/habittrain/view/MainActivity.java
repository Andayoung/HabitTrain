package com.gg.habittrain.view;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.gg.habittrain.R;
import com.gg.habittrain.SerialNumberHelper;
import com.gg.habittrain.data.HabitData;
import com.gg.habittrain.db.DBManager;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public static String ACTION_INTENT_RECEIVER = "com.gg.habit.receiver";
    @BindView(R.id.lst_habit)
    ListView lstHabit;
    private DBManager mgr;
    public MyBroadcastReceiver myReceiver;
    private List<Map<String, String>> list;
    private HabitAdapter haAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        registerMessageReceiver();
        mgr = new DBManager(this);

    }

    private void initView() {
        list = new ArrayList<>();
        List<HabitData> cz = mgr.query();
        Log.e("MainActivity", "size=" + cz.size());
        for (int i = 0; i < cz.size(); i++) {
            Map<String, String> zp = new HashMap<>();
            zp.put("time", cz.get(i).getTime());
            zp.put("title", cz.get(i).getTitle());
            zp.put("cuecontent", cz.get(i).getContent());
            Log.e("MainActivity", "title=" + cz.get(i).getTitle() + ",time=" + cz.get(i).getTime() + ",cuecontent=" + cz.get(i).getContent());
            list.add(zp);
        }
        haAdapter = new HabitAdapter(this, list);
        lstHabit.setAdapter(haAdapter);
        lstHabit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ItemDetailActivity.class);
                intent.putExtra("time", ((HabitAdapter) parent.getAdapter()).getData().get(position).get("time"));
                intent.putExtra("title", ((HabitAdapter) parent.getAdapter()).getData().get(position).get("title"));
                intent.putExtra("cuecontent", ((HabitAdapter) parent.getAdapter()).getData().get(position).get("cuecontent"));
                startActivity(intent);
            }
        });
    }

    public void registerMessageReceiver() {
        myReceiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_INTENT_RECEIVER);
        registerReceiver(myReceiver, filter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SerialNumberHelper serialNumberHelper = new SerialNumberHelper(this);
        String serialNumber = serialNumberHelper.read4File();
        if (serialNumber == null || serialNumber.equals("")) {
            //未登录，转向登录界面
            Log.e("onResume", "8888");
            showDialog();
        } else {
            String[] s = serialNumber.split(" ");
            //登录，用serialNumber注册并绑定信鸽推送
            Context context = getApplicationContext();
            XGPushConfig.enableDebug(context, true);
            XGPushManager.registerPush(context, s[0], new XGIOperateCallback() {
                @Override
                public void onSuccess(Object data, int flag) {
                    Log.e("TPush", "注册成功,Token值为：" + data);
                }

                @Override
                public void onFail(Object data, int errCode, String msg) {
                    Log.e("TPush", "注册失败,错误码为：" + errCode + ",错误信息：" + msg);
                }
            });
            initView();
            XGPushClickedResult clickedResult = XGPushManager.onActivityStarted(this);
            if (clickedResult != null) {
                String title = clickedResult.getTitle();
                Log.e("TPush", "title:" + title);
                String id = clickedResult.getMsgId() + "";
                Log.e("TPush", "id:" + id);
                String content = clickedResult.getContent();
                Log.e("TPush", "content:" + content);
            }
        }


    }

    void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("还没有登录？");
        builder.setTitle("提示");
        builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                MainActivity.this.finish();
            }
        });
        builder.setNegativeButton("去登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("MainActivity", "未登录");
                dialog.dismiss();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        ComponentName comp = new ComponentName("com.gg.myinformation", "com.gg.myinformation.LogOrRegActivity");
                        intent.setComponent(comp);
                        intent.putExtra("appName", "habitTrain");
                        intent.setAction("intent.action.loginZ");
                        startActivity(intent);
                    }
                }, 1500);

            }
        });
        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }


    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            if (arg1.getAction().equals(ACTION_INTENT_RECEIVER)) {
                String content = arg1.getStringExtra("customContent");
                Log.e("onReceive", "content=" + content);
                list.clear();
                List<HabitData> cz = mgr.query();
                Log.e("MainActivity", "size=" + cz.size());
                for (int i = 0; i < cz.size(); i++) {
                    Map<String, String> zp = new HashMap<>();
                    zp.put("time", cz.get(i).getTime());
                    zp.put("title", cz.get(i).getTitle());
                    zp.put("cuecontent", cz.get(i).getContent());
                    Log.e("MainActivity", "title=" + cz.get(i).getTitle() + ",time=" + cz.get(i).getTime() + ",cuecontent=" + cz.get(i).getContent());
                    list.add(zp);
                }
                haAdapter.setData(list);
                haAdapter.notifyDataSetChanged();
            }

        }
    }
}
