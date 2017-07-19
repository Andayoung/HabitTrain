package com.gg.habittrain.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gg.habittrain.data.HabitData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/23 0023.
 */

public class DBManager {
    private DBsqliteHelper helper;

    public DBManager(Context context) {
        helper = new DBsqliteHelper(context);
    }


    public void addForOne(HabitData habitData) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("INSERT INTO myhabit VALUES(null,?,?,?,?,?)", new Object[]{habitData.getHabitId(), habitData.getTime(), habitData.getTitle(), habitData.getContent(), null});
        Log.e("DBManager", "添加成功");
        db.close();

    }
    public void deleteForId(String habitid) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("myhabit", "habitid=?", new String[]{habitid});
        Log.e("DBManager", "删除成功");
    }

    public void deleteAll() {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from myhabit");
        Log.e("DBManager", "删除成功");
    }



    public List<HabitData> queryForId(String habitid) {
        ArrayList<HabitData> habitDatas = new ArrayList<HabitData>();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.query("myhabit", null, "habitid=?", new String[]{habitid}, null, null, null, null);
        while (c.moveToNext()) {
            HabitData habitData = new HabitData(c.getString(c.getColumnIndex("habitid"))
                    , c.getString(c.getColumnIndex("time"))
                    , c.getString(c.getColumnIndex("title"))
                    , c.getString(c.getColumnIndex("cuecontent")));
            habitDatas.add(habitData);
        }
        c.close();
        return habitDatas;
    }
    public List<HabitData> query() {
        ArrayList<HabitData> habitDatas = new ArrayList<HabitData>();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM myhabit", null);
        while (c.moveToNext()) {
            HabitData habitData = new HabitData(c.getString(c.getColumnIndex("habitid"))
                    , c.getString(c.getColumnIndex("time"))
                    , c.getString(c.getColumnIndex("title"))
                    , c.getString(c.getColumnIndex("cuecontent")));
            habitDatas.add(habitData);
        }
        c.close();
        return habitDatas;
    }

}
