package com.gg.habittrain;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.gg.habittrain.data.HabitData;
import com.gg.habittrain.db.DBManager;
import com.gg.habittrain.view.MainActivity;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/6/21 0021.
 */

public class HabitListReceiver extends XGPushBaseReceiver{
    private DBManager mgr;
    @Override
    public void onRegisterResult(Context context, int i, XGPushRegisterResult xgPushRegisterResult) {
    }

    @Override
    public void onUnregisterResult(Context context, int i) {
    }

    @Override
    public void onSetTagResult(Context context, int i, String s) {
    }

    @Override
    public void onDeleteTagResult(Context context, int i, String s) {
    }

    @Override
    public void onTextMessage(Context context, XGPushTextMessage xgPushTextMessage) {
        Log.e("HabitListReceiver","xgPushTextMessage="+xgPushTextMessage.toString());
        //刷新界面！
        if(mgr==null){
            mgr = new DBManager(context);
        }
        try {
            JSONObject jClass = new JSONObject(xgPushTextMessage.getCustomContent());
            String type = jClass.getString("type");
            JSONObject model = jClass.getJSONObject("model");
            HabitData c = new HabitData(model.getString("id"),model.getString("time"), model.getString("title")
                    , model.getString("cuecontent"));
            if(type.equals("0")){
                mgr.deleteForId(model.getString("id"));
            }else if(type.equals("1")){
                mgr.addForOne(c);
            }else if(type.equals("2")){
                mgr.deleteForId(model.getString("id"));
                mgr.addForOne(c);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent mIntent=new Intent(MainActivity.ACTION_INTENT_RECEIVER);
        mIntent.putExtra("customContent", xgPushTextMessage.getCustomContent());
        context.sendBroadcast(mIntent);
    }

    @Override
    public void onNotifactionClickedResult(Context context, XGPushClickedResult xgPushClickedResult) {
    }

    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult xgPushShowedResult) {
        Log.e("ClassListReceiverx","title="+xgPushShowedResult.getTitle()+",content="+xgPushShowedResult.getContent());
    }


}
